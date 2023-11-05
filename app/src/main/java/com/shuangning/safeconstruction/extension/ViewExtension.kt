package com.shuangning.safeconstruction.extension

import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts


/**
 * Created by Chenwei on 2023/9/30.
 */

fun Int.onClick(containerView: View, click:()->Unit){
    containerView.findViewById<View>(this).setOnClickListener {
        click()
    }
}

fun Int.setText(containerView: View, text: String){
    containerView.findViewById<TextView>(this).text = text
}

fun Int.setVisible(containerView: View, visibility: Int){
    containerView.findViewById<View>(this).visibility = visibility
}

/**
 * @param hintTextSize 单位dp
 */
fun EditText.setHintTextSizecColor(hint: String, hintTextSize: Int){
    val spannableString = SpannableString(hint)
    val absoluteSizeSpan = AbsoluteSizeSpan(hintTextSize, true)
    spannableString.setSpan(
        absoluteSizeSpan,
        0,
        spannableString.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    setHint(SpannableString(spannableString))
}

fun ComponentActivity.prepareStartForResult(callback: ActivityResultCallback<ActivityResult>): ActivityResultLauncher<Intent> {
    return registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback)
}
