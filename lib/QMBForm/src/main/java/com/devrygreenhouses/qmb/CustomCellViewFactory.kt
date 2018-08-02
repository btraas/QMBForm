package com.devrygreenhouses.qmb

import android.content.Context
import com.quemb.qmbform.view.Cell

interface CustomCellViewFactory {
    fun createView(ctx: Context): Cell
}