package com.quemb.qmbform.view;

import android.content.Context;

import com.quemb.qmbform.descriptor.CellDescriptor;
import com.quemb.qmbform.descriptor.FormItemDescriptor;
import com.quemb.qmbform.descriptor.RowDescriptor;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by tonimoeckel on 15.07.14.
 */
public class FormTimeDialogFieldCell extends FormTimeFieldCell implements TimePickerDialog.OnTimeSetListener {

    private Calendar mCalendar;

    public FormTimeDialogFieldCell(Context context,
                                   RowDescriptor rowDescriptor) {
        super(context, rowDescriptor);
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

        TimePickerDialog dialog = TimePickerDialog.newInstance(
                this,
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                false
        );

        if (cellConfig != null &&
            cellConfig.containsKey(CellDescriptor.TIME_PICKER_HOUR_INTERVAL) &&
            cellConfig.containsKey(CellDescriptor.TIME_PICKER_MINUTE_INTERVAL)) {

            dialog.setTimeInterval((int)cellConfig.get(CellDescriptor.TIME_PICKER_HOUR_INTERVAL),
                                    (int)cellConfig.get(CellDescriptor.TIME_PICKER_MINUTE_INTERVAL));

        }

        dialog.show(mActivity.getFragmentManager(), "TimepickerDialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        onDateChanged(calendar.getTime());
    }

    public Calendar getCalendar() {
        return mCalendar;
    }
}
