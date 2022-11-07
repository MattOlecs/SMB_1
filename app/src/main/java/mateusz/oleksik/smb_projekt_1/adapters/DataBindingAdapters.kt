package mateusz.oleksik.smb_projekt_1.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

object DataBindingAdapters {

    @BindingAdapter("double_to_text")
    @JvmStatic
    fun convertDoubleToText(textView: TextView, number: Double) {
        textView.text = number.toString()
    }

    @BindingAdapter("int_to_text")
    @JvmStatic
    fun convertIntToText(textView: TextView, number: Int) {
        textView.text = number.toString()
    }
}
