package info.miguelcatalan.flyme.presentation.navigator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import info.miguelcatalan.flyme.presentation.search.SearchActivity
import info.miguelcatalan.flyme.presentation.search.SelectionType

class AppNavigator(
    private val activity: AppCompatActivity
) {

    fun navigateToSearchAirport(type: SelectionType) {
        val intent = Intent(activity, SearchActivity::class.java)
        intent.putExtra(SearchActivity.KEY_TYPE, type)
        openActivityForResult(
            intent = intent,
            requestCode = SearchActivity.REQUEST_CODE
        )
    }

    private fun openActivityForResult(
        intent: Intent,
        requestCode: Int
    ) {
        activity.startActivityForResult(intent, requestCode)
    }
}