package com.example.juegopicobotellag8.viewmodel

import androidx.lifecycle.ViewModel
import com.example.juegopicobotellag8.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    fun registerUser(email: String, pass: String, isRegister: (Boolean) -> Unit) {
        repository.registerUser(email, pass) { response ->
            isRegister(response)
        }
    }

    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {
        repository.loginUser(email, pass) { response ->
            isLogin(response)
        }
    }

    fun logout() {
        repository.logout()
    }

    private fun isLoggedIn(): Boolean {
        return repository.isUserLogged()
    }

    fun session(isEnableView: (Boolean) -> Unit) {
        if (isLoggedIn()) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}
