package com.devrygreenhouses.qmb.rows.push.nested

import com.quemb.qmbform.descriptor.Value

interface NestedElement<T> {

    enum class Traversal {
        DEEP, SHALLOW
    }

    val title: String
    var value: Value<T>

    fun getSubFolders(): List<NestedElement<T>>
    fun getSimpleRows(): List<NestedElement<T>>
    fun getShallowChildren(): List<NestedElement<T>>
    //fun getDeepChildren(): Set<NestedElement> // set because it should be unique and order doesn't matter
//    abstract fun getCustomFormSection(delegate: NestedElementDelegate): SectionDescriptor?

    fun isFolder(): Boolean {
        val children = getShallowChildren()
        return children.count() > 0
    }

    fun filterSimple(objects: List<NestedElement<T>>): List<NestedElement<T>> {
        var output = ArrayList<NestedElement<T>>()
        for(_object in objects) {
            if(!_object.isFolder()) {
                output.add(_object)
            }
        }
        return output
    }

    companion object {
        fun <T> filterFolders(objects: List<NestedElement<T>>): List<NestedElement<T>> {
            var output = ArrayList<NestedElement<T>>()
            for (_object in objects) {
                if (!_object.isFolder()) {
                    output.add(_object)
                }
            }
            return output
        }
    }

}