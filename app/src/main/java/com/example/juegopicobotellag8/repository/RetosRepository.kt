package com.example.juegopicobotellag8.repository
import android.content.Context
import com.example.juegopicobotellag8.data.RetosDB
import com.example.juegopicobotellag8.data.RetosDao
import com.example.juegopicobotellag8.model.Retos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetosRepository(val context: Context){
    private var retosDao:RetosDao = RetosDB.getDatabase(context).retosDao()
     suspend fun saveRetos(retos:Retos){
         withContext(Dispatchers.IO){
             retosDao.saveRetos(retos)
         }
     }

    suspend fun getListRetos():MutableList<Retos>{
        return withContext(Dispatchers.IO){
            retosDao.getListRetos()
        }
    }

    suspend fun deleteRetos(retos: Retos){
        withContext(Dispatchers.IO){
            retosDao.deleteRetos(retos)
        }
    }

    suspend fun updateRepositoy(retos: Retos){
        withContext(Dispatchers.IO){
            retosDao.updateRetos(retos)
        }
    }
}