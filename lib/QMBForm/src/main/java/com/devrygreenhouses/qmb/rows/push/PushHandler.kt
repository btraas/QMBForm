package com.devrygreenhouses.qmb.rows.push

import android.app.Activity
import android.support.annotation.CallSuper
import android.util.Log
import android.widget.ListView
import com.quemb.qmbform.FormManager
import com.quemb.qmbform.OnFormRowClickListener
import com.quemb.qmbform.descriptor.*
import java.io.Serializable


abstract class PushHandler(val oldActivity: Activity, val title: String): Serializable, OnFormRowValueChangedListener, OnFormRowClickListener {

    private val TAG = "PushHandler"

    var mFormManager: FormManager? = null
    var form: FormDescriptor? = null

//    var oldActivity: Activity? = null
    var newActivity: CustomFormActivity? = null
    var selectedValue: Value<*>? = null

    var onPresentCallback: ((Activity, CustomFormActivity) -> Unit)? = null

//    abstract class NestedElementDelegate {
//        abstract fun onSelect(formManager: FormManager)
//        abstract fun onFinish()
//        abstract fun getActivity(): Activity
//        abstract fun saveForm(rows: List<FormDescriptor>)
//        abstract fun loadForm(): List<FormDescriptor>
//    }

    //abstract fun onSelect // moved to OnFormRowValueChangedListener
    //abstract fun onFinish() // moved to OnFormRowValueChangedListener
    //abstract fun getActivity() // moved to oldActivity

    var _rows: List<RowDescriptor<*>> = ArrayList()

    abstract protected fun buildForm(): FormDescriptor

    fun generate(activity: CustomFormActivity, listView: ListView) {

        newActivity = activity

        Log.d(TAG, "building form")


        //this.newActivity = activity
        form = buildForm()

        Log.d(TAG, "showing form")

        mFormManager = FormManager()
        mFormManager?.setup(form, listView, activity)
        mFormManager?.setOnFormRowClickListener(this)
        mFormManager?.setOnFormRowValueChangedListener(this)


        Log.d(TAG, "invoking callback")


        onPresentCallback?.invoke(oldActivity, activity)


    }


    @CallSuper
    override fun onValueChanged(rowDescriptor: RowDescriptor<*>?, oldValue: Value<*>?, newValue: Value<*>?) {
        selectedValue = newValue
        newActivity?.finish()
    }



}