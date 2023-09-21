package com.singaludra.weatherapp.data

import com.singaludra.weatherapp.data.remote.network.ApiResponse
import com.singaludra.weatherapp.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class RepositoryLoader<RequestType, ResultType> {
    private var result: Flow<Resource<ResultType>> = flow {
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                val mappedData = mapApiResponseToDomain(apiResponse.data)
                emit(Resource.Success(mappedData))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error("Empty Data"))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }

    }

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract fun mapApiResponseToDomain(data: RequestType): ResultType

    fun asFlow(): Flow<Resource<ResultType>> = result
}