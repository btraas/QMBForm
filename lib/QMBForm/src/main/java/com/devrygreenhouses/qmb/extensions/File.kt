package com.devrygreenhouses.qmb.extensions


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.util.Log
import java.io.*
import kotlin.math.roundToInt

fun File.autoRotatedBitmap(): Bitmap? {


    try {
        val exif = ExifInterface(path)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        val angle = when(orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }

        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        val options = BitmapFactory.Options()
        options.inSampleSize = 2

        val tmpBmp = BitmapFactory.decodeStream(FileInputStream(this), null, options)
        if(tmpBmp == null) {
            return null
        }
        return Bitmap.createBitmap(tmpBmp, 0,0, tmpBmp.width, tmpBmp.height, matrix, true)


    } catch(e: IOException) {
        Log.w("File.autoRotatedBitmap", "-- Failed to set image data")
        return null
    } catch(e: OutOfMemoryError) {
        Log.w("File.autoRotatedBitmap", "-- OOM Error in ")
        return null
    }

}

fun File.compressBitmap(quality: Float = 1F, format: Bitmap.CompressFormat =  Bitmap.CompressFormat.JPEG) {
    try {


        val options = BitmapFactory.Options()
        options.inSampleSize = 2

        val tmpBmp = BitmapFactory.decodeStream(FileInputStream(this), null, options)

        val os = FileOutputStream(this)

        if(tmpBmp == null) return

        tmpBmp.compress(format, (quality * 100).roundToInt(), os)


    } catch(e: IOException) {
        Log.w("File.compressBitmap", "-- Failed to set image data")
        e.printStackTrace()
    } catch(e: OutOfMemoryError) {
        Log.w("File.compressBitmap", "-- OOM Error in ")
    }

}