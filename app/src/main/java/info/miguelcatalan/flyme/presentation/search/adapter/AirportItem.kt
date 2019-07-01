package info.miguelcatalan.flyme.presentation.search.adapter

import com.xwray.groupie.databinding.BindableItem
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.databinding.SearchItemBinding
import info.miguelcatalan.flyme.domain.airport.Airport

class AirportItem(
    val airport: Airport
) : BindableItem<SearchItemBinding>() {

    override fun getLayout(): Int = R.layout.search_item

    override fun bind(viewBinding: SearchItemBinding, position: Int) {
        viewBinding.airport = airport
    }
}