package com.quemb.qmbform.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.quemb.qmbform.CellViewFactory;
import com.quemb.qmbform.descriptor.FormDescriptor;
import com.quemb.qmbform.descriptor.FormItemDescriptor;
import com.quemb.qmbform.descriptor.RowDescriptor;
import com.quemb.qmbform.descriptor.SectionDescriptor;
import com.quemb.qmbform.CellViewFactory;
import com.quemb.qmbform.view.Cell;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.quemb.qmbform.descriptor.SectionFooterDescriptor;

import java.util.ArrayList;

/**
 * Created by tonimoeckel on 14.07.14.
 */
public class FormAdapter extends BaseAdapter {

    private FormDescriptor mFormDescriptor;
    private ArrayList<FormItemDescriptor> mItems;
    private Context mContext;
    private Boolean mEnableSectionSeparator;

    public static FormAdapter newInstance(FormDescriptor formDescriptor, Context context) {
        return FormAdapter.newInstance(formDescriptor, context, true);
    }

    public static FormAdapter newInstance(FormDescriptor formDescriptor, Context context, boolean enableSectionSeparator) {
        FormAdapter formAdapter = new FormAdapter();
        formAdapter.mFormDescriptor = formDescriptor;
        formAdapter.mContext = context;
        formAdapter.setEnableSectionSeparator(enableSectionSeparator);
        return formAdapter;
    }

    @Override
    public int getCount() {
        mItems = new ArrayList<FormItemDescriptor>();
        int sectionCount = 1;
        for (SectionDescriptor sectionDescriptor : mFormDescriptor.getSections()){

            if (sectionDescriptor.hasTitle()){
                mItems.add(sectionDescriptor);
            }

            mItems.addAll(sectionDescriptor.getRows());

            if (sectionDescriptor.hasFooterTitle()) {
                mItems.add(sectionDescriptor.getFooterDescriptor());
            }

            if (getEnableSectionSeparator() && sectionCount < mFormDescriptor.getSections().size()) {

                FormItemDescriptor itemDescriptor = mItems.get(mItems.size() - 1);
                if (itemDescriptor instanceof RowDescriptor)
                    ((RowDescriptor) itemDescriptor).setLastRowInSection(true);

                mItems.add(RowDescriptor.newInstance(null, RowDescriptor.FormRowDescriptorTypeSectionSeperator));
            }
            sectionCount++;
        }

        return mItems.size();
    }

    @Override
    public FormItemDescriptor getItem(int position) {

        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        return CellViewFactory.getInstance().createViewForFormItemDescriptor(mContext,getItem(position));
    }

    @Override
    public boolean isEnabled(int position) {
        if (getItem(position) instanceof SectionDescriptor ||
            getItem(position) instanceof SectionFooterDescriptor) {
            return false;
        }
        return super.isEnabled(position);
    }

    public Boolean getEnableSectionSeparator() {
        return mEnableSectionSeparator;
    }

    public void setEnableSectionSeparator(Boolean enableSectionSeperator) {
        mEnableSectionSeparator = enableSectionSeperator;
    }
}
