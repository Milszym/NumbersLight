package net.szymon.miloch.ui.numberdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.no_content_view.*
import kotlinx.android.synthetic.main.number_details.*
import net.szymon.miloch.R
import net.szymon.miloch.di.ViewModelFactory
import net.szymon.miloch.ui.common.base.BaseFragment
import net.szymon.miloch.ui.common.toast.toast
import net.szymon.miloch.ui.common.view.nocontent.NoContentMode
import javax.inject.Inject
import javax.inject.Named

class NumberDetailFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory<NumberDetailViewModel>

    private lateinit var vm: NumberDetailViewModel

    private var errorsDisposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.number_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = vmFactory.get()

        initializeView()
        observeEvents()

        vm.loadNumber()
    }

    private fun initializeView() {
        retryButton.setOnClickListener {
            vm.loadNumber()
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
        errorsDisposable = vm.errors
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                toast(it)
                showError()
            }
        vm.number.observe {
            noContentView.visibility = View.GONE
            numberName.text = it.name
            numberText.text = it.text
            Picasso.get().load(it.image).into(numberImage)
        }
    }

    private fun showError() {
        noContentView.visibility = View.VISIBLE
        noContentView.noContentMode = NoContentMode.ERROR
    }

    override fun onDestroyView() {
        super.onDestroyView()
        errorsDisposable?.dispose()
    }

    @Module
    interface InjectorModule {
        @ContributesAndroidInjector(modules = [ArgsModule::class])
        fun contributesNumberDetailFragment(): NumberDetailFragment
    }

    @Module
    class ArgsModule {
        @Provides
        @Named(NUMBER_NAME)
        fun provides(numberDetailFragment: NumberDetailFragment): String {
            return numberDetailFragment.requireArguments().getString(ARG_NUMBER_NAME)
                ?: throw IllegalStateException("No number name provided.")
        }
    }

    companion object {
        const val ARG_NUMBER_NAME = "ARG_NUMBER_NAME"
        const val NUMBER_NAME = "NUMBER_NAME"
    }
}
