package net.szymon.miloch.ui.numberdetails

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import net.szymon.miloch.R
import net.szymon.miloch.ui.numbers.NumbersListActivity

class NumberDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_details)

        if (savedInstanceState == null) {
            val bundle = Bundle().apply {
                putString(
                    NumberDetailFragment.ARG_NUMBER_NAME,
                    intent.getStringExtra(NumberDetailFragment.ARG_NUMBER_NAME)
                )
            }
            supportFragmentManager.beginTransaction()
                .add(R.id.numberDetailContainer, NumberDetailFragment::class.java, bundle)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        if (resources.getBoolean(R.bool.isTablet)) {
            NavUtils.navigateUpTo(this, Intent(this, NumbersListActivity::class.java).apply {
                putExtra(
                    NumbersListActivity.RETAINED_NUMBER_NAME_EXTRA,
                    intent.getStringExtra(NumberDetailFragment.ARG_NUMBER_NAME)
                )
            })
        }
    }
}
