package com.example.juegopicobotellag8.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.juegopicobotellag8.repository.LoginRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`


import org.junit.Assert.assertEquals
import org.mockito.kotlin.any


class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    lateinit var loginRepository: LoginRepository

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        loginRepository = mock(LoginRepository::class.java)
        loginViewModel = LoginViewModel(loginRepository)
    }

/*Verifica que, al registrar un usuario exitosamente, el callback sea invocado con true.
    Simula una respuesta exitosa del repositorio, captura el resultado en una variable y asegura que el valor sea true.*/
   @Test
    fun `should call isRegister with true when repository registerUser succeeds`() {
        // Configurar el mock para simular éxito en el registro
        `when`(loginRepository.registerUser(any(), any(), any()))
            .thenAnswer { invocation ->
                val callback = invocation.getArgument<(Boolean) -> Unit>(2)
                callback(true) // Simula que el registro fue exitoso
            }

        // Variable para capturar el resultado del callback
        var callbackResult: Boolean? = null
        val isRegisterCallback: (Boolean) -> Unit = { result ->
            callbackResult = result // Captura el resultado del callback
        }

        // Llamar al método a probar
        loginViewModel.registerUser("newuser@example.com", "securePassword", isRegisterCallback)

        // Verificar que el callback fue llamado con true
        assertEquals(true, callbackResult)
    }

/*Comprueba que, cuando el correo ya está registrado, el callback sea invocado con false.
Simula una respuesta fallida del repositorio, imprime los argumentos
y verifica que el callback devuelve el valor esperado.*/

    @Test
    fun `should call isRegister with false when email is already registered`() {
        // Configurar el mock para simular fallo en el registro
        `when`(loginRepository.registerUser(any(), any(), any()))
            .thenAnswer { invocation ->
                val email = invocation.getArgument<String>(0)
                val password = invocation.getArgument<String>(1)
                println("Email recibido: $email") // Imprime el email recibido
                println("Password recibido: $password") // Imprime el password recibido

                val callback = invocation.getArgument<(Boolean) -> Unit>(2)
                callback(false) // Simula que el registro falló (usuario ya registrado)
            }

        // Variable para capturar el resultado del callback
        var callbackResult: Boolean? = null
        val isRegisterCallback: (Boolean) -> Unit = { result ->
            println("Callback invocado con: $result") // Imprime el valor recibido en el callback
            callbackResult = result
        }

        // Llamada al método a probar
        loginViewModel.registerUser("existinguser@example.com", "securePassword", isRegisterCallback)

        // Verificar que el callback fue llamado con false
        assertEquals(false, callbackResult)
    }

/*Simula que un usuario está logueado (isUserLogged() devuelve true).
El test asegura que el callback de sesión del ViewModel habilita la vista (isEnableView = true).*/
    @Test
    fun `test logout`() {
        // When
        loginViewModel.logout()

        // Then
        verify(loginRepository).logout()
    }

/*Simula que un usuario está logueado (isUserLogged() devuelve true).
El test asegura que el callback de sesión del ViewModel habilita la vista (isEnableView = true).*/

    @Test
    fun `test session when logged in`() {
        // Given
        `when`(loginRepository.isUserLogged()).thenReturn(true)

        // When
        var isEnableView = false
        loginViewModel.session { response ->
            isEnableView = response
        }

        // Then
        assertTrue(isEnableView)
    }

    /*Simula que un usuario no está logueado (isUserLogged() devuelve false).
        El test asegura que el callback de sesión del ViewModel deshabilita la vista (isEnableView = false).*/
    @Test
    fun `test session when not logged in`() {
        // Given
        `when`(loginRepository.isUserLogged()).thenReturn(false)

        // When
        var isEnableView = true
        loginViewModel.session { response ->
            isEnableView = response
        }

        // Then
        assertFalse(isEnableView)
    }

}