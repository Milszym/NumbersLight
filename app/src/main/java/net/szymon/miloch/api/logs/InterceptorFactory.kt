package net.szymon.miloch.api.logs

import android.os.SystemClock
import net.szymon.miloch.BuildConfig
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object InterceptorFactory {
    private val DEBUG_LOGGING_LEVEL = HttpLoggingInterceptor.Level.BODY
    private val PROD_LOGGING_LEVEL = HttpLoggingInterceptor.Level.NONE

    fun createLoggingInterceptor(): Interceptor {
        SystemClock.sleep(3000)
        val detailLevel = if (BuildConfig.DEBUG) {
            DEBUG_LOGGING_LEVEL
        } else {
            PROD_LOGGING_LEVEL
        }
        return HttpLoggingInterceptor().apply {
            level = detailLevel
        }
    }
}
