package com.quemb.qmbform.interfaces;

import com.quemb.qmbform.descriptor.RowDescriptor;

/**
 * Created by joshua on 13/9/16.
 */
public interface MultiValueListener {

	void onDeleteButtonClicked(RowDescriptor rowDescriptor, Runnable callback);

}
