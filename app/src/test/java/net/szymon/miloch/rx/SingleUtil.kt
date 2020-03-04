package net.szymon.miloch.rx

import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.mockito.stubbing.OngoingStubbing

fun <T> mockSingle(
    stubbing: OngoingStubbing<Single<T>>,
    testScheduler: TestScheduler,
    response: T? = null,
    error: Throwable? = null
) {
    val r = if (response != null) Single.just(response) else Single.error(error)
    stubbing.thenReturn(r.subscribeOn(testScheduler).observeOn(testScheduler))
}