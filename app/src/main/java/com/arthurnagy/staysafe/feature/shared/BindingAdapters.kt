package com.arthurnagy.staysafe.feature.shared

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("textRes")
fun TextView.textResource(@StringRes textResource: Int) {
    if (textResource != 0) {
        setText(textResource)
    }
}

@BindingAdapter("isVisible")
fun View.setVisible(visible: Boolean) {
    this.isVisible = visible
}