package com.example.mrcantt2

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mrcantt2.databinding.FragmentPerfilUsuarioBinding
import com.example.ttmrcan.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentoPerfilMascota.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerfilUsuarioFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentPerfilUsuarioBinding
    private lateinit var inflater: LayoutInflater

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
        // Inflate the layout for this fragment
        this.inflater = inflater
        this.binding = FragmentPerfilUsuarioBinding.inflate(inflater, container, false)

        return binding.root
    }

    var usuario = Usuario(-1,"", "","","",""
        ,"","",-1,"","","",0,
        0,1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO
        val idUsuario = arguments?.getInt("id_usuario")
        usuario.id_usuario = idUsuario!!
        val nombreUsuario = arguments?.getString("nombre_usaurio") // Obtener el nombre de usuario
        val apellidosUsuario = arguments?.getString("apellido_usaurio") // Obtener los apellidos del usuario
        val nombreCompleto = nombreUsuario + " " + apellidosUsuario // Concatenar utilizando el operador "+"
        val telefonoUsuario = arguments?.getString("telefono_usaurio") // Obtener el número de teléfono
        // Formatear el número de teléfono
        val telefonoFormateado = PhoneNumberUtils.formatNumber(telefonoUsuario)
        // Asignar el número de teléfono formateado a un TextView, por ejemplo
        binding.tvTelefonoUsuario.text = telefonoFormateado
        val emailUsuario = arguments?.getString("email_usuario") // Correo

        binding.tvNombreUsuario.setText(nombreCompleto)
        binding.tvTelefonoUsuario.setText(telefonoFormateado)
        binding.tvCorreoUsuario.setText(emailUsuario)

        // Encontrar los tres botones de editar perfil CLIENTE
        val editarPerfil = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        // Set click listeners for each button
        editarPerfil.setOnClickListener {
            // Replace the current Fragment with the Agendar Fragment
            val perfilEditarFragment = EditarPerfilUsuarioFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, perfilEditarFragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentoPerfilMascota.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PerfilUsuarioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}