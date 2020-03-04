package net.szymon.miloch.rx

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single

fun <T> Single<T>.postProgress(
    mutableLiveData: MutableLiveData<Boolean>?
): Single<T> {
    return compose {
        it.doOnSubscribe {
            mutableLiveData?.postValue(true)
        }.doOnDispose {
            mutableLiveData?.postValue(false)
        }.doOnSuccess {
            mutableLiveData?.postValue(false)
        }.doOnError {
            mutableLiveData?.postValue(false)
        }
    }
}