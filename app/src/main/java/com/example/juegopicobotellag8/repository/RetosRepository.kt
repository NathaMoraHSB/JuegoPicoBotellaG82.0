package com.example.juegopicobotellag8.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.juegopicobotellag8.model.Retos
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetosRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val retosCollection = firestore.collection("retos")

    fun saveRetos(retos:Retos, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        retos.id?.let {
            // Asigna la fecha y hora actuales al campo created_at
            retos.created_at = com.google.firebase.Timestamp.now()

            retosCollection
                .document()
                .set(retos)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }
    }

    fun getListRetos(): LiveData<MutableList<Retos>> {
        val mutableData = MutableLiveData<MutableList<Retos>>()

        retosCollection
            .orderBy("created_at", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    Log.e("FirestoreError", "Error al escuchar los cambios: ${error.message}")
                    return@addSnapshotListener
                }

                val listData = mutableListOf<Retos>()
                querySnapshot?.let { snapshot ->
                    for (document in snapshot) {
                        val id = document.id
                        val description = document.getString("description")

                        val retos = Retos(
                            id,
                            description!!
                        )
                        listData.add(retos)
                    }
                }
                mutableData.value = listData
            }

        return mutableData
    }

    fun deleteRetos(retos:Retos, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        retos.id?.let { id ->
            retosCollection.document(id)
                    .delete()
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        onFailure(e)
                    }
        }
    }


    fun updateRepositoy(retos:Retos, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        retos.id?.let {
            retosCollection
                .document(retos.id)
                .set(retos)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }
    }
}