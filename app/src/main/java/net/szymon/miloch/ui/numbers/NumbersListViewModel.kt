package net.szymon.miloch.ui.numbers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import net.szymon.miloch.R
import net.szymon.miloch.api.NumbersApi
import net.szymon.miloch.data.NumberBase
import net.szymon.miloch.rx.postProgress
import net.szymon.miloch.ui.common.base.BaseViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NumbersListViewModel @Inject constructor(
    private val numbersApi: NumbersApi
) : BaseViewModel() {
    private var numberDisposable: Disposable? = null

    private val _numbers: MutableLiveData<List<NumberBase>> = MutableLiveData()
    val numbers: LiveData<List<NumberBase>> = _numbers

    private val _progress: MutableLiveData<Boolean> = MutableLiveData()
    val progress: LiveData<Boolean> = _progress

    private val _errors: PublishSubject<Int> = PublishSubject.create()
    val errors: Observable<Int> = _errors

    init {
        loadNumbers()
    }

    fun loadNumbers() {
        numberDisposable?.dispose()
        numberDisposable = numbersApi.getNumbers()
            .subscribeOn(Schedulers.io())
            .postProgress(_progress)
            .observeOn(AndroidSchedulers.mainThread())
            .delay(1000L, TimeUnit.MILLISECONDS)
            .subscribe({ numbersList ->
                _numbers.postValue(numbersList)
            }, {
                _errors.onNext(R.string.unknown_error_message)
            })
    }
}
