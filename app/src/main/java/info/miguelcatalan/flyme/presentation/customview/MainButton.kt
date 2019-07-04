package info.miguelcatalan.flyme.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import info.miguelcatalan.flyme.R
import kotlinx.android.synthetic.main.main_button_layout.view.*

class MainButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var text: Int = 0
        set(value) {
            field = value
            configureLayout()
        }

    init {
        LayoutInflater.from(context).inflate(
            R.layout.main_button_layout,
            this,
            true
        )
    }

    private fun configureLayout() {
        mainButton.setText(text)
    }

    fun enable(isEnabled: Boolean) {
        mainButton.isEnabled = isEnabled
        when (isEnabled) {
            true -> mainButton.setTextColor(resources.getColor(R.color.white))
            false -> mainButton.setTextColor(resources.getColor(R.color.steel))
        }
    }

    fun setOnClickListener(onClickPressed: () -> Unit = {}) {
        mainButton.setOnClickListener {
            onClickPressed.invoke()
        }
    }
}


