package net.szymon.miloch.ui.common.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import net.szymon.miloch.di.ViewModelFactory
import net.szymon.miloch.lifecycle.HasLifecycleOwner

abstract class BaseFragment : Fragment(), HasLifecycleOwner {
    override val lifecycleOwner: LifecycleOwner
        get() = this

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    inline fun <reified T : ViewModel> ViewModelFactory<T>.get(): T =
        ViewModelProvider(this@BaseFragment, this)[T::class.java]
}