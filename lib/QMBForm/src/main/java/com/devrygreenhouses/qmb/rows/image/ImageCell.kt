package com.devrygreenhouses.qmb.rows.image

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView

import com.quemb.qmbform.view.FormButtonFieldCell
import com.quemb.qmbform.R
import android.view.View
import com.devrygreenhouses.qmb.rows.image.ImageReceiver
import com.devrygreenhouses.qmb.rows.image.ImageRowDescriptor
import com.quemb.qmbform.descriptor.CellDescriptor
import com.quemb.qmbform.descriptor.Value
import kotlinx.android.synthetic.main.finish_field_cell.view.*
import java.io.File

import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider
import com.devrygreenhouses.qmb.ReflectionBuildConfig
import com.devrygreenhouses.qmb.extensions.autoRotatedBitmap
import com.devrygreenhouses.qmb.extensions.compressBitmap
import com.devrygreenhouses.qmb.extensions.context
import com.devrygreenhouses.qmb.extensions.isContent
import com.quemb.qmbform.BuildConfig
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception


@SuppressLint("ViewConstructor")
open
/**
 * Created by pmaccamp on 8/28/2015.
 */
class ImageCell(context: Context, rowDescriptor: ImageRowDescriptor, val imageReceiver: ImageReceiver?)
    : FormButtonFieldCell(context, rowDescriptor) {

    var mCurrentPhotoPath: String? = null // only used for creating files with the camera option
    var imageUri: Uri? = null


    companion object {
        public const val IMAGE_RECEIVED = "image_received"
        public const val CAMERA_REQUEST = 1888
        public const val CUSTOM_CAMERA_PERMISSION_CODE = 100
        public const val CUSTOM_GALLERY_PERMISSION_CODE = 101
    }


    //private var _image: Bitmap? = null

//    public var image: Bitmap?
//        get() = rowDescriptor.value?.value as? Bitmap?
//        set(value) {
//            if(rowDescriptor.value?.value != value) {
//                rowDescriptor.value = Value(value)
//                requestLayout()
//            }
//        }



    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        this.setOnClickListener {

            onClick(null)
        }
        this.findViewById<TextView>(R.id.textView).setOnClickListener {

            this.performClick()
        }
        this.findViewById<ImageView>(R.id.imageView).setOnClickListener {

            this.performClick()
        }


//        image?.let {
//            _applyBitmap(it)
//        }

        rowDescriptor.cellConfig?.let { config ->
            (config[CellDescriptor.COLOR_LABEL] as? Int?)?.let { labelColor ->
                setTextColor(findViewById<TextView>(R.id.textView), CellDescriptor.COLOR_LABEL)
            }
        }



       rowDescriptor.value?.value?.let { it ->
           val file = it as Uri
           imageUri = file
//           mCurrentPhotoPath = imageUri!!.path

           val context = context //row.sectionDescriptor.formDescriptor.context
           val resolver = context?.applicationContext?.contentResolver

           try {
               val image = MediaStore.Images.Media.getBitmap(resolver, imageUri)
               this.imageView.let { iv ->
              //this.findViewById<ImageView>(R.id.imageView).run {
                   if(iv.drawable == null || (iv.drawable as? BitmapDrawable)?.bitmap != image)  {
                       this@ImageCell.applyBitmap(image)
                   }
               }
           } catch (e: Exception) {
               e.printStackTrace()
           }



       }



    }


    override fun getResource(): Int {
        return R.layout.image_field_cell
    }

    // Done the other directions now
