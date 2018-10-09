package com.devrygreenhouses.qmb.rows.image

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import com.devrygreenhouses.qmb.CustomCellViewFactory
import com.quemb.qmbform.annotation.FormValidator
import com.quemb.qmbform.descriptor.RowDescriptor
import com.quemb.qmbform.view.Cell
import java.io.File
import java.util.ArrayList

open class ImageRowDescriptor(imageTag: String, imageTitle: String, val imageViewReceiver: ImageReceiver?): RowDescriptor<Uri>(), CustomCellViewFactory {
    override fun onViewCreated(cell: Cell) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val TAG = "ImageRowDescriptor"

    init {
        this.mValidators = ArrayList<FormValidator>() as MutableList<FormValidator>?
        this.mTitle = imageTitle
        this.mTag = imageTag
//        this.mRowType = rowType
//        this.value = value
    }

    override fun createView(ctx: Context): Cell {
        //val row = this as RowDescriptor<*>


        val cell = ImageCell(ctx, this, imageViewReceiver)
//        if(this.value?.value != null) {
//            imageViewReceiver?.onReceiveFile?.invoke(this.value?.value)
//        }

        return cell


    }

//
//
//    override fun onViewCreated(cell: Cell) {
//        cell.setTextColor(cell.findViewById<TextView>(R.id.textView), CellDescriptor.PUSH_COLOR_LABEL)
//    }

}