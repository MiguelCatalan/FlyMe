package info.miguelcatalan.flyme.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import info.miguelcatalan.flyme.R
import kotlinx.android.synthetic.main.toolbar_back.view.*

data class BackToolbarConfiguration(
    val title: Int? = null
)

class BackToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var configuration: BackToolbarConfiguration = BackToolbarConfiguration()
        set(value) {
            field = value
            configureLayout()
        }

    private var onBackPressedCallback: () -> Unit = {}

    init {
        inflate(context, R.layout.toolbar_back, this)
    }

    private fun configureLayout() {
        configuration.title?.let { resource ->
            toolbarTitle.setText(resource)
        }
        toolbarBack.setOnClickListener {
            onBackPressedCallback.invoke()
        }
    }

    fun onBackPressed(callback: () -> Unit = {}) {
        onBackPressedCallback = callback
    }

}