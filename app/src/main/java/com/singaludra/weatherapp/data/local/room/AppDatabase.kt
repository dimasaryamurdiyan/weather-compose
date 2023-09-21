package com.singaludra.weatherapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.singaludra.weatherapp.data.local.entity.CityEntity
import com.singaludra.weatherapp.data.local.room.dao.CityDao

@Database(entities = [CityEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun cityDao(): CityDao
}