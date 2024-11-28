package com.example.juegopicobotellag8.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.juegopicobotellag8.model.Retos
import com.example.juegopicobotellag8.repository.RetosRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.junit.Assert.*
import org.mockito.Mockito.doNothing
import org.mockito.kotlin.any
import org.mockito.kotlin.eq

class RetosViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    lateinit var retosRepository: RetosRepository

    private lateinit var retosViewModel: RetosViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        retosRepository = mock(RetosRepository::class.java)
        retosViewModel = RetosViewModel(retosRepository)
    }

    @After
    fun tearDown() {
        // Restablece el dispatcher principal después de cada test
        Dispatchers.resetMain()
    }

    @Test
    fun `listar retos`(){
        // Configurar datos simulados
        val fakeList = mutableListOf(
            Retos("1", "Reto 1", Timestamp(1732832567, 137000000)),
            Retos("2", "Reto 2", Timestamp(1732832567, 137000000))
        )
        val fakeLiveData = MutableLiveData<MutableList<Retos>>()
        fakeLiveData.value = fakeList

        // Simular el repositorio
        `when`(retosRepository.getListRetos()).thenReturn(fakeLiveData)

        // Inicializar el ViewModel
        var viewModel = RetosViewModel(retosRepository)

        // Configurar un observador manual para capturar el valor
        val observedValues = mutableListOf<MutableList<Retos>>()
        val observer = Observer<MutableList<Retos>> { value ->
            if (value != null) {
                observedValues.add(value)
            }
        }
        viewModel.listRetos.observeForever(observer)

        try {
            // Ejecutar el método a probar
            viewModel.getListRetos()

            // Verificar los valores capturados
            assertEquals(fakeList, observedValues.first())
        } finally {
            // Eliminar el observador después de la prueba
            viewModel.listRetos.removeObserver(observer)
        }
    }

    @Test
    fun `saveRetos`() {
        // Given
        val reto = Retos("1", "Descripcion")
        doNothing().`when`(retosRepository).saveRetos(eq(reto), any(), any())

        // When
        retosViewModel.saveRetos(reto)

        // Then
        verify(retosRepository).saveRetos(eq(reto), any(), any())
    }

    @Test
    fun `updateRetos`() {
        // Given
        val reto = Retos("1", "Reto actualizado")
        doNothing().`when`(retosRepository).updateRepositoy(eq(reto), any(), any())

        // When
        retosViewModel.updateRetos(reto)

        // Then
        verify(retosRepository).updateRepositoy(eq(reto), any(), any())
    }

    @Test
    fun `deleteRetos`() {
        // Given
        val reto = Retos("1", "Reto a eliminar")
        doNothing().`when`(retosRepository).deleteRetos(eq(reto), any(), any())

        // When
        retosViewModel.deleteRetos(reto)

        // Then
        verify(retosRepository).deleteRetos(eq(reto), any(), any())
    }

}