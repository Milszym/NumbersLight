package net.szymon.miloch.api

import io.reactivex.Single
import net.szymon.miloch.api.data.NumberBaseDto
import net.szymon.miloch.api.data.NumberDetailsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NumbersApiService {
    @GET("json.php")
    fun getNumbers(): Single<List<NumberBaseDto>>

    @GET("json.php")
    fun getNumber(@Query("name") name: String): Single<NumberDetailsDto>
}