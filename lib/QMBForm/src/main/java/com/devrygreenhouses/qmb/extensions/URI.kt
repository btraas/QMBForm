package com.devrygreenhouses.qmb.extensions


import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun Uri.autoRotatedBitmap(cr: ContentResolver): Bitmap? {

    try {

        //val newPath = cr.canonicalize(this)

        val cursor = cr.query(this, arrayOf(MediaStore.MediaColumns.DATA), null, null, null) ?: return null

        cursor.use { c ->
            if(c.moveToFirst()) {
                val newPath = cursor.getString(0)

                val exif = ExifInterface(newPath)
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

                val tmpBmp = MediaStore.Images.Media.getBitmap(cr, this)
                //val tmpBmp = BitmapFactory.decodeStream(FileInputStream(this), null, options)
                return Bitmap.createBitmap(tmpBmp, 0,0, tmpBmp.width, tmpBmp.height, matrix, true)

            } else {
                return null
            }
        }



    } catch(e: IOException) {
        e.printStackTrace()
        Log.w("Uri.autoRotatedBitmap", "-- Failed to set image data")
        return null
    } catch(e: OutOfMemoryError) {
        e.printStackTrace()
        Log.w("Uri.autoRotatedBitmap", "-- OOM Error in ")
        return null
    }

}

fun Uri.isContent(): Boolean {
    return (scheme == "content")
}

fun Uri.isFile(): Boolean {
    return scheme == "file"
}