//    override fun onValueChanged(newValue: Value<*>?) {
//        super.onValueChanged(newValue)
//
//        if(newValue?.value is Bitmap) {
//            _applyBitmap(newValue.value as Bitmap)
//        }
//    }


    /**
     * Warning: doesn't set values, just updates the UI.
     */
    public fun applyBitmap(bitmap: Bitmap) {

        //rowDescriptor.value = Value(bitmap)

        mActivity.runOnUiThread {
            this.findViewById<ImageView>(R.id.imageView).apply {

//                if(this@ImageCell.imageUri != null) {
//                    setImageURI(this@ImageCell.imageUri)
//                } else {
                    setImageBitmap(bitmap)
//                }

                this.
//
                colorFilter = null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageTintList = null
                }

                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }

    @Throws(IOException::class)
    fun createImageFile(storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)) : File? {
// Create an image file name
        val imageFileName = "devry_" + System.currentTimeMillis().toString() + "_"
        //val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("InlinedApi")
    override fun onClick(v: View?) {

        imageReceiver?.onReceiveBitmap = { btmp ->
            var bitmap = btmp
            if(imageUri != null ) { // workaround to get larger resolution images
                val ctx = v?.context ?: this@ImageCell.context
                //val bmp = MediaStore.Images.Media.getBitmap(ctx.contentResolver, imageUri)

                if(mCurrentPhotoPath != null) {
                    val file = File(mCurrentPhotoPath) //(imageUri!!.path)

//                val bmp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    imageUri!!.autoRotatedBitmap(ctx.contentResolver)
//                } else {
//                    MediaStore.Images.Media.getBitmap(ctx.contentResolver, imageUri)
//                }

                    val bmp = file.autoRotatedBitmap()

                    file.compressBitmap(0.4F)

                    rowDescriptor.value = (Value(Uri.fromFile(file)))


                    if(bmp != null) {
                        bitmap = bmp
                    }
                } else {

                    Log.e("ImageCell.onClick", "mCurrentPhotoPath is null!")
                }


            }

            if(bitmap != null) {
//                rowDescriptor.value = (Value(bitmap))
                applyBitmap(bitmap)
            }


        }
        imageReceiver?.onReceiveUri = { _uri ->
            //file?.compressBitmap(0.4F) // don't compress a file on the SD card...
            //val bmp = file?.autoRotatedBitmap()

            var uri = _uri

            if(_uri?.isContent() == true) { // image is from a content provider (like google photos). This Uri won't be valid after this activity is destroyed.
                //val bitmap = MediaStore.Images.Media.getBitmap(context.applicationContext.contentResolver, uri)

                val newFile = createImageFile(context.cacheDir)

                val inStream = context.applicationContext.contentResolver.openInputStream(uri)
                val outStream = FileOutputStream(newFile)
                val buf = ByteArray(1024)
                var len = inStream.read(buf)
                while (len > 0) {
                    outStream.write(buf, 0, len)
                    len = inStream.read(buf)
                }
                outStream.close()
                inStream.close()

                uri = Uri.fromFile(newFile)

            }
            rowDescriptor.value = (Value(uri))

            imageUri = uri //Uri.fromFile(file)

        }

        imageReceiver?.initPhotoFromCamera = {
            this@ImageCell.initPhotoFromCamera()
        }
        imageReceiver?.initPhotoFromGallery = {
            this@ImageCell.initPhotoFromGallery()
        }


//        if (checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(mActivity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
//                    CUSTOM_CAMERA_PERMISSION_CODE)
//        } else {

            val policyBuilder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(policyBuilder.build())

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Choose Photo")
            var checked = 0
            builder.setSingleChoiceItems(arrayOf("Camera", "Gallery"), checked) { di, which ->
                checked = which

            }
            builder.setPositiveButton("Choose") { a,b ->
                when(checked) {
                    0 -> {
                        initPhotoFromCamera()
                    }
                    1 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            initPhotoFromGallery()
                        } else {
                            Toast.makeText(context, "Must be at >= Jelly Bean!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }


            builder.show()

     //   }


    }

    fun initPhotoFromCamera() {
        if (checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(mActivity, arrayOf(Manifest.permission.CAMERA),
                    CUSTOM_CAMERA_PERMISSION_CODE)
        } else {


            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)


            if (cameraIntent.resolveActivity(context.packageManager) != null) {
                try {
                    val photoFile = this.createImageFile()
                            ?: throw IOException("Failed to create image file")

                    // make sure to create thsi fileprovider!
                    val uri = FileProvider.getUriForFile(context, ReflectionBuildConfig.APPLICATION_ID(context) +".fileprovider", photoFile)
                    this@ImageCell.imageUri = uri
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                    startActivityForResult(mActivity, cameraIntent, CAMERA_REQUEST, null)


                } catch (ex: IOException) {
                    ex.printStackTrace()
                    Toast.makeText(context, "Failed to create image file", Toast.LENGTH_LONG).show()
                }

            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun initPhotoFromGallery() {
        if (checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(mActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    CUSTOM_GALLERY_PERMISSION_CODE)
        } else {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            //                        intent.type = "image/*"
            //intent.action = Intent.ACTION_PICK
            //intent
            startActivityForResult(mActivity, Intent.createChooser(intent, "Select Picture"), CAMERA_REQUEST, null)
        }
    }


}
