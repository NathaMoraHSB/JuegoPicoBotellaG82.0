package com.example.juegopicobotellag8.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.juegopicobotellag8.R
import com.example.juegopicobotellag8.databinding.FragmentHomeFragmentBinding
import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.transition.Visibility
import com.example.juegopicobotellag8.view.DialogoReto.Companion.showDialogoReto
import com.example.juegopicobotellag8.viewmodel.PokemonViewModel
import com.example.juegopicobotellag8.viewmodel.RetosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val pokemonViewModel: PokemonViewModel by viewModels()
    private val retosViewModel: RetosViewModel by viewModels()

    private var mediaPlayer: MediaPlayer? = null
    private var bgSound: MediaPlayer? = null
    private var currentRotation: Float = 0f
    private var bgSoundEnabled = true
    private lateinit var counterTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupBackgroundSound()
        setupBottleAnimation()
    }

    private fun setupView() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        setupToolbarActions()
        updateSoundIcon()
    }

    private fun setupToolbarActions() {
        val touchAnimation = AlphaAnimation(0.5f, 1.0f).apply { duration = 150 }

        binding.toolbarHome.apply {
            btnChallenges.setOnClickListener {
                it.startAnimation(touchAnimation)
                navigateTo(R.id.action_homeFragment_to_retosFragment)
                toggleBackgroundSound(false)
            }
            btnInstructions.setOnClickListener {
                it.startAnimation(touchAnimation)
                navigateTo(R.id.action_homeFragment_to_instructionsFragment)
                toggleBackgroundSound(false)
            }
            btnRateApp.setOnClickListener {
                it.startAnimation(touchAnimation)
                navigateTo(R.id.action_homeFragment_to_rateFragment)
            }
            btnShareApp.setOnClickListener {
                it.startAnimation(touchAnimation)
                shareContent()
            }
            btnToggleAudio.setOnClickListener {
                it.startAnimation(touchAnimation)
                bgSoundEnabled = !bgSoundEnabled
                toggleBackgroundSound(bgSoundEnabled)
                updateSoundIcon()
            }
        }
    }

    private fun navigateTo(actionId: Int) {
        findNavController().navigate(actionId)
    }

    private fun updateSoundIcon() {
        val imageResource = if (bgSoundEnabled) R.drawable.ic_audio_on else R.drawable.ic_audio_off
        binding.toolbarHome.btnToggleAudio.setImageResource(imageResource)
    }

    private fun setupBackgroundSound() {
        bgSound = MediaPlayer.create(activity, R.raw.bgsound)?.apply {
            start()
            isLooping = true
            setVolume(1.0f, 1.0f)
        }
    }

    private fun setupBottleAnimation() {
        val bottleImage: ImageView = binding.bottleImage
        val animationView = binding.animationView

        counterTextView = binding.counterTextView
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.spinning)

        animationView.setOnClickListener {
            bgSound?.pause()
            val duration = 6000L
            val randomAngle = Random.nextInt(0, 360)
            val newRotation = currentRotation + 2000f + randomAngle

            ObjectAnimator.ofFloat(bottleImage, "rotation", currentRotation, newRotation).apply {
                this.duration = duration
                addListener(object : android.animation.Animator.AnimatorListener {
                    override fun onAnimationStart(animation: android.animation.Animator) {
                        mediaPlayer?.start()
                    }

                    override fun onAnimationEnd(animation: android.animation.Animator) {
                        mediaPlayer?.pause()
                        mediaPlayer?.seekTo(0)
                        currentRotation = newRotation % 360
                        startCountdown()
                    }

                    override fun onAnimationCancel(animation: android.animation.Animator) {
                        mediaPlayer?.pause()
                    }

                    override fun onAnimationRepeat(animation: android.animation.Animator) {}
                })
                start()
            }
            animationView.visibility = View.GONE
        }
    }

    private fun startCountdown() {
        counterTextView.visibility = View.VISIBLE
        var countdownValue = 3

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (countdownValue >= 0) {
                    counterTextView.text = countdownValue.toString()
                    countdownValue--
                    handler.postDelayed(this, 1000)
                } else {
                    showDialog()
                }
            }
        }
        handler.post(runnable)
    }

    private fun showDialog() {
        showDialogoReto(this, pokemonViewModel, retosViewModel){
            toggleBackgroundSound(true)
            counterTextView.visibility = View.GONE
            binding.animationView.visibility = View.VISIBLE
        }
    }

    private fun shareContent() {
        val url = "https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es"
        val title = "App Pico Botella"
        val slogan = "Solo los valientes lo juegan !!"
        val message = "$title\n\n$slogan\n\n$url"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Compartir con:")
        context?.packageManager?.let { packageManager ->
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(shareIntent)
            } else {
                Toast.makeText(context, "No hay aplicaciones disponibles", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        bgSound?.release()
        mediaPlayer = null
        bgSound = null
        _binding = null
    }

    private fun toggleBackgroundSound(isEnabled: Boolean) {
        if (bgSoundEnabled) {
            if (isEnabled) {
                bgSound?.start()
            } else {
                bgSound?.pause()
                bgSound?.seekTo(0)
            }
        } else {
            bgSound?.pause()
        }
    }

    override fun onPause() {
        super.onPause()
        toggleBackgroundSound(false)
    }

    override fun onResume() {
        super.onResume()
        toggleBackgroundSound(bgSoundEnabled)
    }
}