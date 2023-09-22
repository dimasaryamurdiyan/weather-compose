package com.singaludra.weatherapp.presentation.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singaludra.weatherapp.domain.Resource
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.domain.usecase.AddCityUseCase
import com.singaludra.weatherapp.domain.usecase.GetForecastUseCase
import com.singaludra.weatherapp.domain.usecase.GetSpecificCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchCityUIState {
    data class Success(val forecast: Forecast?): SearchCityUIState
    data class Error(val errorMessage: String?): SearchCityUIState

    object Loading: SearchCityUIState
}

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    private val addCityUseCase: AddCityUseCase,
    private val getSpecificCityUseCase: GetSpecificCityUseCase
): ViewModel() {
    private val _searchCityUIState = MutableStateFlow<SearchCityUIState>(SearchCityUIState.Loading)
    val searchCityUIState = _searchCityUIState.asStateFlow()

    var searchFieldValue by mutableStateOf("")
        private set

    var isCitySearched by mutableStateOf(false)
        private set

    fun errorOnClick() {
        _searchCityUIState.value = SearchCityUIState.Success(null)
    }

    fun searchCityClick() {
        isCitySearched = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (checkSearchFieldValue()) {
                    fetchForecastWithCityName(searchFieldValue)
                } else {
                    _searchCityUIState.value = SearchCityUIState.Error("kosong")
                }
            } catch (e: Exception) {
                _searchCityUIState.value = SearchCityUIState.Error(e.message)
            }
        }
    }

    fun addCity(myCity: Forecast.City) {
        viewModelScope.launch {
            try {
                if (!getSpecificCityUseCase.execute(myCity.cityName)) {
                    addCityUseCase.execute(myCity)
                } else {
                    Log.e("add city", "you have already added this city")
                }
            } catch (e: Exception) {
                Log.e("e", e.message.toString())
            }
        }
    }

    private suspend fun fetchForecastWithCityName(cityName: String) {
        viewModelScope.launch {
            getForecastUseCase.execute(cityName).collect{
                when (it) {
                    is Resource.Success -> {
                        _searchCityUIState.value = SearchCityUIState.Success(it.data)
                    }
                    is Resource.Error -> {
                        _searchCityUIState.value = SearchCityUIState.Error(it.message)
                    }
                }
            }
        }

    }

    fun updateSearchField(input: String) {
        searchFieldValue = input
    }

    private fun checkSearchFieldValue(): Boolean {
        return searchFieldValue.isNotEmpty()
    }
}