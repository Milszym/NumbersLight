package net.szymon.miloch.rx

import io.reactivex.Scheduler

interface SchedulerProvider {
    val IO: Scheduler
    val Main: Scheduler
}