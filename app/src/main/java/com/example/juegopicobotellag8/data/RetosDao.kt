package com.example.juegopicobotellag8.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.juegopicobotellag8.model.Retos

@Dao
interface RetosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRetos(retos: Retos)

    @Query("SELECT * FROM Retos ORDER BY id DESC")
    suspend fun getListRetos(): MutableList<Retos>

    @Delete
    suspend fun deleteRetos(retos: Retos)

    @Update
    suspend fun updateRetos(retos: Retos)
}