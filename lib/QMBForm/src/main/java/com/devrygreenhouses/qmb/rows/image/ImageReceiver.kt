package com.devrygreenhouses.qmb.rows.image

import android.graphics.Bitmap
import android.widget.ImageView
import java.io.File

interface ImageReceiver {
    var onReceiveBitmap: ((Bitmap?) -> Unit)?
    var onReceiveFile: ((File?) -> Unit)?

    var initPhotoFromCamera: (() -> Unit)?
    var initPhotoFromGallery: (() -> Unit)?
}