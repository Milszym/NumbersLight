package net.szymon.miloch.di

import dagger.Module
import net.szymon.miloch.ui.numberdetails.NumberDetailFragment
import net.szymon.miloch.ui.numbers.NumbersListActivity

@Module(
    includes = [
        NumbersListActivity.InjectorModule::class,
        NumberDetailFragment.InjectorModule::class
    ]
)
class AndroidInjectorsModule