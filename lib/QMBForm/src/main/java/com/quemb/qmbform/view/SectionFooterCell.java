package com.quemb.qmbform.view;

import android.content.Context;
import android.widget.TextView;

import com.quemb.qmbform.R;
import com.quemb.qmbform.descriptor.CellDescriptor;
import com.quemb.qmbform.descriptor.SectionFooterDescriptor;

/**
 * Created by tonimoeckel on 15.07.14.
 */
public class SectionFooterCell extends Cell {

    //private SectionFooterDescriptor mSectionDescriptor;

    private TextView mTextView;

    public SectionFooterCell(Context context,
                             SectionFooterDescriptor sectionDescriptor) {
        super(context, sectionDescriptor);
    }


    @Override
    protected void init() {

        super.init();

        setClickable(false);
        setEnabled(false);

        mTextView = (TextView) findViewById(R.id.textView);

        setStyleId(mTextView, CellDescriptor.APPEARANCE_FOOTER, CellDescriptor.FOOTER_COLOR_LABEL);
    }

    @Override
    protected int getResource() {
        return R.layout.section_footer_cell;
    }

    @Override
    protected void update() {

        String title = getFormItemDescriptor().getTitle();
        mTextView.setText(title);

    }

    @Override
    public boolean shouldAddDivider() {
        return false;
    }

    public SectionFooterDescriptor getSectionDescriptor() {
        return (SectionFooterDescriptor) getFormItemDescriptor();
    }

}
