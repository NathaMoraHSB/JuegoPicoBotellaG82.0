package com.example.juegopicobotellag8.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.navigation.fragment.findNavController
import com.example.juegopicobotellag8.R
import com.example.juegopicobotellag8.databinding.FragmentInstructionsBinding


class InstructionsFragment : Fragment() {
    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val touchAnimation = AlphaAnimation(0.5f, 1.0f).apply {
            duration = 150
        }

        binding.contentToolbar.toolbar.setNavigationOnClickListener {
            it.startAnimation(touchAnimation)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}