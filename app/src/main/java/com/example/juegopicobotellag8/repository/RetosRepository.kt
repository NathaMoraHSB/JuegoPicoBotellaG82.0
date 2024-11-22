package com.example.juegopicobotellag8.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.juegopicobotellag8.model.Retos
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetosRepository @Inject constructor(private val firestore: FirebaseFirestore){
    //private var retosDao:RetosDao = RetosDB.getDatabase(context).retosDao()
    private val retosCollection = firestore.collection("retos")

     fun saveRetos(retos:Retos, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        retos.id?.let {
            retosCollection.document(it)
                .set(retos)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }
     }

    fun getListRetos():LiveData<MutableList<Retos>>{
        val mutableData = MutableLiveData<MutableList<Retos>>()
        retosCollection.get().addOnSuccessListener { result ->
            val listData = mutableListOf<Retos>()
            for (reto in result){
                val id = reto.getString("id")
                val description = reto.getString("description")

                val retos = Retos(
                    id!!,
                    description!!
                )

                listData.add(retos)
            }
            mutableData.value = listData
        }
        return mutableData
    }

    /*suspend fun deleteRetos(retos: Retos){
        withContext(Dispatchers.IO){
            retosDao.deleteRetos(retos)
        }
    }

    suspend fun updateRepositoy(retos: Retos){
        withContext(Dispatchers.IO){
            retosDao.updateRetos(retos)
        }
    }*/
}