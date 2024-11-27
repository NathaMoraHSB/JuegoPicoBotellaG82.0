package com.example.juegopicobotellag8.repository

import com.google.firebase.auth.FirebaseAuth

class LoginRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, pass:String, isRegisterComplete: (Boolean)->Unit){
        if(email.isNotEmpty() && pass.isNotEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isRegisterComplete(true)
                    } else {
                        isRegisterComplete(false)
                    }
                }
        }else{
            isRegisterComplete(false)
        }
    }

    fun loginUser(email: String, pass: String, isLoginComplete: (Boolean) -> Unit) {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener { signInTask ->
                    if (signInTask.isSuccessful) {
                        isLoginComplete(true)
                    } else {
                        registerUser(email, pass, isLoginComplete)
                    }
                }
        } else {
            isLoginComplete(false)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun isUserLogged(): Boolean {
        return firebaseAuth.currentUser != null
    }
}