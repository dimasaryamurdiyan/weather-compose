package com.singaludra.weatherapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singaludra.weatherapp.domain.Resource
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.domain.usecase.GetForecastUseCase
import com.singaludra.weatherapp.domain.usecase.GetMyCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface MyCitiesState {
    data class Success(val forecast: List<Forecast.City>?): MyCitiesState
    data class Error(val errorMessage: String?): MyCitiesState

    object Loading: MyCitiesState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    private val getMyCityUseCase: GetMyCityUseCase
): ViewModel() {

    private val _myCitiesState = MutableStateFlow<MyCitiesState>(MyCitiesState.Loading)
    val myCitiesState = _myCitiesState.asStateFlow()

    init {
        loadMyCities()
    }

    fun loadMyCities() {
        viewModelScope.launch {
            getMyCityUseCase.execute(Unit).collect{
                if(it.isNotEmpty()) {
//                    updateCitiesData(it)
                    _myCitiesState.value = MyCitiesState.Success(it)
                } else {
                    _myCitiesState.value = MyCitiesState.Success(emptyList())
                }
            }
        }
    }

    private fun updateCitiesData(data: List<Forecast.City>) {
        data.forEach { city ->
            viewModelScope.launch {
                getForecastUseCase.execute(city.cityName).collect{ result ->
                    when(result){
                        is Resource.Success -> {

                        }
                        is Resource.Error -> {
                            _myCitiesState.value = MyCitiesState.Error(result.message)
                        }
                    }
                }
            }
        }
    }


}