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
import kotlin.random.Random

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val PokemonViewModel: PokemonViewModel by viewModels()
    private val RetosViewModel: RetosViewModel by viewModels()

    //Sonido del giro de la botella
    private var mediaPlayer: MediaPlayer? = null

    //Angulo actual de la botella
    private var currentRotation: Float = 0f

    //Texto de cuenta regresiva
    private lateinit var counterTextView: TextView

    private var bgSoundEnabled = true

    //Sonido de fondo
    private lateinit var bgSound: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        config_view()
        bg_sound()
        // Se invoca logica giro de botella
        girarBotella()
    }

    private  fun config_view() {
        // Cerrar la aplicación al presionar el botón de atrás
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        changeIconSound() //cambia el icono ON/OFF

        // Animación de toque
        val touchAnimation = AlphaAnimation(0.5f, 1.0f).apply {
            duration = 150
        }

        // Navegación con animación de toque
        binding.toolbarHome.btnChallenges.setOnClickListener {
            it.startAnimation(touchAnimation) // Aplicar la animación
            findNavController().navigate(R.id.action_homeFragment_to_retosFragment)
            toggleBackgroundSound(false)
        }

        binding.toolbarHome.btnInstructions.setOnClickListener {
            it.startAnimation(touchAnimation)
            findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
            toggleBackgroundSound(false)
        }

        binding.toolbarHome.btnRateApp.setOnClickListener {
            it.startAnimation(touchAnimation)
            findNavController().navigate(R.id.action_homeFragment_to_rateFragment)
        }

        binding.toolbarHome.btnShareApp.setOnClickListener {
            it.startAnimation(touchAnimation)
            shareContent()
        }

        binding.toolbarHome.btnToggleAudio.setOnClickListener {
            it.startAnimation(touchAnimation)
            bgSoundEnabled = !bgSoundEnabled
            toggleBackgroundSound(bgSoundEnabled)

            changeIconSound()
        }
    }

    private fun changeIconSound(){
        val imageResource = if (bgSoundEnabled){
            R.drawable.ic_audio_on
        } else {
            R.drawable.ic_audio_off
        }
        binding.toolbarHome.btnToggleAudio.setImageResource(imageResource)
    }

    private fun bg_sound() {
        bgSound = MediaPlayer.create(activity, R.raw.bgsound)

        bgSound.start()
        bgSound.isLooping = true
        bgSound.setVolume(1.0f, 1.0f)
    }

    private fun girarBotella() {
        //Referencia a la botella
        val bottleImage: ImageView = binding.bottleImage

        //Referencia al boton
        val animationView = binding.animationView

        //Inicializa el texto de la cuenta regresiva
        counterTextView = binding.counterTextView

        //Inicializa el MediaPlayer con el archivo de sonido
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.spinning)

        //Configura el evento onClick para el LottieAnimationView
        animationView.setOnClickListener {
            bgSound.pause()

            //Establece la duración de la animación en 6 segundos
            val duration = 6000L

            //Genera un ángulo de rotación aleatorio entre 0 y 360 grados
            val randomAngle = Random.nextInt(0, 360)

            //Calcula el nuevo ángulo de rotación sumando el ángulo aleatorio al ángulo actual
            //2000 grados para 3 vueltas + el ángulo aleatorio
            val newRotation = currentRotation + 2000f + randomAngle

            //Configura la animación de rotación
            val rotateAnimator =
                ObjectAnimator.ofFloat(bottleImage, "rotation", currentRotation, newRotation)
            rotateAnimator.duration = duration

            //Inicia el sonido cuando la botella gira
            mediaPlayer?.start()

            //Detiene el sonido cuando la botella termina
            rotateAnimator.addListener(object : android.animation.Animator.AnimatorListener {
                override fun onAnimationStart(animation: android.animation.Animator) {
                    mediaPlayer?.start()
                }

                override fun onAnimationEnd(animation: android.animation.Animator) {
                    //Pausa el sonido al final de la animación
                    mediaPlayer?.pause()

                    //Vuelve al inicio del sonido
                    mediaPlayer?.seekTo(0)

                    //Actualiza el ángulo actual, asegurándose de que esté entre 0 y 360
                    currentRotation = newRotation % 360

                    //Invoca la cuenta regresiva
                    startCountdown()
                }

                override fun onAnimationCancel(animation: android.animation.Animator) {
                    //Pausa el sonido si la animación se cancela
                    mediaPlayer?.pause()
                }

                override fun onAnimationRepeat(animation: android.animation.Animator) {}
            })

            rotateAnimator.start()

            //Ocultamos el boton que hace girar la botella
            binding.animationView.visibility = View.GONE
        }
    }

    private fun startCountdown() {
        //Cambia la visibilidad del TextView a VISIBLE
        binding.counterTextView.visibility = View.VISIBLE

        //Crea un objeto Handler para manejar la cuenta regresiva
        val handler = Handler(Looper.getMainLooper())
        var countdownValue = 3

        //Actualiza el TextView con el valor inicial de la cuenta regresiva
        binding.counterTextView.text = countdownValue.toString()

        //Usa un Runnable para contar hacia abajo cada segundo
        val runnable = object : Runnable {
            override fun run() {
                //Si el valor es mayor que 0, actualiza el TextView y decrementa el contador
                if (countdownValue >= 0) {
                    //Actualiza el texto
                    binding.counterTextView.text = countdownValue.toString()
                    countdownValue--

                    //Espera 1 segundo antes de ejecutar de nuevo
                    handler.postDelayed(this, 1000)
                } else {
                    //abrir el dialogo del reto
                    showDialog()
                }
            }
        }

        //Inicia la cuenta regresiva
        handler.post(runnable)
    }

    private fun showDialog() {
        showDialogoReto(this, PokemonViewModel, RetosViewModel){
            toggleBackgroundSound(true)
            binding.counterTextView.visibility = View.GONE
            binding.animationView.visibility = View.VISIBLE
        }
    }


    private fun shareContent() {
        val url = "https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es";
        val title = "App Pico Botella"
        val slogan = "Solo los valientes lo juegan !!"
        val message = "$title\n\n$slogan\n\n$url"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Compartir con:")

        // Obtener packageManager desde el contexto
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
        //Libera recursos del MediaPlayer
        mediaPlayer?.release()
        bgSound.release()
        mediaPlayer = null
        _binding = null
    }

    private fun toggleBackgroundSound(isEnabled: Boolean) {
        if (bgSoundEnabled) {
            if (isEnabled) {
                bgSound.start()
            } else {
                bgSound.pause()
                bgSound.seekTo(0)
            }
        }
        else {
            bgSound.pause()
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