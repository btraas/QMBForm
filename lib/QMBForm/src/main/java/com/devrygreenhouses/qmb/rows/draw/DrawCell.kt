package com.devrygreenhouses.qmb.rows.draw

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.ImageView

import com.quemb.qmbform.R
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.devrygreenhouses.qmb.rows.image.ImageCell
import com.devrygreenhouses.qmb.rows.image.ImageRowDescriptor
import com.devrygreenhouses.qmb.rows.push.CustomFragmentActivity
import com.devrygreenhouses.qmb.rows.push.PushHandlerPointer
import com.devrygreenhouses.qmb.rows.push.fragment.FragmentPushHandler
import android.graphics.Bitmap
import android.net.Uri
import com.quemb.qmbform.descriptor.Value
import java.io.FileOutputStream
import java.io.IOException


@SuppressLint("ViewConstructor")
/**
 * Created by pmaccamp on 8/28/2015.
 */
class DrawCell(val activity: Activity, rowDescriptor: ImageRowDescriptor)
    : ImageCell(activity, rowDescriptor, null) {



//
//    companion object {
//        public const val IMAGE_RECEIVED = "image_received"
//        public const val CAMERA_REQUEST = 1888
//        public const val CUSTOM_CAMERA_PERMISSION_CODE = 100
//    }

    //val newFragment = DrawFragment()
    //var savedImage: Bitmap? = null




    val handler = FragmentPushHandler(activity, rowDescriptor.title, { true }) {

        val newFragment = DrawFragment()
        newFragment.onSave = {bitmap ->

            //image = it
            applyBitmap(bitmap)

            this.createImageFile()?.let { newFile ->
                var out: FileOutputStream? = null
                try {
                    out = FileOutputStream(newFile.absolutePath)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) // bmp is your Bitmap instance
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    try {
                        if (out != null) {
                            out!!.close()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                rowDescriptor.value = Value(Uri.fromFile(newFile))
            }




        }
        newFragment
    }



    override fun onClick(v: View?) {

        val intent = Intent(context, CustomFragmentActivity::class.java)

        intent.putExtra("handler", PushHandlerPointer(handler))
        activity.startActivityForResult(intent, 1)



    }



}
