package info.miguelcatalan.flyme.presentation.itinerarylist.adapter

import com.xwray.groupie.databinding.BindableItem
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.databinding.ItineraryItemBinding
import info.miguelcatalan.flyme.domain.schedule.Itinerary

class ItineraryItem(
    val itinerary: Itinerary

) : BindableItem<ItineraryItemBinding>() {

    val viewModel =  ItineraryItemViewModel()
    override fun getLayout(): Int = R.layout.itinerary_item

    override fun bind(viewBinding: ItineraryItemBinding, position: Int) {
        viewBinding.viewModel = viewModel
        viewModel.setItinerary(itinerary)
    }
}