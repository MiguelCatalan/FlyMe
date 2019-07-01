package info.miguelcatalan.flyme.presentation.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.OnItemClickListener
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.databinding.SearchLayoutBinding
import info.miguelcatalan.flyme.presentation.search.adapter.AirportAdapter
import kotlinx.android.synthetic.main.search_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModel()
    private val airportAdapter = AirportAdapter()

    private lateinit var groupLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        configureList()
    }

    private fun initBinding() {
        val activitySearchBinding: SearchLayoutBinding =
            DataBindingUtil.setContentView(this, R.layout.search_layout)

        activitySearchBinding.apply {
            lifecycleOwner = this@SearchActivity
            viewModel = searchViewModel
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

        searchViewModel.getAirports().observe(this, Observer {
            airportAdapter.updateAirports(it)
        })

        searchViewModel.getSearchTerm().observe(this, Observer {
            searchViewModel.search(it)
        })

        searchViewModel.getIsLoading().observe(this, Observer {
            airportAdapter.isLoading = it
        })
    }

    private fun getOnItemClickListener(): OnItemClickListener =
        OnItemClickListener { item, _ ->

        }
}
