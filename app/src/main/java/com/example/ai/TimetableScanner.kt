package com.example.ai

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object TimetableScanner {

    private const val TAG = "TimetableScanner"

    fun extractBusySlots(
        context: Context,
        uri: Uri,
        onResult: (List<TimetableSlot>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val contentResolver = context.contentResolver
                val mimeType = contentResolver.getType(uri)
                val bitmaps = mutableListOf<Bitmap>()

                if (mimeType == "application/pdf") {
                    bitmaps.addAll(renderPdfToBitmaps(context, uri))
                } else {
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val source = ImageDecoder.createSource(contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        @Suppress("DEPRECATION")
                        MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    }
                    bitmaps.add(bitmap.copy(Bitmap.Config.ARGB_8888, false))
                }

                val allDetectedSlots = mutableListOf<TimetableSlot>()
                val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

                for (bitmap in bitmaps) {
                    val image = InputImage.fromBitmap(bitmap, 0)
                    try {
                        val visionText = recognizer.process(image).await()
                        Log.d(TAG, "Detected text: ${visionText.text}")
                        allDetectedSlots.addAll(TimetableParser.parse(visionText.text))
                    } catch (e: Exception) {
                        Log.e(TAG, "OCR failed for a page", e)
                    }
                }

                val finalSlots = allDetectedSlots.distinctBy { it.time.lowercase().trim() }
                
                withContext(Dispatchers.Main) {
                    onResult(finalSlots)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Scanning failed", e)
                withContext(Dispatchers.Main) {
                    onResult(emptyList())
                }
            }
        }
    }

    private fun renderPdfToBitmaps(context: Context, uri: Uri): List<Bitmap> {
        val bitmaps = mutableListOf<Bitmap>()
        context.contentResolver.openFileDescriptor(uri, "r")?.use { pfd ->
            val renderer = PdfRenderer(pfd)
            val pageCount = renderer.pageCount
            
            for (i in 0 until pageCount) {
                renderer.openPage(i).use { page ->
                    val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bitmap)
                    canvas.drawColor(Color.WHITE)
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    bitmaps.add(bitmap)
                }
            }
            renderer.close()
        }
        return bitmaps
    }
}
