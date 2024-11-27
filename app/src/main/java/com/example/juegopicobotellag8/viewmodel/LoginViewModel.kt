package com.example.juegopicobotellag8.viewmodel
import androidx.lifecycle.ViewModel
import com.example.juegopicobotellag8.repository.LoginRepository

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

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

    fun session(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}