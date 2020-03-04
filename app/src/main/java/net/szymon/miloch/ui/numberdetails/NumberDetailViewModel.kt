package net.szymon.miloch.ui.numberdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import net.szymon.miloch.R
import net.szymon.miloch.api.NumbersApi
import net.szymon.miloch.data.NumberDetails
import net.szymon.miloch.rx.SchedulerProvider
import net.szymon.miloch.rx.postProgress
import net.szymon.miloch.ui.common.base.BaseViewModel
import net.szymon.miloch.ui.numberdetails.NumberDetailFragment.Companion.NUMBER_NAME
import javax.inject.Inject
import javax.inject.Named

class NumberDetailViewModel @Inject constructor(
    @Named(NUMBER_NAME)
    private val numberName: String,
    private val numbersApi: NumbersApi,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private var numberDisposable: Disposable? = null

    private val _number: MutableLiveData<NumberDetails> = MutableLiveData()
    val number: LiveData<NumberDetails> = _number

    private val _progress: MutableLiveData<Boolean> = MutableLiveData()
    val progress: LiveData<Boolean> = _progress

    private val _errors: PublishSubject<Int> = PublishSubject.create()
    val errors: Observable<Int> = _errors

    fun loadNumber() {
        numberDisposable?.dispose()
        numberDisposable = numbersApi.getNumber(numberName)
            .subscribeOn(schedulerProvider.IO)
            .postProgress(_progress)
            .subscribe({ numbersList ->
                _number.postValue(numbersList)
            }, {
                _errors.onNext(R.string.unknown_error_message)
            })
    }

    override fun onCleared() {
        numberDisposable?.dispose()
        super.onCleared()
    }
}
