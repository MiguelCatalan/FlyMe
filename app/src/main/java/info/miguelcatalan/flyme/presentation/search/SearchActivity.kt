package info.miguelcatalan.flyme.presentation.search

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.OnItemClickListener
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.databinding.SearchLayoutBinding
import info.miguelcatalan.flyme.presentation.customview.BackToolbarConfiguration
import info.miguelcatalan.flyme.presentation.search.adapter.AirportAdapter
import info.miguelcatalan.flyme.presentation.search.adapter.AirportItem
import kotlinx.android.synthetic.main.search_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 34598
        const val KEY_AIRPORT = "key_airport"
        const val KEY_TYPE = "key_type"
    }

    private val searchViewModel: SearchViewModel by viewModel { parametersOf(this@SearchActivity) }
    private val airportAdapter = AirportAdapter()

    private lateinit var groupLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        searchViewModel.stopType = intent.getSerializableExtra(KEY_TYPE) as SelectionType
        configureList()
        configureView()
    }

    private fun initBinding() {
        val activitySearchBinding: SearchLayoutBinding =
            DataBindingUtil.setContentView(this, R.layout.search_layout)

        activitySearchBinding.apply {
            lifecycleOwner = this@SearchActivity
            viewModel = searchViewModel
        }
    }

    private fun configureView() {
        searchViewModel.searchTerm.observe(this, Observer { term ->
            searchViewModel.search(term)
        })

        toolbar.configuration = BackToolbarConfiguration(
            title = when (searchViewModel.stopType) {
                SelectionType.ORIGIN -> R.string.search_origin
                SelectionType.DESTINATION -> R.string.search_destination
            }
        )

        toolbar.onBackPressed {
            setResult(Activity.RESULT_CANCELED)
            onBackPressed()
        }
    }


    private fun configureList() {
        airportAdapter.apply {
            setOnItemClickListener(getOnItemClickListener())
        }

        groupLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        airportList.apply {
            adapter = airportAdapter
            layoutManager = groupLayoutManager
            setHasFixedSize(true)
        }

        searchViewModel.airports.observe(this, Observer { airports ->
            airportAdapter.updateAirports(airports)
        })

        searchViewModel.isLoading.observe(this, Observer { isLoading ->
            airportAdapter.isLoading = isLoading
        })
    }

    private fun getOnItemClickListener(): OnItemClickListener =
        OnItemClickListener { item, _ ->
            val returnIntent = intent
            returnIntent.putExtra(KEY_AIRPORT, (item as AirportItem).airport)
            returnIntent.putExtra(KEY_TYPE, searchViewModel.stopType)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
}

enum class SelectionType {
    ORIGIN,
    DESTINATION
}
