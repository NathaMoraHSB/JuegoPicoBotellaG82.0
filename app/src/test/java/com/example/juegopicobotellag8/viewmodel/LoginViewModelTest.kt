package com.example.juegopicobotellag8.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.juegopicobotellag8.repository.LoginRepository
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`

class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var loginRepository: LoginRepository
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        loginRepository = mock(LoginRepository::class.java)
        loginViewModel = LoginViewModel()
    }

    @Test
    fun `test registro exitoso`() {

        //given
        val email = "pepito@mail.com"
        val password = "123456"
        var isRegistered = false

        doAnswer { invocation ->
            val callback = invocation.arguments[2] as (Boolean) -> Unit
            callback(true)
            null
        }.`when`(loginRepository).registerUser(email, password, any())

        // When
        loginViewModel.registerUser(email, password) { response ->
            isRegistered = response
        }

        // Then
        assertTrue(isRegistered)
        verify(loginRepository).registerUser(email, password, any())
    }

    @Test
    fun `test loginUser success`() {
        // Given
        val email = "user@example.com"
        val password = "securepassword"
        var isLoggedIn = false

        doAnswer { invocation ->
            val callback = invocation.arguments[2] as (Boolean) -> Unit
            callback(true) // Simular login exitoso
            null
        }.`when`(loginRepository).loginUser(eq(email), eq(password), any())

        // When
        loginViewModel.loginUser(email, password) { response ->
            isLoggedIn = response
        }

        // Then
        assertTrue(isLoggedIn)
        verify(loginRepository).loginUser(eq(email), eq(password), any())
    }

   /* @Test
    fun `test logout`() {
        // When
        loginViewModel.logout()

        // Then
        verify(loginRepository).logout()
    }*/

    @Test
    fun `test session when logged in`() {
        // Given
        `when`(loginRepository.isUserLogged()).thenReturn(true)
        var isViewEnabled = false

        // When
        loginViewModel.session { response ->
            isViewEnabled = response
        }

        // Then
        assertTrue(isViewEnabled)
        verify(loginRepository).isUserLogged()
    }

    @Test
    fun `test session when not logged in`() {
        // Given
        `when`(loginRepository.isUserLogged()).thenReturn(false)
        var isViewEnabled = false

        // When
        loginViewModel.session { response ->
            isViewEnabled = response
        }

        // Then
        assertFalse(isViewEnabled)
        verify(loginRepository).isUserLogged()
    }





}