package com.example.juegopicobotellag8.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.juegopicobotellag8.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var webView: WebView

/**
 * A simple [Fragment] subclass.
 * Use the [RateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflar el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_rate_fragment, container, false)

        //Inicializar el WebView
        webView = view.findViewById(R.id.webView)

        // Configurar el WebView con ajustes adicionales
        webView.settings.apply {
            //Habilitar JavaScript
            javaScriptEnabled = true

            //Habilitar almacenamiento DOM
            domStorageEnabled = true

            //Ajustar al tamaño del contenido
            useWideViewPort = true

            //Mostrar toda la página en pantalla
            loadWithOverviewMode = true
        }

        // Cargar la URL externa
        webView.loadUrl("https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es")

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InstructionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}