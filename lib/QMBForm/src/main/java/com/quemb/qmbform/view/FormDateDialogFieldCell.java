package com.quemb.qmbform.view;

import android.content.Context;

import com.quemb.qmbform.R;
import com.quemb.qmbform.descriptor.CellDescriptor;
import com.quemb.qmbform.descriptor.FormItemDescriptor;
import com.quemb.qmbform.descriptor.RowDescriptor;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by tonimoeckel on 15.07.14.
 */
public class FormDateDialogFieldCell extends FormDateFieldCell implements DatePickerDialog.OnDateSetListener{

    private Calendar mCalendar;

    public FormDateDialogFieldCell(Context context,
                                   RowDescriptor rowDescriptor) {
        super(context, rowDescriptor);
    }


    @Override
    protected int getResource() {
        return R.layout.date_field_cell;
    }


    @Override
    protected void initDatePicker(Calendar calendar) {

        mCalendar = calendar;
    }

    @Override
    public void onCellSelected() {
        super.onCellSelected();

        HashMap<String,Object> cellConfig = null;
        FormItemDescriptor itemDescriptor = getFormItemDescriptor();
        if (itemDescriptor != null) {
            cellConfig = itemDescriptor.getCellConfig();
        }

        DatePickerDialog dialog = DatePickerDialog.newInstance(
                this,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
        );

        if (cellConfig != null) {
            if (cellConfig.containsKey(CellDescriptor.DATE_PICKER_MIN_DATE)) {
                Date minDate = (Date) cellConfig.get(CellDescriptor.DATE_PICKER_MIN_DATE);
                Calendar minDateCalendar = Calendar.getInstance();
                minDateCalendar.setTime(minDate);
                dialog.setMinDate(minDateCalendar);
            }

            if (cellConfig.containsKey(CellDescriptor.DATE_PICKER_MAX_DATE)) {
                Date maxDate = (Date) cellConfig.get(CellDescriptor.DATE_PICKER_MAX_DATE);
                Calendar maxDateCalendar = Calendar.getInstance();
                maxDateCalendar.setTime(maxDate);
                dialog.setMaxDate(maxDateCalendar);
            }
        }

        dialog.show(mActivity.getFragmentManager(), "DatepickerDialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        Date date = new Date(calendar.getTimeInMillis());

        onDateChanged(date);
    }
}
