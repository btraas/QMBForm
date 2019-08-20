package com.devrygreenhouses.qmb.rows.push.fragment

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.*
import androidx.fragment.app.Fragment
import com.devrygreenhouses.qmb.CustomCellViewFactory
import com.devrygreenhouses.qmb.rows.push.CustomFragmentActivity
import com.devrygreenhouses.qmb.rows.push.PushCell
import com.devrygreenhouses.qmb.rows.push.PushRowDescriptor
import com.quemb.qmbform.annotation.FormValidator
import com.quemb.qmbform.view.Cell
import java.util.ArrayList
import com.quemb.qmbform.R
import com.quemb.qmbform.descriptor.Value

/**
 * Used for showing a custom fragment. Not quite sure what to return yet.
 *
 * This is used for scanning.
 *
 */
open class FragmentPushRowDescriptor<T: Fragment>(tag: String, title: String, val subtitle: String?, val icon: Drawable?, activity: Activity,
                                                  handler: FragmentPushHandler)
    : PushRowDescriptor<T>(tag, title, activity, handler), CustomCellViewFactory, Cloneable {

//    var createCallback: ((Activity, Activity) -> Unit)? = null

    init {
        this.mValidators = ArrayList<FormValidator>() as MutableList<FormValidator>?
        this.mTitle = title
        this.mTag = tag
//        this.mRowType = rowType
//        this.value = value
    }

    override fun createView(ctx: Context): Cell {
        //val row = this as RowDescriptor<*>


        val cell = PushCell(activity, CustomFragmentActivity::class, this, handler, icon, subtitle)

        cell.findViewById<TextView>(R.id.textView).apply {
            text = title
        }

        return cell


    }

    override fun setValue(value: Value<T>?) {
//        Log.d("NestedPushRowDescriptor", "setValue(${value?.value})")
//
//        super.setValue(value)
//        val str = value?.value?.toString()
//        if(this.cell !is PushCell<*>) {
//            Log.w("NestedPushRowDescriptor", "setValue() called on a "+this.cell.javaClass.simpleName)
//            return
//        }
//        val cell = this.cell as PushCell<T>
//        val valueTextView = (cell).findViewById<TextView>(R.id.value)
//        activity.runOnUiThread {
//            Log.d("NestedPushRowDescriptor", this.cell.javaClass.simpleName.toString()+"($title).findViewById(R.id.value).text = \"${str}\"")
//
//            valueTextView!!.text = "$str"
//            valueTextView.invalidate()
//            Log.d("NestedPushRowDescriptor", "Actual: " + this.cell.findViewById<TextView>(R.id.value).text.toString())
//
//            //this.cell = cell
//            //cell?.findViewById<TextView>(R.id.textView)?.text = str
//        }
    }



}