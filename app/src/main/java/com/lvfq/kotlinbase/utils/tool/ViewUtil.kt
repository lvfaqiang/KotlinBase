package com.lvfq.kotlinbase.utils.tool

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.kotlinx.coroutines.launchUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

/**
 * ViewUtil
 * @author FaQiang on 2018/9/28 上午11:06
 * @desc :
 *
 */
object ViewUtil {

    /**
     * @param view  如果 editTexts 中有输入框是未输入内容的，则 view 为不可点击状态，以及 isSelected = false 状态
     * @param editTexts
     */
    fun bindEditChangeSelectState(
        view: View,
        vararg editTexts: EditText,
        block: (hasEmpty: Boolean) -> Unit = {}
    ) {

        fun checkInputValue(): Int {
            val inputSize = editTexts.filter { editText ->
                editText.text.toString().isEmpty()
            }.size
            view.apply {
                isSelected = !(inputSize > 0)
                isClickable = !(inputSize > 0)
            }
            return inputSize
        }

        checkInputValue()

        val testWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                block.invoke(checkInputValue() > 0)
            }

        }
        editTexts.map { edit ->
            edit.addTextChangedListener(testWatcher)
        }
    }


    fun countDown(
        coroutineScope: CoroutineScope,
        textView: TextView,
        resetLabel: Int = 0,
        sendingLabel: Int = 0,
        seconds: Long = 60
    ) {
        val context = textView.context

        val resetStrId = if (resetLabel == 0) R.string.str_send_code_resend else resetLabel
        val sendingStrId =
            if (sendingLabel == 0) R.string.str_send_code_count_down_label_s else sendingLabel

        textView.isClickable = false
        textView.isSelected = false

        textView.text = context.getString(sendingStrId, seconds.toString())
        val job = launchUI(coroutineScope) {
            var count = seconds
            while (count > 0) {
                delay(1000)
                count--
                textView.text = context.getString(sendingStrId, count.toString())
            }
        }
        job.invokeOnCompletion {
            if (it == null) {   //异常信息为 null 则表示是正常完成。
                textView.isClickable = true
                textView.isSelected = true
                textView.text = context.getString(resetStrId)
            }
        }
    }

}