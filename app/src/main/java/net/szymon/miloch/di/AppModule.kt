package net.szymon.miloch.di

import dagger.Module
import net.szymon.miloch.api.ApiModule
import net.szymon.miloch.rx.RxModule

@Module(
    includes = [
        ApiModule::class,
        RxModule::class
    ]
)
class AppModule