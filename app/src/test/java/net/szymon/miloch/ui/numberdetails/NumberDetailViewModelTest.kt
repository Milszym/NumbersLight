package net.szymon.miloch.ui.numberdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import net.szymon.miloch.api.NumbersApi
import net.szymon.miloch.data.NumberDetails
import net.szymon.miloch.rx.TestSchedulerProvider
import net.szymon.miloch.rx.mockSingle
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class NumberDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testScheduler = TestScheduler()
    private val testSchedulerProvider = TestSchedulerProvider(testScheduler)

    private val numberName = "1"

    private val numbersApi = Mockito.mock(NumbersApi::class.java)

    private fun createViewModel() = NumberDetailViewModel(
        numberName = numberName,
        numbersApi = numbersApi,
        schedulerProvider = testSchedulerProvider
    )

    @Test
    fun `Successful fetching populates proper number object`() {
        val numberDetails = NumberDetails(
            name = numberName,
            text = "Text $numberName",
            image = "http://fakeimageprovider.pl/$numberName"
        )
        mockSingle(
            stubbing = Mockito.`when`(numbersApi.getNumber(numberName)),
            testScheduler = testScheduler,
            response = numberDetails
        )
        val vm = createViewModel()
        vm.loadNumber()
        testScheduler.triggerActions()
        assert(vm.number.value != null)
        assert(vm.number.value == numberDetails)
    }

    @Test
    fun `Failed fetching populates error`() {
        val vm = createViewModel()
        mockSingle(
            stubbing = Mockito.`when`(numbersApi.getNumber(numberName)),
            testScheduler = testScheduler,
            response = null,
            error = Exception()
        )
        val errorObserver = TestObserver.create<Int>().apply {
            vm.errors.subscribe(this)
        }
        vm.loadNumber()
        testScheduler.triggerActions()
        assert(vm.number.value == null)
        assert(errorObserver.valueCount() == 1)
    }

}