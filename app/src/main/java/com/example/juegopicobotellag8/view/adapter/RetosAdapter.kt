package com.example.juegopicobotellag8.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.juegopicobotellag8.databinding.ItemRetoBinding
import com.example.juegopicobotellag8.model.Retos
import com.example.juegopicobotellag8.view.viewholder.RetosViewHolder
import com.example.juegopicobotellag8.viewmodel.RetosViewModel

class RetosAdapter(
    private val retosViewModel: RetosViewModel
):RecyclerView.Adapter<RetosViewHolder>() {
    private val listRetos: MutableList<Retos> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetosViewHolder {
        val binding = ItemRetoBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return RetosViewHolder(binding, retosViewModel)
    }

    override fun getItemCount(): Int = listRetos.size


    override fun onBindViewHolder(holder: RetosViewHolder, position: Int) {
        val retos = listRetos[position]
        holder.setItemRetos(retos)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Retos>) {
        listRetos.clear()
        listRetos.addAll(newList)
        notifyDataSetChanged()
    }
}