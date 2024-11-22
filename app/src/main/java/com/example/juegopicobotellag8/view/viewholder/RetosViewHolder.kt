package com.example.juegopicobotellag8.view.viewholder

import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.juegopicobotellag8.R
import com.example.juegopicobotellag8.databinding.DialogEditarRetoBinding
import com.example.juegopicobotellag8.databinding.DialogEliminarRetoBinding
import com.example.juegopicobotellag8.databinding.ItemRetoBinding
import com.example.juegopicobotellag8.model.Retos
import com.example.juegopicobotellag8.viewmodel.RetosViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class RetosViewHolder @Inject constructor(
    private val binding: ItemRetoBinding,
    private val retosViewModel: RetosViewModel
) : RecyclerView.ViewHolder(binding.root) {

    fun setItemRetos(retos: Retos) {
        binding.textSection.text = retos.description

        // Acción para editar un reto
        binding.icEdit.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(binding.root.context)
            val inflater = LayoutInflater.from(binding.root.context)
            val dialogBinding = DialogEditarRetoBinding.inflate(inflater)

            val inputField = dialogBinding.editTextReto
            val saveButton = dialogBinding.buttonGuardar
            val cancelButton = dialogBinding.buttonCancelar

            builder.setView(dialogBinding.root)
            val alertDialog = builder.create()
            alertDialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            alertDialog.setCancelable(false)

            inputField.setText(retos.description)
            saveButton.setBackgroundColor(
                ContextCompat.getColor(binding.root.context, R.color.orange)
            )

            cancelButton.setOnClickListener { alertDialog.dismiss() }

            saveButton.setOnClickListener {
                val reto = inputField.text.toString()
                if (reto.isNotBlank()) {
                    val retoObj = Retos(description = reto, id = retos.id)
                    retosViewModel.updateRetos(retoObj)
                    Toast.makeText(
                        binding.root.context,
                        "Reto Actualizado: $reto",
                        Toast.LENGTH_SHORT
                    ).show()
                    alertDialog.dismiss()
                } else {
                    Toast.makeText(
                        binding.root.context,
                        "Por favor, escribe un reto.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            inputField.addTextChangedListener {
                val isEnabled = it?.isNotEmpty() == true
                saveButton.isEnabled = isEnabled
                saveButton.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        if (isEnabled) R.color.orange else R.color.gray_light
                    )
                )
            }
            alertDialog.show()
        }

        // Acción para eliminar un reto
        binding.icDelete.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(binding.root.context)
            val inflater = LayoutInflater.from(binding.root.context)
            val dialogBinding = DialogEliminarRetoBinding.inflate(inflater)

            val deleteButton = dialogBinding.buttonSi
            val cancelButton = dialogBinding.buttonNo

            dialogBinding.dialogDescription.text = retos.description

            builder.setView(dialogBinding.root)
            val alertDialog = builder.create()
            alertDialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            alertDialog.setCancelable(false)

            cancelButton.setOnClickListener { alertDialog.dismiss() }

            deleteButton.setOnClickListener {
                retosViewModel.deleteRetos(retos)
                Toast.makeText(
                    binding.root.context,
                    "Reto Eliminado: ${retos.description}",
                    Toast.LENGTH_SHORT
                ).show()
                alertDialog.dismiss()
            }

            alertDialog.show()
        }
    }
}