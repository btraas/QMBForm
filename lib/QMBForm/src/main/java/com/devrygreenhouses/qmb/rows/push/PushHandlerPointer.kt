package com.devrygreenhouses.qmb.rows.push

import java.io.Serializable

class PushHandlerPointer(handler: PushHandler<*>): Serializable {

    companion object {
        private var objects = HashMap<Int, PushHandler<*>>()

        private fun save(obj: PushHandler<*>): Int {
            val hash = obj.hashCode()
            objects[hash] = obj
            return hash
        }
    }

    val address: Int = save(handler)

    fun retrieve(): PushHandler<*>? {
            if(objects.contains(address)) return objects[address]
            return null
    }

    fun free() {
        objects.remove(address)
    }

    override fun equals(other: Any?): Boolean {
        if(other !is PushHandlerPointer) return false
        return other.address == this.address
    }

    override fun hashCode(): Int {
        return address.hashCode()
    }

    override fun toString(): String {
        return address.toString()
    }

}