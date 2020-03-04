package net.szymon.miloch.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object Schedulers : SchedulerProvider {
    override val IO: Scheduler = Schedulers.io()
    override val Main: Scheduler = AndroidSchedulers.mainThread()
}