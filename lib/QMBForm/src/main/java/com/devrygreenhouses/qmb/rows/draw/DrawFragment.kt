package com.devrygreenhouses.qmb.rows.draw

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class DrawFragment: Fragment() {
    var drawView: DrawingView? = null

    var onSave: ((Bitmap) -> Unit)? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        drawView = DrawingView(context)

        return drawView

    }

    override fun onPause() {

        val bitmap = drawView!!.bitmapFromView
        onSave?.invoke(bitmap)


        super.onPause()
    }


}