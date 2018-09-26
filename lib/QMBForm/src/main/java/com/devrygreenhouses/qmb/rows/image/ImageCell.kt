package com.devrygreenhouses.qmb.rows.image

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView

import com.quemb.qmbform.view.FormButtonFieldCell
import com.quemb.qmbform.R
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.view.View
import android.support.v7.app.AlertDialog
import com.devrygreenhouses.qmb.rows.image.ImageReceiver
import com.devrygreenhouses.qmb.rows.image.ImageRowDescriptor
import com.quemb.qmbform.descriptor.CellDescriptor
import com.quemb.qmbform.descriptor.Value
import kotlinx.android.synthetic.main.finish_field_cell.view.*
import java.io.File
import android.os.StrictMode.VmPolicy
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat.*
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.widget.Toast
import com.devrygreenhouses.qmb.extensions.autoRotatedBitmap
import com.devrygreenhouses.qmb.extensions.compressBitmap
import com.quemb.qmbform.BuildConfig
import java.io.IOException


@SuppressLint("ViewConstructor")
open
/**
 * Created by pmaccamp on 8/28/2015.
 */
class ImageCell(context: Context, rowDescriptor: ImageRowDescriptor, val imageReceiver: ImageReceiver?)
    : FormButtonFieldCell(context, rowDescriptor) {

    var mCurrentPhotoPath: String? = null
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


    protected fun _applyBitmap(bitmap: Bitmap) {

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
    fun createImageFile() : File? {
// Create an image file name
        val imageFileName = "merch_" + System.currentTimeMillis().toString() + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    @SuppressLint("InlinedApi")
    override fun onClick(v: View?) {

        imageReceiver?.onReceiveBitmap = { btmp ->
            var bitmap = btmp
            if(imageUri != null) { // workaround to get larger resolution images
                val ctx = v?.context ?: this@ImageCell.context
                //val bmp = MediaStore.Images.Media.getBitmap(ctx.contentResolver, imageUri)

                val file = File(mCurrentPhotoPath) //(imageUri!!.path)

//                val bmp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    imageUri!!.autoRotatedBitmap(ctx.contentResolver)
//                } else {
//                    MediaStore.Images.Media.getBitmap(ctx.contentResolver, imageUri)
//                }

                val bmp = file.autoRotatedBitmap()

                file.compressBitmap(0.4F)

                rowDescriptor.value = (Value(file))


                if(bmp != null) {
                    bitmap = bmp
                }
            }

            if(bitmap != null) {
//                rowDescriptor.value = (Value(bitmap))
                _applyBitmap(bitmap)
            }


        }
        imageReceiver?.onReceiveFile = { file ->
            file?.compressBitmap(0.4F)
            //val bmp = file?.autoRotatedBitmap()
            rowDescriptor.value = (Value(file))

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

                    val uri = FileProvider.getUriForFile(context, "com.devrygreenhouses.merchandisingcarttracker.fileprovider", photoFile)
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
