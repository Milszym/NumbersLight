package net.szymon.miloch.ui.numbers

import android.content.Intent
import android.os.Bundle
import android.view.View
import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.no_content_view.*
import kotlinx.android.synthetic.main.numbers_list.*
import net.szymon.miloch.R
import net.szymon.miloch.di.ViewModelFactory
import net.szymon.miloch.ui.common.base.BaseActivity
import net.szymon.miloch.ui.common.toast.toast
import net.szymon.miloch.ui.common.view.nocontent.NoContentMode
import net.szymon.miloch.ui.numberdetails.NumberDetailActivity
import net.szymon.miloch.ui.numberdetails.NumberDetailFragment
import net.szymon.miloch.ui.numbers.list.NumbersAdapter
import javax.inject.Inject

class NumbersListActivity : BaseActivity() {

    @Inject
    lateinit var vmFactory: ViewModelFactory<NumbersListViewModel>

    private lateinit var vm: NumbersListViewModel

    private var errorsDisposable: Disposable? = null

    private var twoPane: Boolean = false

    private val numbersAdapter = NumbersAdapter(
        onNumberClick = this::showNumberDetails
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbers_list)

        twoPane = resources.getBoolean(R.bool.isTablet)

        vm = vmFactory.get()

        val numberName = intent.extras?.getString(RETAINED_NUMBER_NAME_EXTRA, null)
        if (numberName != null) {
            showNumberDetails(numberName)
        }

        observeEvents()
        initializeView()
        observeEvents()
    }

    private fun initializeView() {
        numbersList.adapter = numbersAdapter
        retryButton.setOnClickListener {
            vm.loadNumbers()
        }
    }

    private fun observeEvents() {
        vm.progress.observe {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
        errorsDisposable?.dispose()
        errorsDisposable = vm.errors.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toast(it)
                showNoContentView(NoContentMode.ERROR)
            }, {
                toast(R.string.error_label)
            })
        vm.numbers.observe {
            if (it.isNullOrEmpty()) {
                showNoContentView(NoContentMode.NO_CONTENT)
            } else {
                noContentView.visibility = View.GONE
                numbersList.visibility = View.VISIBLE
                numbersAdapter.updateItems(it)
            }
        }
    }

    private fun showNumberDetails(numberName: String) {
        if (twoPane) {
            initializeNumberDetailContainer(numberName)
        } else {
            startNumberDetailsActivity(numberName)
        }
    }

    private fun initializeNumberDetailContainer(numberName: String) {
        val fragment = NumberDetailFragment()
            .apply {
                arguments = Bundle().apply {
                    putString(NumberDetailFragment.ARG_NUMBER_NAME, numberName)
                }
            }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.numberDetailContainer, fragment)
            .commit()
    }

    private fun startNumberDetailsActivity(numberName: String) {
        val intent = Intent(this, NumberDetailActivity::class.java).apply {
            putExtra(NumberDetailFragment.ARG_NUMBER_NAME, numberName)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        errorsDisposable?.dispose()
    }

    private fun showNoContentView(noContentMode: NoContentMode) {
        numbersList.visibility = View.GONE
        noContentView.visibility = View.VISIBLE
        noContentView.noContentMode = noContentMode
    }

    @Module
    interface InjectorModule {
        @ContributesAndroidInjector
        fun contributesNumbersListActivity(): NumbersListActivity
    }

    companion object {
        const val RETAINED_NUMBER_NAME_EXTRA = "RETAINED_NUMBER_NAME_EXTRA"
    }
}
