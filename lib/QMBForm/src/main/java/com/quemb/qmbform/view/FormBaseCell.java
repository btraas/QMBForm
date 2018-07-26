package com.quemb.qmbform.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.quemb.qmbform.R;
import com.quemb.qmbform.descriptor.CellDescriptor;
import com.quemb.qmbform.descriptor.FormItemDescriptor;
import com.quemb.qmbform.descriptor.OnFormRowValueChangedListener;
import com.quemb.qmbform.descriptor.OnValueChangeListener;
import com.quemb.qmbform.descriptor.RowDescriptor;
import com.quemb.qmbform.descriptor.SectionDescriptor;
import com.quemb.qmbform.descriptor.Value;
import com.quemb.qmbform.interfaces.MultiValueListener;

import java.util.HashMap;

/**
 * Created by tonimoeckel on 14.07.14.
 */
public abstract class FormBaseCell extends Cell {


    private static final int REMOVE_BUTTON_ID = R.id.end;
    private static final int ADD_BUTTON_ID = R.id.beginning;

    private MultiValueListener multiValueListener;

    private LinearLayout mMultiValueWrapper;

    public FormBaseCell(Context context, RowDescriptor rowDescriptor) {

        super(context, rowDescriptor);

    }

    @Override
    protected void init() {
        super.init();

        if (getRowDescriptor() != null && getRowDescriptor().getValue() != null) {
            getRowDescriptor().getValue().setOnValueChangeListener(new OnValueChangeListener() {
                @Override
                public void onChange(Object value) {
                    update();
                }
            });
        }


        HashMap<String,Object> cellConfig = getRowDescriptor().getCellConfig();
        if (cellConfig != null && cellConfig.containsKey(CellDescriptor.BACKGROUND_COLOR)) {
            Object config = cellConfig.get(CellDescriptor.BACKGROUND_COLOR);
            if (config instanceof Integer) {
                setBackgroundColor((Integer) config);
            }
        } else {
            setBackgroundResource(R.drawable.row_background);
        }
    }

    protected ViewGroup getSuperViewForLayoutInflation() {

        if (getRowDescriptor().getSectionDescriptor() != null &&
            this.getRowDescriptor().getSectionDescriptor().isMultivalueSection() &&
            !this.getRowDescriptor().getDisabled()) {

            FormItemDescriptor itemDescriptor = getFormItemDescriptor();
            if (itemDescriptor != null) {
                HashMap<String,Object> cellConfig = itemDescriptor.getCellConfig();
                if (cellConfig != null && cellConfig.containsKey(CellDescriptor.MULTI_VALUE_LISTENER)) {
                    multiValueListener = (MultiValueListener) cellConfig.get(CellDescriptor.MULTI_VALUE_LISTENER);
                }
            }

            LinearLayout linearLayout = createMultiValueWrapper();
            addView(linearLayout);
            return linearLayout;
        }

        return super.getSuperViewForLayoutInflation();
    }

    protected LinearLayout createMultiValueWrapper() {

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setId(R.id.wrap_content);
        linearLayout.setFocusable(false);
        linearLayout.setFocusableInTouchMode(false);

        float scale = getResources().getDisplayMetrics().density;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins((int) (5 * scale + 0.5f), 0, 0, 0);

        ImageButton deleteButton = new ImageButton(getContext());
        deleteButton.setId(REMOVE_BUTTON_ID);
        deleteButton.setFocusableInTouchMode(false);
        deleteButton.setFocusable(false);
        deleteButton.setImageResource(R.drawable.ic_action_remove);
        deleteButton.setBackgroundColor(Color.TRANSPARENT);
        deleteButton.setVisibility(VISIBLE);
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Runnable callback = new Runnable() {
                    @Override
                    public void run() {
                        RowDescriptor rowDescriptor = getRowDescriptor();

                        SectionDescriptor sectionDescriptor = rowDescriptor.getSectionDescriptor();
                        sectionDescriptor.removeRow(rowDescriptor);
                        sectionDescriptor.getFormDescriptor().getOnFormRowValueChangedListener().onValueChanged(rowDescriptor, rowDescriptor.getValue(), null);
                    }
                };

                if (multiValueListener != null) {
                    multiValueListener.onDeleteButtonClicked(getRowDescriptor(), callback);
                } else {
                    callback.run();
                }
            }
        });

        linearLayout.addView(deleteButton, params);

        ImageButton addButton = new ImageButton(getContext());
        addButton.setId(ADD_BUTTON_ID);
        addButton.setFocusableInTouchMode(false);
        addButton.setFocusable(false);
        addButton.setImageResource(R.drawable.ic_action_new);
        addButton.setBackgroundColor(Color.TRANSPARENT);
        addButton.setVisibility(GONE);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                SectionDescriptor sectionDescriptor = getRowDescriptor().getSectionDescriptor();
                sectionDescriptor.addRow(RowDescriptor.newInstance(getRowDescriptor()));

            }
        });

        linearLayout.addView(addButton, params);

        SectionDescriptor sectionDescriptor = getRowDescriptor().getSectionDescriptor();

        if (this.getRowDescriptor().getSectionDescriptor().canAddValue()) {
            int index = sectionDescriptor.getIndexOfRowDescriptor(getRowDescriptor());
            if (index == sectionDescriptor.getRowCount() - 1) {
                addButton.setVisibility(VISIBLE);
                deleteButton.setVisibility(GONE);
            } else {
                addButton.setVisibility(GONE);
                deleteButton.setVisibility(VISIBLE);
            }
        } else {
            addButton.setVisibility(GONE);
            deleteButton.setVisibility(VISIBLE);
        }

        mMultiValueWrapper = linearLayout;

        return mMultiValueWrapper;
    }

    @Override
    public boolean shouldAddDivider() {

        RowDescriptor rowDescriptor = (RowDescriptor) getFormItemDescriptor();
        if (rowDescriptor.isLastRowInSection() && !rowDescriptor.getSectionDescriptor().hasFooterTitle())
            return false;

        return super.shouldAddDivider();
    }

    @Override
    public void lastInSection() {


    }

    public RowDescriptor getRowDescriptor() {
        return (RowDescriptor) getFormItemDescriptor();
    }

    public void onValueChanged(Value<?> newValue) {
        RowDescriptor row = getRowDescriptor();
        Value<?> oldValue = row.getValue();
        if (oldValue == null || newValue == null || !newValue.getValue().equals(oldValue.getValue())) {
            OnFormRowValueChangedListener listener = getRowDescriptor().getSectionDescriptor().getFormDescriptor().getOnFormRowValueChangedListener();
            row.setValue(newValue);
            if (listener != null) {
                listener.onValueChanged(row, oldValue, newValue);
            }
        }

    }


}
