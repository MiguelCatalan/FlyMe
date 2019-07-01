package info.miguelcatalan.flyme.presentation.search.adapter

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import info.miguelcatalan.flyme.domain.airport.Airport

class AirportAdapter : GroupAdapter<ViewHolder>() {

    private val section = Section()
    private val loadingView = LoadingAirportItem()

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

    fun updateAirports(airports: List<Airport>) {
        section.update(airports.map {
            AirportItem(it)
        })

        if (airports.isEmpty() && !isLoading) {
            section.add(EmptyAirportItem())
        }
    }

    private fun hideLoading() {
        section.remove(loadingView)
    }

    private fun showLoading() {
        section.update(listOf(loadingView))
    }
}