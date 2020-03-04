package net.szymon.miloch.rx

import io.reactivex.Scheduler

class TestSchedulerProvider(scheduler: Scheduler) : SchedulerProvider {
    override val IO: Scheduler = scheduler
    override val Main: Scheduler = scheduler
}