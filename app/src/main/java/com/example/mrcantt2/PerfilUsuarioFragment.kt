package com.example.mrcantt2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PerfilUsuarioFragment : Fragment() {
    lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_perfil_usuario, container, false)


        // Encontrar los tres botones de editar perfil CLIENTE
        val editarPerfil = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        // Set click listeners for each button
        editarPerfil.setOnClickListener {
            // Replace the current Fragment with the Agendar Fragment
            val perfilEditarFragment = EditarPerfilClienteFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, perfilEditarFragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        // Return the inflated view del fragmento citas
        return view
    }
}