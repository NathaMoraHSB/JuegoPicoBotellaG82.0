package com.example.juegopicobotellag8.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.juegopicobotellag8.R
import com.example.juegopicobotellag8.databinding.FragmentLoginBinding
import com.example.juegopicobotellag8.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        session()
        setup()
        return binding.root
    }

    private fun setup() {
        val emailField = binding.emailField
        val passwordField = binding.passwordField
        val registerButton = binding.registerButton
        val loginButton = binding.loginButton

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isNotEmpty = emailField.text.isNotEmpty() && passwordField.text.isNotEmpty() && passwordField.text.toString().length >= 6

                val password = passwordField.text.toString()
                if (password.length < 6) {
                    binding.tilPassword.error = "Mínimo 6 dígitos"
                    binding.tilPassword.boxStrokeColor = resources.getColor(android.R.color.holo_red_light, null)
                    handleLoginAndRegisterButtons(isNotEmpty, registerButton, loginButton)
                } else {
                    binding.tilPassword.error = null
                    binding.tilPassword.boxStrokeColor = resources.getColor(android.R.color.white, null)
                    handleLoginAndRegisterButtons(isNotEmpty, registerButton, loginButton)
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        emailField.addTextChangedListener(textWatcher)
        passwordField.addTextChangedListener(textWatcher)

        // Listen for focus changes to adjust the outline color
        emailField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { binding.tilEmail.boxStrokeColor = resources.getColor(android.R.color.white, null) }
        }

        passwordField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { binding.tilPassword.boxStrokeColor = resources.getColor(android.R.color.white, null)}
        }

        registerButton.setOnClickListener {
            registerUser()
        }

        loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun handleLoginAndRegisterButtons(isNotEmpty: Boolean, registerButton: Button, loginButton: Button) {
        registerButton.isEnabled = isNotEmpty
        loginButton.isEnabled = isNotEmpty
        loginButton.backgroundTintList = if (isNotEmpty) resources.getColorStateList(android.R.color.white, null) else resources.getColorStateList(R.color.orange, null)
        loginButton.setTextColor(if (isNotEmpty) resources.getColor(android.R.color.black, null) else resources.getColor(android.R.color.white, null))
        registerButton.setTextColor(if (isNotEmpty) resources.getColor(android.R.color.white, null) else resources.getColor(R.color.gray_lighter, null)
        )
    }

    private fun goToHome() {
        val navController = findNavController()
        navController.navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun registerUser() {
        val email = binding.emailField.text.toString()
        val pass = binding.passwordField.text.toString()
        loginViewModel.registerUser(email, pass) { isRegister ->
            if (isRegister) {
                goToHome()
            } else {
                Toast.makeText(requireContext(), "Error en el registro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser() {
        val email = binding.emailField.text.toString()
        val pass = binding.passwordField.text.toString()
        loginViewModel.loginUser(email, pass) { isLogin ->
            if (isLogin) {
                goToHome()
            } else {
                Toast.makeText(requireContext(), "Login incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun session() {
        loginViewModel.session() { isEnableView ->
            if (isEnableView) {
                goToHome()
            }
        }
    }
}