package com.quemb.qmbform.descriptor;

/**
 * Created by tonimoeckel on 14.07.14.
 */
public class SectionFooterDescriptor extends FormItemDescriptor {

    private FormDescriptor mFormDescriptor;

    public static SectionFooterDescriptor newInstance(String tag) {

        return SectionFooterDescriptor.newInstance(tag, null);

    }

    public static SectionFooterDescriptor newInstance(String tag, String title) {
        SectionFooterDescriptor descriptor = new SectionFooterDescriptor();
        descriptor.mTitle = title;
        descriptor.mTag = tag;
        return descriptor;
    }

    public SectionFooterDescriptor() {

    }

    public FormDescriptor getFormDescriptor() {
        return mFormDescriptor;
    }

    public void setFormDescriptor(FormDescriptor formDescriptor) {
        mFormDescriptor = formDescriptor;
    }
}
