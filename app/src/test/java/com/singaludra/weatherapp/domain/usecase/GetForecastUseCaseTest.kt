package com.singaludra.weatherapp.domain.usecase

import com.singaludra.weatherapp.TestCoroutineRule
import com.singaludra.weatherapp.domain.Resource
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.domain.repository.IWeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class GetForecastUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: IWeatherRepository

    private lateinit var getForecastUseCase: GetForecastUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getForecastUseCase = GetForecastUseCase(repository)
    }

    @Test
    fun `test successful forecast retrieval`() = testCoroutineRule.runBlockingTest {
        // Arrange
        val cityName = "City1"
        val forecastData = Forecast(
            weatherList = emptyList(),
            cityDtoData = Forecast.City(270.5, "Paris", "FR", 0, 12.5, 12.5, "asda")
        )
        val successResource = Resource.Success(forecastData)

        // Mock repository behavior to return a success flow
        Mockito.`when`(repository.getForecastDataWithCityName(cityName)).thenReturn(flow { emit(successResource) })

        // Act
        val result = getForecastUseCase.execute(cityName)

        // Assert
        result.collect { resource ->
            assert(resource is Resource.Success)
            assert((resource as Resource.Success).data == forecastData)
        }
    }

    @Test
    fun `test error during forecast retrieval`() = testCoroutineRule.runBlockingTest {
        // Arrange
        val cityName = "City2"
        val errorMessage = "Failed to retrieve forecast"
        val errorResource = Resource.Error<Forecast>(errorMessage)

        // Mock repository behavior to return an error flow
        Mockito.`when`(repository.getForecastDataWithCityName(cityName)).thenReturn(flow { emit(errorResource) })

        // Act
        val result = getForecastUseCase.execute(cityName)

        // Assert
        result.collect { resource ->
            assert(resource is Resource.Error)
            assert((resource as Resource.Error).message == errorMessage)
        }
    }
}