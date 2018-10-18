package com.devrygreenhouses.qmb.rows.push.nested

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.ImageView
import android.widget.TextView
import com.devrygreenhouses.qmb.rows.push.PushHandler

import com.quemb.qmbform.view.FormButtonFieldCell
import com.quemb.qmbform.R

@SuppressLint("ViewConstructor")
/**
 * Created by pmaccamp on 8/28/2015.
 */
class FinishCell<T: NestedElement<*>>(activity: Activity, rowDescriptor: NestedPushRowDescriptor<*>, val handler: NestedPushHandler<T>?, val value: T)
    : FormButtonFieldCell(activity, rowDescriptor) {


//    var filterAdapter: FilterableAdapter?
//    var arrayAdapter: ArrayAdapter<String>?

    init {


        if(handler?.showRadioButton == true)
            this.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_radio)
        else
            this.findViewById<ImageView>(R.id.imageView).setImageDrawable(null)

        this.setOnClickListener {
            if(handler?.showRadioButton == true)
                this.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_radio_checked)
            else
                this.findViewById<ImageView>(R.id.imageView).setImageDrawable(null)

            handler?.onFormRowClick(rowDescriptor)
        }
        this.findViewById<TextView>(R.id.textView).setOnClickListener {
            this.performClick()
        }
        this.findViewById<ImageView>(R.id.imageView).setOnClickListener {
            this.performClick()
        }
    }


    @Suppress("UNNECESSARY_SAFE_CALL") // it's actually necessary.
    override fun afterInit() {
        super.afterInit()
        val r = rowDescriptor
        val roValue = r.value
        val h = handler
        val selected = h?.selected
        val v = value
        if( (handler?.showRadioButton == true) && handler?.selected != null && handler?.selected == value) {
            this.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_radio_checked)
        } else if(handler?.showRadioButton == true && (handler?.selected == null || handler?.selected != value)) {
            this.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_radio)
        } else {
            this.findViewById<ImageView>(R.id.imageView).setImageDrawable(null)
        }
    }

    override fun getResource(): Int {
        return R.layout.finish_field_cell
    }


    override fun update() {

        super.update()

        updateEditView()

    }

    protected fun updateEditView() {

//        val hint = rowDescriptor.getHint(context)
//        if (hint != null) {
//            mEditView.setHint(hint)
//        }

        val value = rowDescriptor.value?.value
        if(handler?.showRadioButton == true && value != null && value == handler?.selected)
            this.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_radio_checked)
        else if(handler?.showRadioButton == true && (value == null || value == handler?.selected)) {
            this.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_radio)
        }
        else
            this.findViewById<ImageView>(R.id.imageView).setImageDrawable(null)

//        if (value != null && value.value != null) {
//            val valueString = value.value
//            mEditView.setText(valueString)
//        }

    }






}
