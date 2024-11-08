package com.example.juegopicobotellag8.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.juegopicobotellag8.model.Retos
import com.example.juegopicobotellag8.utils.Constants.NAME_BD

@Database(entities = [Retos::class], version = 1)
abstract class RetosDB : RoomDatabase() {

    abstract fun retosDao(): RetosDao

    companion object{
        fun getDatabase(context: Context): RetosDB {
            return Room.databaseBuilder(
                context.applicationContext,
                RetosDB::class.java,
                NAME_BD
            ).build()
        }
    }
}