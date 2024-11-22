package com.example.juegopicobotellag8.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import com.example.juegopicobotellag8.model.Retos
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.juegopicobotellag8.R
import com.example.juegopicobotellag8.databinding.FragmentRetosFragmentBinding
import com.example.juegopicobotellag8.databinding.DialogAgregarRetoBinding
import com.example.juegopicobotellag8.view.adapter.RetosAdapter
import com.example.juegopicobotellag8.viewmodel.RetosViewModel
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class RetosFragment : Fragment() {
    private lateinit var binding: FragmentRetosFragmentBinding
    private val retosViewModel: RetosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRetosFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        observadorViewModel()
    }

    private fun controladores() {
        val touchAnimation = AlphaAnimation(0.5f, 1.0f).apply {
            duration = 150
        }

        binding.contentToolbar.toolbar.setNavigationOnClickListener {
            // Animación de toque
            it.startAnimation(touchAnimation)
            findNavController().navigateUp()
        }

        binding.buttonAdd.setOnClickListener {
            showAddDialog(requireContext())
        }

    }

    private fun observadorViewModel() {
        observerListRetos()
    }

    private fun observerListRetos() {
        retosViewModel.getListRetos()
        /*retosViewModel.listRetos.observe(viewLifecycleOwner){ listRetos ->
            val recycler: RecyclerView = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = RetosAdapter(listRetos, retosViewModel)
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }*/
    }

    private fun showAddDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context)
        val inflater = LayoutInflater.from(context)
        val dialogBinding = DialogAgregarRetoBinding.inflate(inflater)

        val inputField = dialogBinding.editTextReto
        val saveButton = dialogBinding.buttonGuardar
        val cancelButton = dialogBinding.buttonCancelar

        builder.setView(dialogBinding.root)
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        alertDialog.setCancelable(false)

        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }

        saveButton.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.gray_light
            )
        )

        saveButton.setOnClickListener {
            val reto = inputField.text.toString()
            if (reto.isNotBlank()) {
                val retoObj = Retos(description = reto)
                retosViewModel.saveRetos(retoObj)
                Toast.makeText(context, "Reto guardado: $reto", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            } else {
                Toast.makeText(context, "Por favor, escribe un reto.", Toast.LENGTH_SHORT).show()
            }
        }

        inputField.addTextChangedListener {
            val isEnabled = it?.isNotEmpty() == true
            saveButton.isEnabled = isEnabled
            if (isEnabled) {
                saveButton.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.orange
                    )
                )
            } else {
                saveButton.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.gray_light
                    )
                )
            }
        }

        alertDialog.show()
    }

}