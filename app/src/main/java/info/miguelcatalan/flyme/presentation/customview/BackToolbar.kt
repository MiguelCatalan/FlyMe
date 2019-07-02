package info.miguelcatalan.flyme.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import info.miguelcatalan.flyme.R

class BackToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    private var onBackPressedCallback: () -> Unit = {}

    init {
        inflate(context, R.layout.toolbar_back, this)
    }

    fun onBackPressed(callback: () -> Unit = {}) {
        onBackPressedCallback = callback
    }

}