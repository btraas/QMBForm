package com.devrygreenhouses.qmb.rows.image

import android.graphics.Bitmap
import android.widget.ImageView

interface ImageReceiver {
    var onReceiveBitmap: ((Bitmap?) -> Unit)?
}