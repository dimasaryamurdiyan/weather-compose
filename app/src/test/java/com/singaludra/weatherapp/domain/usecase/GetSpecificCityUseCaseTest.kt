package com.singaludra.weatherapp.domain.usecase

import com.singaludra.weatherapp.TestCoroutineRule
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
class GetSpecificCityUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: IWeatherRepository

    private lateinit var getSpecificCityUseCase: GetSpecificCityUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getSpecificCityUseCase = GetSpecificCityUseCase(repository)
    }

    @Test
    fun `test successful specific city retrieval`() = testCoroutineRule.runBlockingTest {
        // Arrange
        val cityName = "City1"

        // Mock repository behavior to return a true result
        Mockito.`when`(repository.getSpecificCity(cityName)).thenReturn(true)

        // Act
        val result = getSpecificCityUseCase.execute(cityName)

        // Assert
        assert(result)
    }

    @Test
    fun `test unsuccessful specific city retrieval`() = testCoroutineRule.runBlockingTest {
        // Arrange
        val cityName = "City2"

        // Mock repository behavior to return a false result
        Mockito.`when`(repository.getSpecificCity(cityName)).thenReturn(false)

        // Act
        val result = getSpecificCityUseCase.execute(cityName)

        // Assert
        assert(!result)
    }
}