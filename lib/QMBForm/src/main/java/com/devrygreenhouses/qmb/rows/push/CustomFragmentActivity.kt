package com.devrygreenhouses.qmb.rows.push

import android.os.Bundle
import android.util.Log
import com.quemb.qmbform.R
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import com.devrygreenhouses.qmb.rows.image.ImageReceiver
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.devrygreenhouses.qmb.FormFinishDelegate
import com.devrygreenhouses.qmb.extensions.compressBitmap
import com.devrygreenhouses.qmb.rows.image.ImageCell
import com.devrygreenhouses.qmb.rows.push.fragment.FragmentPushHandler
import java.io.File
import java.io.IOException


class CustomFragmentActivity : AppCompatActivity(), ImageReceiver {

    var handler: FragmentPushHandler? = null
    override var onReceiveBitmap: ((Bitmap?) -> Unit)? = null
    override var onReceiveUri: ((Uri?) -> Unit)? = null
    override var initPhotoFromCamera: (() -> Unit)? = null
    override var initPhotoFromGallery: (() -> Unit)? = null


    private val TAG = "CustomFormActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")


        setContentView(R.layout.activity_custom_fragment)


        handler = (intent.getSerializableExtra("handler") as PushHandlerPointer).retrieve() as FragmentPushHandler

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = handler?.title

        //Toast.makeText(this, "generating form", Toast.LENGTH_SHORT).show()

        handler?.newActivity = this
        handler?.generate(this)




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

//        menu?.add(0,0,0,"Submit")

        menuInflater.inflate(R.menu.save, menu)



        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.save -> {
                val fragment = supportFragmentManager.findFragmentById(R.id.fragment)
                if(fragment is FormFinishDelegate) {
                    fragment.finishForm()
                } else {
                    finish()
                }
//                val fragment = supportFragmentManager.findFragmentById(R.id.fragment)
//                fragment.
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ImageCell.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            var imageData = data?.extras?.get("data") as? Bitmap?
            var imageFile: File? = null

            if (data?.getData() is Uri) {

//                val cursor = contentResolver.query(data.data, arrayOf(android.provider.MediaStore.Images.ImageColumns.DATA), null, null, null)
//                cursor!!.moveToFirst()
//
//                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
//                val selectedImagePath = cursor.getString(idx)
//                cursor.close()
//
//                imageFile = File(selectedImagePath)

                onReceiveUri?.invoke(data.data)
            }

            if(imageData == null) {
                val uri = data?.getData()



                if (uri != null) {

                    try {
                        imageData = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            }




            runOnUiThread {
                onReceiveBitmap?.invoke(imageData)
            }



        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                ImageCell.CUSTOM_CAMERA_PERMISSION_CODE -> initPhotoFromCamera?.invoke()
                ImageCell.CUSTOM_GALLERY_PERMISSION_CODE -> initPhotoFromGallery?.invoke()
            }

        } else {
            Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
        }


    }

//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                imageView.setImageBitmap(photo);
//            }
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
