package com.quemb.qmbform.descriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tonimoeckel on 14.07.14.
 */
public class SectionDescriptor extends FormItemDescriptor {

    private FormDescriptor mFormDescriptor;
    private ArrayList<RowDescriptor> mRows;
    private Boolean mMultivalueSection = false;
    private Boolean mCanAddValue = true;
    private String mFooterTitle = null;
    private MultiValueDelegate mMultiValueDelegate;

    public static SectionDescriptor newInstance(String tag) {

        return SectionDescriptor.newInstance(tag, null);

    }

    public static SectionDescriptor newInstance(String tag, String title) {

        SectionDescriptor descriptor = new SectionDescriptor();
        descriptor.mTitle = title;
        descriptor.mTag = tag;
        return descriptor;

    }

    public SectionDescriptor() {

        mRows = new ArrayList<RowDescriptor>();

    }

    public FormDescriptor getFormDescriptor() {
        return mFormDescriptor;
    }

    public void setFormDescriptor(FormDescriptor formDescriptor) {
        mFormDescriptor = formDescriptor;
    }

    public String getTitle() {
        return mTitle;
    }

    public void addRow(RowDescriptor row) {
        addRow(row, mRows.size());
    }

    public void addRow(RowDescriptor row, int index) {
        insertRowAtIndex(row, index);

        // Propagate the CellConfig from Section to Row

        HashMap<String, Object> cellConfig = getCellConfig();
        if (cellConfig != null)
            row.setCellConfig(cellConfig);
    }

    public void addRow(RowDescriptor row, HashMap<String, Object> cellConfig) {
        addRow(row, mRows.size());

        if (cellConfig != null)
            row.setCellConfig(cellConfig);
    }

    public void removeRow(RowDescriptor row) {
        int index = mRows.indexOf(row);
        removeRowAtIndex(index);
    }

    public int getRowCount() {
        if (hasFooterTitle()) {
            return mRows.size() + 1;
        } else {
            return mRows.size();
        }
    }

    public List<RowDescriptor> getRows() {
        return mRows;
    }

    private void insertRowAtIndex(RowDescriptor row, int index) {
        if (mRows.size() >= index) {
            row.setSectionDescriptor(this);
            mRows.add(index, row);
            if (getFormDescriptor() != null) {
                getFormDescriptor().didInsertRow(row, this);
            }

            if (mMultiValueDelegate != null) {
                mMultiValueDelegate.onAddedRow(row);
            }
        }
    }

    private void removeRowAtIndex(int index) {
        if (index > -1 && index < mRows.size()) {
            RowDescriptor rowDescriptor = mRows.get(index);
            mRows.remove(index);
            if (getFormDescriptor() != null) {
                getFormDescriptor().didRemoveRow(rowDescriptor, this);
            }
        }
    }

    public boolean hasTitle() {
        return getTitle() != null && getTitle().length() > 0;
    }

    public RowDescriptor findRowDescriptor(String tag) {
        RowDescriptor rowDescriptor = null;

        for (RowDescriptor iRowDescriptor : getRows()) {
            if (tag.equals(iRowDescriptor.getTag())) {
                rowDescriptor = iRowDescriptor;
                break;
            }
        }

        return rowDescriptor;
    }

    public int getIndexOfRowDescriptor(RowDescriptor rowDescriptor) {
        return mRows.indexOf(rowDescriptor);
    }

    public Boolean isMultivalueSection() {
        return mMultivalueSection;
    }

    public void setMultivalueSection(Boolean multivalueSection) {
        mMultivalueSection = multivalueSection;
    }

    public void setmMultiValueDelegate(MultiValueDelegate delegate) {
        mMultiValueDelegate = delegate;
    }

    public void setCanAddValue(Boolean mCanAddValue) {
        this.mCanAddValue = mCanAddValue;
    }

    public Boolean canAddValue() {
        return mCanAddValue;
    }

    public List getRowValues() {

        ArrayList<Object> values = new ArrayList<>();
        for (RowDescriptor rowDescriptor : mRows) {
            if (rowDescriptor.getValue() != null && rowDescriptor.getValue().getValue() != null) {
                values.add(rowDescriptor.getValue().getValue());
            }
        }
        return values;

    }

    public void setFooterTitle(String footerTitle) {
        String oldFooterTitle = this.mFooterTitle;
        this.mFooterTitle = footerTitle;

        if (oldFooterTitle == null && footerTitle != null) {
            getFormDescriptor().didInsertRow(null, this);
        } else if (footerTitle == null) {
            getFormDescriptor().didRemoveRow(null, this);
        } else {
            getFormDescriptor().didChangedRow(null, this);
        }
    }

    public String getFooterTitle() {
        return mFooterTitle;
    }

    public boolean hasFooterTitle() {
        return mFooterTitle != null;
    }

    public SectionFooterDescriptor getFooterDescriptor() {
        SectionFooterDescriptor descriptor = SectionFooterDescriptor.newInstance(mTag, getFooterTitle());
        if (getCellConfig() != null) {
            descriptor.setCellConfig(getCellConfig());
        }
        return descriptor;
    }
}
