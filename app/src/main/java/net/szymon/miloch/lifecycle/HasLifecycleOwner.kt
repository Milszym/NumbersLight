package net.szymon.miloch.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

interface HasLifecycleOwner {
    val lifecycleOwner: LifecycleOwner

    fun <T : Any> LiveData<T>.observe(observer: (T) -> Unit) {
        this.observe(lifecycleOwner, Observer {
            it?.let(observer)
        })
    }

    fun <T> LiveData<T?>.observeNotNull(observer: (T) -> Unit) {
        this.observe(lifecycleOwner, Observer {
            it?.let(observer)
        })
    }

    fun <T> LiveData<T?>.observeNullable(observer: (T?) -> Unit) {
        this.observe(lifecycleOwner, Observer {
            observer.invoke(it)
        })
    }

}