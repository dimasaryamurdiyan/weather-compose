package com.singaludra.weatherapp.domain.usecase

import com.singaludra.weatherapp.TestCoroutineRule
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.domain.repository.IWeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class AddCityUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: IWeatherRepository

    private lateinit var addCityUseCase: AddCityUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addCityUseCase = AddCityUseCase(repository)
    }

    @Test
    fun `test successful city addition`() = testCoroutineRule.runBlockingTest {
        // Arrange
        val cityToAdd = Forecast.City(270.5, "Paris", "FR", 0, 12.5, 12.5, "asda")

        // Act
        addCityUseCase.execute(cityToAdd)

        // Assert
        Mockito.verify(repository).addCity(cityToAdd)
    }

    @Test
    fun `test error during city addition`() = testCoroutineRule.runBlockingTest {
        // Arrange
        val cityToAdd = Forecast.City(270.5, "Paris", "FR", 0, 12.5, 12.5, "asda")
        val errorMessage = "Failed to add city"

        // Mock repository behavior to simulate an error
        Mockito.`when`(repository.addCity(cityToAdd)).thenThrow(RuntimeException(errorMessage))

        // Act and Assert
        val result = kotlin.runCatching { addCityUseCase.execute(cityToAdd) }

        assert(result.isFailure)
        assert(result.exceptionOrNull()?.message == errorMessage)
    }
}