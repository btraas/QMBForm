package com.quemb.qmbform.descriptor;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by tonimoeckel on 14.07.14.
 */
public class Value<T> implements Serializable {
    private @Nullable T mValue;
    private OnValueChangeListener mOnValueChangeListener;

    public Value(T value) {
        mValue = value;
    }

    public @Nullable T getValue() {
        return mValue;
    }

    @SuppressWarnings("unchecked")
    public void setValue(T value) {
        mValue = value;
        if (mOnValueChangeListener != null) {
            mOnValueChangeListener.onChange(value);
        }
    }


    public void setOnValueChangeListener(OnValueChangeListener listener) {
        this.mOnValueChangeListener = listener;
    }
	
    public OnValueChangeListener getOnValueChangeListener() {
        return this.mOnValueChangeListener;
    }	
}
