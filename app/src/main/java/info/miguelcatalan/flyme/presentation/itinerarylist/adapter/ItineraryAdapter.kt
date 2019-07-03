package info.miguelcatalan.flyme.presentation.itinerarylist.adapter

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import info.miguelcatalan.flyme.domain.schedule.Itinerary

class ItineraryAdapter : GroupAdapter<ViewHolder>() {

    private val section = Section()
    private val loadingView = LoadingItineraryItem()

    var isLoading: Boolean = false
        set(value) {
            when (value) {
                true -> showLoading()
                false -> hideLoading()
            }
            field = value
        }

    init {
        add(section)
    }

    fun updateItineraries(itineraries: List<Itinerary>) {
        section.update(itineraries.map {
            ItineraryItem(it)
        })

        if (itineraries.isEmpty() && !isLoading) {
            section.add(EmptyItineraryItem())
        }
    }

    private fun hideLoading() {
        section.remove(loadingView)
    }

    private fun showLoading() {
        section.update(listOf(loadingView))
    }
}