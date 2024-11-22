package com.example.juegopicobotellag8.view

import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.juegopicobotellag8.databinding.DialogoRetoBinding
import com.example.juegopicobotellag8.view.fragment.HomeFragment
import com.example.juegopicobotellag8.viewmodel.PokemonViewModel
import com.example.juegopicobotellag8.viewmodel.RetosViewModel
import kotlin.random.Random

class DialogoReto {
    companion object {
        fun showDialogoReto(
            fragment: HomeFragment,
            pokemonViewModel: PokemonViewModel,
            retosViewModel: RetosViewModel,
            onCloseCallback: () -> Unit
        ) {
            val inflater = LayoutInflater.from(fragment.requireContext())
            val binding = DialogoRetoBinding.inflate(inflater)

            val alertDialog = AlertDialog.Builder(fragment.requireContext()).create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            alertDialog.setCancelable(false)
            alertDialog.setView(binding.root)

            pokemonViewModel.getPokemons()
            pokemonViewModel.listPokemon.observe(fragment.viewLifecycleOwner) { lista ->
                if (lista.isNotEmpty()) {
                    val pokemon = lista[Random.nextInt(lista.size)]
                    val options = RequestOptions().circleCrop()
                    Glide.with(binding.root.context)
                        .load(pokemon.image)
                        .apply(options)
                        .into(binding.pokemon)
                }
            }

            retosViewModel.getListRetos()
            retosViewModel.listRetos.observe(fragment.viewLifecycleOwner) { lista ->
                if (lista.isNotEmpty()) {
                    val retos = lista[Random.nextInt(lista.size)]
                    binding.reto.text = retos.description
                }
            }

            binding.btnCerrar.setOnClickListener {
                onCloseCallback.invoke()
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }
}