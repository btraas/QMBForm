package com.devrygreenhouses.qmb.rows.image

import android.Manifest
import android.annotation.SuppressLint
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
import android.support.v4.app.ActivityCompat.requestPermissions
import android.view.View
import android.support.v7.app.AlertDialog
import com.devrygreenhouses.qmb.rows.image.ImageReceiver
import com.devrygreenhouses.qmb.rows.image.ImageRowDescriptor
import com.quemb.qmbform.descriptor.CellDescriptor
import com.quemb.qmbform.descriptor.Value
import kotlinx.android.synthetic.main.finish_field_cell.view.*


@SuppressLint("ViewConstructor")
open
/**
 * Created by pmaccamp on 8/28/2015.
 */
class ImageCell(context: Context, rowDescriptor: ImageRowDescriptor, val imageReceiver: ImageReceiver?)
    : FormButtonFieldCell(context, rowDescriptor) {



    companion object {
        public const val IMAGE_RECEIVED = "image_received"
        public const val CAMERA_REQUEST = 1888
        public const val CUSTOM_CAMERA_PERMISSION_CODE = 100
    }


    //private var _image: Bitmap? = null

    public var image: Bitmap?
        get() = rowDescriptor.value?.value as? Bitmap?
        set(value) {
            if(rowDescriptor.value?.value != value) {
                rowDescriptor.value = Value(value)
                requestLayout()
            }
        }



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


        image?.let {
            _applyBitmap(it)
        }

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

    override fun onClick(v: View?) {

        imageReceiver?.onReceiveBitmap = { bitmap ->
            if(bitmap != null) {
                rowDescriptor.value = (Value(bitmap))
                _applyBitmap(bitmap)
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


            builder.show()

        }


    }




}
