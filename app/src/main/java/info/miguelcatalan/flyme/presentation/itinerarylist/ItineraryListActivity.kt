package info.miguelcatalan.flyme.presentation.itinerarylist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.databinding.ItineraryListLayoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItineraryListActivity : AppCompatActivity() {

    private val itineraryListViewModel: ItineraryListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        val scheduleListLayoutBinding: ItineraryListLayoutBinding =
            DataBindingUtil.setContentView(this, R.layout.itinerary_list_layout)

        scheduleListLayoutBinding.apply {
            lifecycleOwner = this@ItineraryListActivity
            viewModel = itineraryListViewModel
        }
    }
}
