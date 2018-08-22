package com.devrygreenhouses.qmb.rows.image

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.widget.ImageView
import android.widget.TextView

import com.quemb.qmbform.view.FormButtonFieldCell
import com.quemb.qmbform.R
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.app.ActivityCompat.requestPermissions
import android.view.View
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.app.AlertDialog


@SuppressLint("ViewConstructor")
/**
 * Created by pmaccamp on 8/28/2015.
 */
class ImageCell(context: Context, rowDescriptor: ImageRowDescriptor, val imageReceiver: ImageReceiver)
    : FormButtonFieldCell(context, rowDescriptor) {



    companion object {
        public const val IMAGE_RECEIVED = "image_received"
        public const val CAMERA_REQUEST = 1888
        public const val CUSTOM_CAMERA_PERMISSION_CODE = 100
    }

//    private val cameraReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//
//            val photo = intent?.extras?.get("data") as? Bitmap?
//            receivedBitmap(context,photo)
//        }
//
//    }





//    var filterAdapter: FilterableAdapter?
//    var arrayAdapter: ArrayAdapter<String>?


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        this.setOnClickListener {

            onClick(null)
        }
        this.findViewById<TextView>(R.id.textView).setOnClickListener {

            this.performClick()
        }
        this.findViewById<ImageView>(R.id.imageView).setOnClickListener {

            this.performClick()
        }

//        this.findViewById<View>(R.id.cell).setOnClickListener {
//            Toast.makeText(mActivity, "on cell click", Toast.LENGTH_LONG).show()
//
//            this.performClick()
//        }



    }

    override fun getResource(): Int {
        return R.layout.image_field_cell
    }

    override fun onClick(v: View?) {

        //Toast.makeText(mActivity, "should open camera now", Toast.LENGTH_LONG).show()

//        val intentFilter = IntentFilter(IMAGE_RECEIVED)
//        LocalBroadcastManager.getInstance(mActivity).registerReceiver(cameraReceiver, intentFilter)

        imageReceiver.onReceiveBitmap = { bitmap ->
            if(bitmap != null) {
                mActivity.runOnUiThread {
                    this.findViewById<ImageView>(R.id.imageView).apply {

                        setImageBitmap(bitmap)
                        colorFilter = null
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            imageTintList = null
                        }

                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                }
            }
        }


        if (checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(mActivity, arrayOf(Manifest.permission.CAMERA),
                    CUSTOM_CAMERA_PERMISSION_CODE)
        } else {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Choose Photo")
            var checked = 0
            builder.setSingleChoiceItems(arrayOf("Camera", "Gallery"), checked) { di, which ->
                checked = which

            }
            builder.setPositiveButton("Choose") { a,b ->
                when(checked) {
                    0 -> {
                        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(mActivity, cameraIntent, CAMERA_REQUEST, null)
                    }
                    1 -> {
                        val intent = Intent()
                        intent.type = "image/*"
                        intent.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(mActivity, Intent.createChooser(intent, "Select Picture"), CAMERA_REQUEST, null)
                    }
                }
            }
//            builder.setPositiveButton("Camera") { a,b ->
//                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
//                startActivityForResult(mActivity, cameraIntent, CAMERA_REQUEST, null)
//
//            }
//            builder.setNeutralButton("Gallery"){ a,b ->
//                val intent = Intent()
//                intent.type = "image/*"
//                intent.action = Intent.ACTION_GET_CONTENT
//                startActivityForResult(mActivity, Intent.createChooser(intent, "Select Picture"), CAMERA_REQUEST, null)
//            }


            builder.show()

        }





    }
//
//    fun receivedBitmap(context: Context?, bitmap: Bitmap?) {
//
//        if(bitmap != null) {
//            mActivity.runOnUiThread {
//                this.findViewById<ImageView>(R.id.imageView).apply {
//                    setImageBitmap(bitmap)
//                    scaleType = ImageView.ScaleType.CENTER_CROP
//                }
//            }
//        }
//
//    }

//
//    override fun update() {
//
//        super.update()
//
//        updateEditView()
//
////        if (rowDescriptor.disabled!!) {
////            mEditView.setEnabled(false)
////            setTextColor(mEditView, CellDescriptor.COLOR_VALUE_DISABLED)
////        } else
////            mEditView.setEnabled(true)
//
//    }
//
//    protected fun updateEditView() {
//
////        val hint = rowDescriptor.getHint(context)
////        if (hint != null) {
////            mEditView.setHint(hint)
////        }
//
//
//
//        val value = rowDescriptor.value?.value?.toString() ?: "" // as Value<String>
//        this.findViewById<TextView>(R.id.value).text = value
////        if (value != null && value.value != null) {
////            val valueString = value.value
////            mEditView.setText(valueString)
////        }
//
//    }





}
