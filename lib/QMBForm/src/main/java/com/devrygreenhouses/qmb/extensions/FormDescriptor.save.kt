package com.devrygreenhouses.qmb.extensions

import android.content.Context
import android.media.Image
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import com.devrygreenhouses.qmb.rows.image.ImageCell
import com.devrygreenhouses.qmb.rows.image.ImageRowDescriptor
import com.quemb.qmbform.descriptor.FormDescriptor
import com.quemb.qmbform.descriptor.RowDescriptor
import com.quemb.qmbform.descriptor.Value
import com.quemb.qmbform.view.FormEditIntegerFieldCell
import com.quemb.qmbform.view.FormEditNumberFieldCell
import com.quemb.qmbform.view.FormEditTextFieldCell
import com.quemb.qmbform.view.FormEditTextInlineFieldCell
import java.lang.RuntimeException
import java.io.*
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.lang.System.`in`


val FormDescriptor.context: Context?
    get() {
        if (this.allRows.count() > 0) {
            for(row in this.allRows) {
                if(row.cell != null) {
                    return row.cell?.context
                }
            }

        }
        return null
    }

var FormDescriptor.saveableValues: MutableMap<String, Any>
    get() {
        val values: HashMap<String,Any> = HashMap()
        for(row in this.allRows) {
//            if(row is ImageRowDescriptor && (row.cell as? ImageCell)?.imageUri != null) {
//                val value = (row.cell as ImageCell).imageUri.toString()
//                values[row.tag] = value
//            } else if(row.value != null) {
                values[row.tag] = row.value?.value.toString()
//            }
        }
        return values
    }

    set(newValues) {
        for (value in newValues) {
            val tag = value.key
            val row = this.findRowDescriptor(tag)

            if(value.value.toString() == "null") {
                continue
            }

            var uriParsed: Uri? = null
            try {
                val uriString = value.value.toString()
                uriParsed = Uri.parse(uriString)
            } catch (e: Exception) {

            }

            if(uriParsed != null && row is ImageRowDescriptor) {
                row.value = Value(uriParsed)
                (row.cell as? ImageCell)?.let {// cell can be null if it's off-screen! When it's onLayout is run, it should load the image from the row.value.
                    setImageUriForCell(it, uriParsed)
                }
            } else if(row.cell != null) {

//                if(row.cell == null) {
//                  continue
//                }
                row.value = Value(value.value)
                when(row.cell) {
                    is FormEditIntegerFieldCell -> row.value = Value(parseInt(value.value.toString()))
                    is FormEditNumberFieldCell -> row.value = Value(parseDouble(value.value.toString()))
                }
            } else {
                row.value = Value(value.value)
            }

            Thread {
                Handler(Looper.getMainLooper()).post {
                    (value.value as? String)?.let { str ->
                        (row.cell as? FormEditTextFieldCell)?.editView?.setText(str)
                    }
                }
            }.start()



        }
    }

private fun setImageUriForCell(cell: ImageCell, uri: Uri) {

    val context = cell.context
    val resolver = context?.applicationContext?.contentResolver

    val image = MediaStore.Images.Media.getBitmap(resolver, uri)

//    var cell: ImageCell? = row.cell as? ImageCell

//    if(row.cell == null) {
//        context?.let {
//            cell = row.createView(it) as ImageCell
//            row.cell = cell
//        }
//    }

    cell.apply {
        imageUri = uri

        mCurrentPhotoPath = uri.path

        if(image != null)
            applyBitmap(image)
    }



    //val file = File(uri.path) // resolver?.openFileDescriptor()

    //row.value = Value(uri)



    Log.d("setImageUriForRow", cell.rowDescriptor.value?.value?.toString())

//    do {
//        val imageData = try Data(contentsOf: value)
//            row.value = UIImage(data: imageData)
//            row.imageURL = value
//        } catch {
//            print("‼️ Form.setImageURLFor(row: \(row.tag ?? "<nil>"), value: \(value.relativeString)): failed to load image")
//        }
}

private val FormDescriptor.cacheDir: File?
    get() {
        val cacheDir = this.context?.applicationContext?.cacheDir ?: return null
        val baseFolder = cacheDir.absolutePath
        return File("$baseFolder/forms")
    }



fun FormDescriptor.load(fileName: String): Boolean {
    if(Looper.getMainLooper().thread == Thread.currentThread()) {
        throw RuntimeException("Cannot call FormDescriptor.load() from the UI thread!")
    }

    var failed = false
    Log.d("FormDescriptor.load()","💾 Form.load($fileName): decoding file")

    val folder = cacheDir
    if(folder == null) {
        Log.d("FormDescriptor.load()","💾 FormDescriptor.load($fileName): failed to load cacheDir")
        return false
    }
    if(!folder.exists()) {
        Log.w("FormDescriptor.load()"," ‼️Form.load($fileName): file doesn't exist!")
        return false
    }

    val file = File(folder, fileName)

    var fis: FileInputStream? = null
    var inStream: ObjectInputStream? = null
    try {
        fis = FileInputStream(file)
        inStream = ObjectInputStream(fis)
        val values = inStream.readObject() as MutableMap<String, Any>
        this.saveableValues = values
        print(this.saveableValues.toString())

//        Thread {
//            Handler(Looper.getMainLooper()).post {
//                for(row in this.allRows) {
//                    row.
//                }
//            }
//        }.start()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        failed = true
    } catch (e: StreamCorruptedException) {
        e.printStackTrace()
        failed = true
    } catch (e: IOException) {
        e.printStackTrace()
        failed = true
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
        failed = true
    } catch (e: Exception) {
        e.printStackTrace()
        failed = true

    } finally {

        try {
            fis?.close()
            inStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }



    if(failed) {
        Log.w("FormDescriptor.load()", "‼️ FormDescriptor.load($fileName): failed to read data")
    }
    return !failed

}

fun FormDescriptor.save(fileName: String): Boolean {
    if(Looper.getMainLooper().thread == Thread.currentThread()) {
        throw RuntimeException("Cannot call FormDescriptor.save() from the UI thread!")
    }

    var failed = false
    Log.d("FormDescriptor.save()","💾 FormDescriptor.save($fileName): archiving values to file")

    val folder = cacheDir
    if(folder == null) {
        Log.d("FormDescriptor.save()","💾 FormDescriptor.save($fileName): failed to load cacheDir")
        return false
    }

    if(!folder.exists()) {
        folder.mkdirs()
    }

    val file = File(folder, fileName)

    var fos: FileOutputStream? = null
    var out: ObjectOutputStream? = null
    try {
        fos = FileOutputStream(file)
        out = ObjectOutputStream(fos)
        val values = saveableValues
        out.writeObject(values)
    } catch (ex: IOException) {
        ex.printStackTrace()
        failed = true
    } catch (e: Exception) {
        e.printStackTrace()
        failed = true
    } finally {
        try {
            fos?.flush()
            fos!!.close()
            out?.flush()
            out!!.close()
        } catch (e: Exception) {

        }

    }

    if(failed) {
        Log.w("FormDescriptor.save()", "‼️ FormDescriptor.save($fileName): failed to write data")
    }
    return !failed

}

//