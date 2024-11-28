package com.example.juegopicobotellag8.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.juegopicobotellag8.model.Pokemon
import com.example.juegopicobotellag8.repository.PokemonRepository
import org.junit.Assert.*


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher

import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`


import org.junit.Assert.assertEquals
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class PokemonViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Permite probar LiveData

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var pokemonRepository: PokemonRepository
    private lateinit var pokemonViewModel: PokemonViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Configura un dispatcher de prueba
        pokemonRepository = mock(PokemonRepository::class.java) // Mockea el repositorio
        pokemonViewModel = PokemonViewModel(pokemonRepository) // Inicializa el ViewModel
    }

    /* verifica que el método getPokemons del ViewModel llama al método correspondiente
    del repositorio.*/
    @Test
    fun `getPokemons should call repository method`() = runTest {
        // When
        pokemonViewModel.getPokemons()

        // Then
        verify(pokemonRepository).getPokemons()
    }

/*Este test asegura que el LiveData listPokemon se actualiza correctamente
con la lista de Pokémon devuelta por el repositorio.*/
    @Test
    fun `test getPokemons updates listPokemon LiveData`() = runBlocking {
        // Given: Configurar el comportamiento del repositorio
        Dispatchers.setMain(UnconfinedTestDispatcher()) // Dispatcher de prueba
        val mockPokemonList = mutableListOf(
            Pokemon(id = 1, image = "https://example.com/pikachu.png"),
            Pokemon(id = 2, image = "https://example.com/charmander.png")
        )
        `when`(pokemonRepository.getPokemons()).thenReturn(mockPokemonList)

        // When: Llamar a la función a probar
        pokemonViewModel.getPokemons()

        // Then: Verificar que el LiveData se actualizó correctamente
        assertEquals(mockPokemonList, pokemonViewModel.listPokemon.value)
    }


}