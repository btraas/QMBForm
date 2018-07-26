package com.quemb.qmbform.view;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.quemb.qmbform.R;
import com.quemb.qmbform.descriptor.ImageDelegateListener;
import com.quemb.qmbform.descriptor.RowDescriptor;
import com.quemb.qmbform.descriptor.Value;

import java.io.File;

/**
 * Created by joshua on 6/10/2017.
 */

public class FormImageFieldCell extends FormTitleFieldCell {
	private ImageView mImageView;

	private ImageLoader imageLoader;

	public FormImageFieldCell(Context context, RowDescriptor rowDescriptor) {
		super(context, rowDescriptor);
	}

	@Override
	protected void init() {
		super.init();
		imageLoader = ImageLoader.getInstance();

		mImageView = (ImageView) findViewById(R.id.imageView);
		mImageView.setOnClickListener(this);
	}

	@Override
	protected int getResource() {
		return R.layout.image_field_cell;
	}

	@Override
	protected void update() {
		super.update();

		if (getRowDescriptor().getValue() != null) {
			@SuppressWarnings("unchecked") Value<String> value = (Value<String>) getRowDescriptor().getValue();
			if (value != null && value.getValue() != null) {
				String valueString = value.getValue();
				if (imageLoader != null) {
					if (valueString.startsWith("http")) {
						imageLoader.displayImage(valueString, mImageView);
					} else {
						imageLoader.displayImage(Uri.fromFile(new File(valueString)).toString(), mImageView);
					}
				}
			}
		}
	}

	@Override
	public void onCellSelected() {
		super.onCellSelected();

		if (getRowDescriptor().getDisabled()) {
			return;
		}

		if (getRowDescriptor().hasImageDelegate()) {
			getRowDescriptor().getImageDelegate().pickImage(new ImageDelegateListener() {
				@Override
				public void onPickImage(String path) {
					onValueChanged(new Value<String>(path));
					imageLoader.displayImage(Uri.fromFile(new File(path)).toString(), mImageView);
				}
			});
		}

	}
}
