package info.miguelcatalan.flyme.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.domain.airport.Airport
import kotlinx.android.synthetic.main.tripstop_layout.view.*

data class TripStopConfiguration(
    val stopType: TripStopType = TripStopType.Origin,
    val selectable: Boolean = true
)

sealed class TripStopType {
    object Origin : TripStopType()
    object Destination : TripStopType()
}

class TripStopView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var onStopPressedCallback: () -> Unit = {}

    var configuration: TripStopConfiguration = TripStopConfiguration()
        set(value) {
            field = value
            configureLayout()
        }

    var stop: Airport? = null
        set(value) {
            field = value
            configureLayout()
        }

    init {
        inflate(context, R.layout.tripstop_layout, this)
    }

    fun onPressed(callback: () -> Unit = {}) {
        onStopPressedCallback = callback
    }

    private fun configureLayout() {
        stopIcon.setImageResource(getStopIcon(configuration.stopType))

        stop?.let {
            configureWithStop(it)
        } ?: configureWithoutStop()

        when (configuration.selectable) {
            true -> {
                stopTitle.isClickable = true
                stopTitle.isFocusable = true
                stopTitle.setOnClickListener {
                    onStopPressedCallback.invoke()
                }
            }
            false -> {
                stopTitle.isClickable = false
                stopTitle.isFocusable = false
                stopTitle.setOnClickListener {}
            }
        }
    }

    private fun configureWithoutStop() {
        stopTitle.setTextColor(resources.getColor(R.color.sugar))
        stopTitle.setText(getHint(configuration.stopType))
    }

    private fun configureWithStop(stop: Airport) {
        stopTitle.setTextColor(resources.getColor(R.color.iron))
        stopTitle.text = "${stop.name} (${stop.airportCode})"
    }

    private fun getHint(stopType: TripStopType): Int {
        return when (stopType) {
            TripStopType.Origin -> R.string.selector_stop_hint_origin
            TripStopType.Destination -> R.string.selector_stop_hint_destination
        }
    }

    private fun getStopIcon(stopType: TripStopType): Int {
        return when (stopType) {
            TripStopType.Origin -> R.drawable.ic_home
            TripStopType.Destination -> R.drawable.ic_airport
        }
    }


}
