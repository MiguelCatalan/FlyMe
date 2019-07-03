package info.miguelcatalan.flyme.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import info.miguelcatalan.flyme.databinding.ItineraryItemBinding
import info.miguelcatalan.flyme.domain.schedule.Itinerary
import info.miguelcatalan.flyme.presentation.base.ResourceResolver
import info.miguelcatalan.flyme.presentation.itinerarylist.adapter.ItineraryItemViewModel

class ItineraryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val viewModel: ItineraryItemViewModel

    init {
        val inflater = LayoutInflater.from(context)

        val itineraryItemBinding: ItineraryItemBinding = ItineraryItemBinding.inflate(inflater, this, true)
        viewModel = ItineraryItemViewModel(
            ResourceResolver(context)
        )
        itineraryItemBinding.viewModel = viewModel
    }

    fun setItinerary(itinerary: Itinerary) {
        viewModel.setItinerary(itinerary)
    }

}