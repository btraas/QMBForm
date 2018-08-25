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
import com.devrygreenhouses.qmb.rows.image.ImageReceiver
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.devrygreenhouses.qmb.FormFinishDelegate
import com.devrygreenhouses.qmb.rows.image.ImageCell
import com.devrygreenhouses.qmb.rows.push.fragment.FragmentPushHandler
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

            if(imageData == null) {
                val uri = data?.getData()

                try {
                    imageData = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }


            runOnUiThread {
                imageData?.let {
                    onReceiveBitmap?.invoke(it)
                }
            }

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
