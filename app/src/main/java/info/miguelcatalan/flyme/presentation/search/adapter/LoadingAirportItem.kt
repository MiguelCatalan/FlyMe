package info.miguelcatalan.flyme.presentation.search.adapter

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import info.miguelcatalan.flyme.R

class LoadingAirportItem : Item() {
    override fun getLayout(): Int = R.layout.search_item_loading
    override fun bind(viewHolder: ViewHolder, position: Int) {}
}
