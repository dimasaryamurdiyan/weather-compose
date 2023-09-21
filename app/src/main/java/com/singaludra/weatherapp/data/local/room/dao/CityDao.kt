package com.singaludra.weatherapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import com.singaludra.weatherapp.data.local.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert
    suspend fun addCity(cityEntity: CityEntity)

    @Query("SELECT * FROM city")
    fun getMyCity(): Flow<List<CityEntity>>

    @Query("DELETE FROM city WHERE city = :cityName")
    suspend fun deleteMyCity(cityName: String)
}