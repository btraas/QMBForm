package com.devrygreenhouses.qmb.rows.push

import android.app.Activity
import com.quemb.qmbform.descriptor.*
import java.io.Serializable


abstract class PushHandler<NewActivityT: Activity>(val oldActivity: Activity,
                                                val title: String, val valueChangedListener: OnFormRowValueChangedListener)
    : Serializable {

    private val TAG = "PushHandler"

    var newActivity: Activity? = null



//    var oldActivity: Activity? = null

    //var onPresentCallback: ((Activity, CustomFormActivity) -> Unit)? = null


    //abstract protected fun buildForm(): FormDescriptor

    abstract fun onPresent(oldActivity: Activity, newActivity: NewActivityT)
    abstract fun generate(activity: NewActivityT)


}