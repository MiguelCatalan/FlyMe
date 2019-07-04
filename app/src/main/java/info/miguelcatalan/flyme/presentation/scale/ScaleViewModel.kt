package info.miguelcatalan.flyme.presentation.scale

import androidx.lifecycle.MutableLiveData
import info.miguelcatalan.flyme.domain.schedule.Itinerary
import info.miguelcatalan.flyme.presentation.base.BaseViewModel

class ScaleViewModel : BaseViewModel() {

    val itinerary = MutableLiveData<Itinerary>()

    fun setItinerary(itinerary: Itinerary) {
        this.itinerary.value = itinerary
    }
}