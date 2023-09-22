package com.singaludra.weatherapp.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singaludra.weatherapp.domain.Resource
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.domain.usecase.GetForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ForecastUIState {
    data class Success(val forecast: Forecast?): ForecastUIState
    data class Error(val errorMessage: String?): ForecastUIState

    object Loading: ForecastUIState
}
@HiltViewModel
class DetailForecastViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
): ViewModel() {
    private val _forecastUIState = MutableStateFlow<ForecastUIState>(ForecastUIState.Loading)
    val forecastUIState = _forecastUIState.asStateFlow()

    fun getForecastData(cityName: String){
        viewModelScope.launch {
            getForecastUseCase.execute(cityName).collect{
                when(it){
                    is Resource.Success -> {
                        _forecastUIState.value = ForecastUIState.Success(it.data)
                    }
                    is Resource.Error -> {
                        _forecastUIState.value = ForecastUIState.Error(it.message)
                    }
                }
            }
        }
    }
}