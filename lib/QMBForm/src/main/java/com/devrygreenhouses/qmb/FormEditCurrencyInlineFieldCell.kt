package com.quemb.qmbform.view

import android.annotation.SuppressLint
import com.quemb.qmbform.descriptor.RowDescriptor
import com.quemb.qmbform.descriptor.Value

import android.content.Context
import android.util.Log

import java.text.NumberFormat
import java.text.ParseException

/**
 * Created by tonimoeckel on 15.07.14.
 */
class FormEditCurrencyInlineFieldCell(context: Context,
                                rowDescriptor: RowDescriptor<*>) : FormEditNumberInlineFieldCell(context, rowDescriptor) {

    var previousValue: String? = null

    override fun init() {
        super.init()
        editView.setText(NumberFormat.getCurrencyInstance().currency.symbol)
        editView.setSelection(1)
        editView.onFocusChangeListener = OnFocusChangeListener { view, bool ->
            if(editView.selectionEnd == 0)
                editView.setSelection(1)
        }
    }

    override fun onCellSelected() {
        super.onCellSelected()
        editView.setSelection(1)
    }


    override fun updateEditView() {

        try {
            val value = rowDescriptor.value as Value<Number>?
            if (value != null && value.value != null) {
                val format = NumberFormat.getCurrencyInstance()
                val valueString = format.format(value.value)//String.valueOf(value.getValue());
                editView.setText(valueString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }



    }


    @SuppressLint("SetTextI18n")
    override fun onEditTextChanged(string: String) {

        val symbol = NumberFormat.getCurrencyInstance().currency.symbol

        val cleaned = string.replace(symbol, "") //.removePrefix(symbol)

        if(cleaned == previousValue) {
            return
        }

        previousValue = cleaned

        this.editView.setText(symbol + cleaned)
        this.editView.setSelection((symbol+cleaned).length)

        try {
            val format = NumberFormat.getCurrencyInstance()
            val floatValue = format.parse(symbol) as Number
            onValueChanged(Value(symbol + floatValue))
        } catch (e: NumberFormatException) {
            Log.e(TAG, e.message, e)
            onValueChanged(Value(symbol + cleaned))
        } catch (e: ParseException) {
            Log.e(TAG, e.message, e)
            onValueChanged(Value(symbol + cleaned))
        } catch (e: ClassCastException) {
            Log.e(TAG, e.message, e)
        }




    }

    companion object {

        private val TAG = "CurrencyInlineFieldCell"
    }

}
