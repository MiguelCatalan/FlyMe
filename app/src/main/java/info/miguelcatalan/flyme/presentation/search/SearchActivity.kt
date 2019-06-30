package info.miguelcatalan.flyme.presentation.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.databinding.ActivitySearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        val activitySearchBinding: ActivitySearchBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_search)

        activitySearchBinding.apply {
            lifecycleOwner = this@SearchActivity
            viewModel = searchViewModel
        }
    }
}
