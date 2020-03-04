package net.szymon.miloch.api

import dagger.Module
import dagger.Provides
import net.szymon.miloch.api.logs.InterceptorFactory
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(InterceptorFactory.createLoggingInterceptor())
        }.build()
    }

    @Provides
    @Singleton
    fun providesRetrofitClientFactory(
        okHttpClient: OkHttpClient
    ): RetrofitClientFactory {
        return RetrofitClientFactory(okHttpClient)
    }

    @Provides
    fun providesNumbersApiService(retrofitClientFactory: RetrofitClientFactory): NumbersApiService {
        return retrofitClientFactory.create(NumbersApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesNumbersApi(numbersApiImpl: NumbersApiImpl): NumbersApi = numbersApiImpl
}