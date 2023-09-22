package com.singaludra.weatherapp.domain.usecase

import com.singaludra.weatherapp.TestCoroutineRule
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.domain.repository.IWeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class GetMyCityUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: IWeatherRepository

    private lateinit var getMyCityUseCase: GetMyCityUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getMyCityUseCase = GetMyCityUseCase(repository)
    }

    @Test
    fun `test successful data retrieval`() = testCoroutineRule.runBlockingTest{
        // Arrange
        val cityList = listOf(
            Forecast.City(270.5, "Paris", "FR", 0, 12.5, 12.5, "asda"),
        )
        Mockito.`when`(repository.getMyCity()).thenReturn(flowOf(cityList))

        // Act
        val result: Flow<List<Forecast.City>> = getMyCityUseCase.execute(Unit)

        // Assert
        result.collect { cities ->
            assert(cities == cityList)
        }
    }
}
