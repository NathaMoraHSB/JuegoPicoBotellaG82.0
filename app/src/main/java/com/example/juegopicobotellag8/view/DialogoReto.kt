package com.example.juegopicobotellag8.view

import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
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
            PokemonViewModel: PokemonViewModel,
            retoViewModel: RetosViewModel,
            onCloseCallback: () -> Unit
        ) {
            val inflater = LayoutInflater.from(fragment.requireContext())
            val binding = DialogoRetoBinding.inflate(inflater)

            val alertDialog = AlertDialog.Builder(fragment.requireContext()).create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            alertDialog.setCancelable(false)
            alertDialog.setView(binding.root)

            PokemonViewModel.getPokemons()
            fragment.view?.let { view ->
                PokemonViewModel.list_pokemon.observe(fragment.viewLifecycleOwner) { lista ->
                    val pokemon = lista[Random.nextInt(1,151)]

                    val options = RequestOptions()
                        .circleCrop()

                    Glide.with(binding.root.context)
                        .load(pokemon.image)
                        //.placeholder(R.drawable.ic_person_wms) // Imagen mientras carga
                        //.error(R.drawable.ic_person_wms) // Imagen si falla la carga
                        .apply(options)
                        .into(binding.pokemon)
                }
            }

            //retoViewModel.getListRetos()
            fragment.view?.let { view ->
                retoViewModel.getListRetos().observe(fragment.viewLifecycleOwner, Observer { lista ->
                    val retos = lista[Random.nextInt(lista.size)]
                    binding.reto.text = retos.description
                })
            }

            binding.btnCerrar.setOnClickListener {
                //Toast.makeText(fragment.requireContext(), "Reto cerrado", Toast.LENGTH_SHORT).show()
                onCloseCallback.invoke()
                alertDialog.dismiss()
            }
            alertDialog.show()

        }
    }
}