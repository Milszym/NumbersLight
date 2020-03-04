package net.szymon.miloch.api

import io.reactivex.Single
import net.szymon.miloch.data.NumberBase
import net.szymon.miloch.data.NumberDetails

interface NumbersApi {
    fun getNumbers(): Single<List<NumberBase>>
    fun getNumber(numberName: String): Single<NumberDetails>
}