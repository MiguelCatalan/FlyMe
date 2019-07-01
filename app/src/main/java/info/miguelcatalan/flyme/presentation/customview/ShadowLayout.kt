package info.miguelcatalan.flyme.presentation.customview

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class ShadowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        cardElevation = 4.px.toFloat()
        radius = 8.px.toFloat()
    }
}