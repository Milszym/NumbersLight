package net.szymon.miloch.rx

import dagger.Module
import dagger.Provides

@Module
class RxModule {
    @Provides
    fun providesSchedulers(): SchedulerProvider {
        return Schedulers
    }
}