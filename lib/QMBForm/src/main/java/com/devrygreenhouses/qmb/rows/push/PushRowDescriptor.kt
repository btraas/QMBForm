package com.devrygreenhouses.qmb.rows.push

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.*
import com.devrygreenhouses.qmb.CustomCellViewFactory
import com.devrygreenhouses.qmb.rows.push.nested.NestedElement
import com.devrygreenhouses.qmb.rows.push.nested.NestedPushHandler
import com.quemb.qmbform.CellViewFactory
import com.quemb.qmbform.annotation.FormValidator
import com.quemb.qmbform.descriptor.RowDescriptor
import com.quemb.qmbform.view.Cell
import java.util.ArrayList
import com.quemb.qmbform.R
import com.quemb.qmbform.descriptor.CellDescriptor
import com.quemb.qmbform.descriptor.Value

/**
 * Can't pass element because there may not be a root element.
 *
 */
abstract class PushRowDescriptor<T>(tag: String, title: String, val activity: Activity,
                                                      val handler: PushHandler<*>)
    : RowDescriptor<T>(), CustomCellViewFactory, Cloneable {

//    var createCallback: ((Activity, Activity) -> Unit)? = null

    init {
        this.mValidators = ArrayList<FormValidator>() as MutableList<FormValidator>?
        this.mTitle = title
        this.mTag = tag
//        this.mRowType = rowType
//        this.value = value
    }

    override fun onViewCreated(cell: Cell) {
        cell.setTextColor(cell.findViewById<TextView>(R.id.textView), CellDescriptor.PUSH_COLOR_LABEL)
    }



//    override fun setValue(value: Value<T>?) {
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
//    }

//    fun isFolder(obj: T, getShallowChildren: ((T) -> List<T>)): Boolean {
//        val children = getShallowChildren(obj)
//        return children.count() > 0
//    }
//
//    fun filterFolders(objects: List<T>, getShallowChildren: ((T) -> List<T>)): List<T> {
//        return objects.filter { isFolder(it, getShallowChildren) }
//    }
//
//    fun filterSimple(objects: List<T>, getShallowChildren: ((T) -> List<T>)): List<T> {
//        return objects.filter { !isFolder(it, getShallowChildren) }
//    }



//    fun addNested(element: T) {
//        val subFolders = element.getSubFolders() as List<T>
//        val simpleRows = element.getSimpleRows() as List<T>
//
//        return addNestedCustom(subFolders, simpleRows, {
//            it.getShallowChildren() as List<T>
//        }, {
//            it.getDeepChildren() as List<T>
//        }, {
//            null
//        })
//
//    }
//
//    fun addNestedCustom(subFolders: List<T>, simpleRows: List<T>,
//                        getShallowChildren: (T) -> List<T>, getDeepChildren: (T) -> List<T>,
//                        getCustomForm: () -> FormDescriptor?
//    ) {
//
//        handler.onPresentCallback = { oldActivity, newActivity ->
//
//            handler
//
//            val form = handler.form!!
//
//
//
//            if(subFolders.isNotEmpty()) {
//                val
//            }
//
//        }
//
//
//    }




}