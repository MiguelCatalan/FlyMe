package info.miguelcatalan.flyme.presentation.itinerarylist.adapter

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import info.miguelcatalan.flyme.R

class EmptyItineraryItem : Item() {
    override fun getLayout(): Int = R.layout.search_item_empty
    override fun bind(viewHolder: ViewHolder, position: Int) {}
}
