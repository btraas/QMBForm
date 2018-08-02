package com.devrygreenhouses.qmb.rows.push

interface NestedElement {

    enum class Traversal {
        DEEP, SHALLOW
    }

    val title: String

    fun getSubFolders(): List<NestedElement>
    fun getSimpleRows(): List<NestedElement>
    fun getShallowChildren(): List<NestedElement>
    fun getDeepChildren(): Set<NestedElement> // set because it should be unique and order doesn't matter
//    abstract fun getCustomFormSection(delegate: NestedElementDelegate): SectionDescriptor?

    fun isFolder(): Boolean {
        val children = getShallowChildren()
        return children.count() > 0
    }

    fun filterSimple(objects: List<NestedElement>): List<NestedElement> {
        var output = ArrayList<NestedElement>()
        for(_object in objects) {
            if(!_object.isFolder()) {
                output.add(_object)
            }
        }
        return output
    }

    companion object {
        fun filterFolders(objects: List<NestedElement>): List<NestedElement> {
            var output = ArrayList<NestedElement>()
            for (_object in objects) {
                if (!_object.isFolder()) {
                    output.add(_object)
                }
            }
            return output
        }
    }

}