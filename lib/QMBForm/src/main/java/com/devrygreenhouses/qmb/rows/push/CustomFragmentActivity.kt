package com.devrygreenhouses.qmb.rows.push

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.quemb.qmbform.R
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import com.devrygreenhouses.qmb.rows.image.ImageCell
import com.devrygreenhouses.qmb.rows.image.ImageReceiver
import android.R.attr.bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import java.io.IOException


class CustomFragmentActivity : AppCompatActivity(), ImageReceiver {

    var handler: FragmentPushHandler? = null
    override var onReceiveBitmap: ((Bitmap?) -> Unit)? = null

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ImageCell.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            var imageData = data?.extras?.get("data") as? Bitmap?

            if(imageData == null) {
                val uri = data?.getData()

                try {
                    imageData = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

//            if(imageData == null) {
//                val selectedImage = data?.getData()
//                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//
//                val cursor = contentResolver.query(selectedImage,
//                        filePathColumn, null, null, null)
//                cursor!!.moveToFirst()
//
//                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
//                val picturePath = cursor.getString(columnIndex)
//                cursor.close()
//
//                imageData = BitmapFactory.decodeFile(picturePath)
//
//            }

            runOnUiThread {
                imageData?.let {
                    onReceiveBitmap?.invoke(it)
                }
            }


//            val newIntent = Intent(ImageCell.IMAGE_RECEIVED)
//            newIntent.putExtra("data", imageData)
//            LocalBroadcastManager.getInstance(this).sendBroadcast(data)
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ImageCell.CUSTOM_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, ImageCell.CAMERA_REQUEST)
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }

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
