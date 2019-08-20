package com.devrygreenhouses.qmb.rows.draw

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.devrygreenhouses.qmb.rows.image.ImageCell
import com.devrygreenhouses.qmb.rows.image.ImageReceiver
import com.devrygreenhouses.qmb.rows.image.ImageRowDescriptor
import com.quemb.qmbform.R
import com.quemb.qmbform.view.Cell
import kotlinx.android.synthetic.main.finish_field_cell.view.*

class DrawRowDescriptor(val activity: Activity, tag: String, title: String): ImageRowDescriptor(tag, title, null) {
    override fun createView(ctx: Context): Cell {
        //val row = this as RowDescriptor<*>


        val cell = DrawCell(activity, this)

        cell.findViewById<ImageView>(R.id.imageView).setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_edit))

        return cell


    }
}