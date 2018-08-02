package com.devrygreenhouses.qmb.rows.push

import android.app.Activity
import android.content.Context
import android.widget.*
import com.devrygreenhouses.qmb.CustomCellViewFactory
import com.quemb.qmbform.annotation.FormValidator
import com.quemb.qmbform.descriptor.RowDescriptor
import com.quemb.qmbform.view.Cell
import java.util.ArrayList
import com.quemb.qmbform.R

/**
 * Can't pass element because there may not be a root element.
 *
 */
class PushRowDescriptor<T: NestedElement>(tag: String, title: String, val activity: Activity,
                                                                             val handler: NestedPushHandler<T>, val rootElement: NestedElement)
    : RowDescriptor<T>(), CustomCellViewFactory {

//    var createCallback: ((Activity, Activity) -> Unit)? = null

    init {
        this.mValidators = ArrayList<FormValidator>()
        this.mTitle = title
        this.mTag = tag
//        this.mRowType = rowType
        this.value = value
    }

    override fun createView(ctx: Context): Cell {
        //val row = this as RowDescriptor<*>


        val cell = handler.createViewForElement(this, rootElement) //when {

//            rootElement.isFolder() -> PushCell(activity, this, handler)
//            else -> FinishCell(activity, this, handler)
//        }
//

        cell.findViewById<TextView>(R.id.textView).text = title

        return cell

    }

    fun isFolder(obj: T, getShallowChildren: ((T) -> List<T>)): Boolean {
        val children = getShallowChildren(obj)
        return children.count() > 0
    }

    fun filterFolders(objects: List<T>, getShallowChildren: ((T) -> List<T>)): List<T> {
        return objects.filter { isFolder(it, getShallowChildren) }
    }

    fun filterSimple(objects: List<T>, getShallowChildren: ((T) -> List<T>)): List<T> {
        return objects.filter { !isFolder(it, getShallowChildren) }
    }



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