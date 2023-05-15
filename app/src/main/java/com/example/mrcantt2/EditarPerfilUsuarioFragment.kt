package com.example.mrcantt2

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mrcantt2.databinding.FragmentEditarPerfilClienteBinding
import com.example.ttmrcan.RetrofitClient
import com.example.ttmrcan.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class EditarPerfilUsuarioFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentEditarPerfilClienteBinding
    private lateinit var inflater: LayoutInflater

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.inflater = inflater
        this.binding = FragmentEditarPerfilClienteBinding.inflate(inflater, container, false)

        return binding.root
    }

    var usuario = Usuario(-1,"", "","","",""
        ,"","",-1,"","","",0,
        0,1)
    var isEditando = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idUsuario = arguments?.getInt("id_usuario")
        //val idMascota = arguments?.getInt("id_mascota")
        usuario.id_usuario = idUsuario!!
        //mascota.id_mascota = idMascota!!
        val nombreUsuario = arguments?.getString("nombre_usaurio") //Nombre
        val apellidosUsuario = arguments?.getString("apellido_usaurio") // Apellido
        val telefonoUsuario = arguments?.getString("telefono_usaurio") // Telefono
        val emailUsuario = arguments?.getString("email_usuario") // Correo
        val estadoUsuario = arguments?.getString("estado_usuario") // Estado
        val ciudadUsuario = arguments?.getString("ciudad_usuario")// Ciudad
        val coloniaUsuario = arguments?.getString("colonia_usuario") // Colonia
        val cpUsuario = arguments?.getInt("cp_usuario") // CODIGO POSTAL
        val calleUsuario = arguments?.getString("calle_usuario") // Calle
        val numExtUsuario = arguments?.getString("num_ext_usuario") // Num Ext
        val passwordUsuario = arguments?.getString("password_usuario") // Contraseña

        //val fechaCortaMascota = fechaMascota?.substring(0,10)

        //SET TEXT A CADA UNO DE LOS EDIT TEXT *- CADA CAMPO
        binding.etNombreEditarPerfil.setText(nombreUsuario)
        binding.etApellidoEditarPerfil.setText(apellidosUsuario)
        binding.etTelefonoEditarPerfil.setText(telefonoUsuario)
        binding.etCorreoRegistroEditar.setText(emailUsuario)
        binding.etEstadoEditarPerfil.setText(estadoUsuario)
        binding.etCiudadEditarPerfil.setText(ciudadUsuario)
        binding.etColoniaEditarPerfil.setText(coloniaUsuario)
        binding.etCPEditarPerfil.setText(cpUsuario?.toString()) //obtengo el valor del CP y lo genero como un strinf con toString() *-/ Int -> String /-*
        binding.etCalleEditarPerfil.setText(calleUsuario)
        binding.etNumExtEditarPerfil.setText(numExtUsuario)
        binding.etContrasenaEditarPerfil.setText(passwordUsuario)


        //Toast.makeText(activity,"ID: ${mascota.id_mascota}", Toast.LENGTH_LONG).show()

        binding.buttonGuardarEditarPerfilUsuario.setOnClickListener {
            val isValido = validarCampos()
            if(isValido){
                if(!isEditando){
                    actualizarUsuario()
                }
            }
        }

        binding.buttonCancelarEditarPerfilUsuario.setOnClickListener {
            requireActivity().onBackPressed() //Esto se ocupa para regresar entre fragments ES UTIL
        }

    }

    fun actualizarUsuario() {

        this.usuario.nombre_usuario = binding.etNombreEditarPerfil.text.toString()
        this.usuario.apellido_usuario = binding.etApellidoEditarPerfil.text.toString()
        this.usuario.telefono_usuario = binding.etTelefonoEditarPerfil.text.toString()
        this.usuario.email_usuario = binding.etCorreoRegistroEditar.text.toString()
        this.usuario.estado_usuario = binding.etEstadoEditarPerfil.text.toString()
        this.usuario.ciudad_usuario = binding.etCiudadEditarPerfil.text.toString()
        this.usuario.colonia_usuario = binding.etColoniaEditarPerfil.text.toString()
        this.usuario.cp_usuario = binding.etCPEditarPerfil.text.toString().toInt()
        this.usuario.calle_usuario = binding.etCalleEditarPerfil.text.toString()
        this.usuario.num_ext_usuario = binding.etNumExtEditarPerfil.text.toString()
        this.usuario.password_usuario = binding.etContrasenaEditarPerfil.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webServ.actualizarUsuario(usuario.id_usuario, usuario)
            activity?.runOnUiThread{
                if (call.isSuccessful){
                    Toast.makeText(activity,call.body().toString(), Toast.LENGTH_SHORT).show()
                    mostrarDialogo() // crear la funcion donde se llame el dialogo EXITOSO
                    requireActivity().onBackPressed()

                }else{
                    Toast.makeText(activity,call.body().toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun validarCampos(): Boolean{
        return !(binding.etNombreEditarPerfil.text.isNullOrEmpty()||binding.etApellidoEditarPerfil.text.isNullOrEmpty()
                ||binding.etTelefonoEditarPerfil.text.isNullOrEmpty()||binding.etCiudadEditarPerfil.text.isNullOrEmpty()
                ||binding.etColoniaEditarPerfil.text.isNullOrEmpty()||binding.etCPEditarPerfil.text.isNullOrEmpty()
                ||binding.etCalleEditarPerfil.text.isNullOrEmpty()||binding.etNumExtEditarPerfil.text.isNullOrEmpty()
                ||binding.etContrasenaEditarPerfil.text.isNullOrEmpty())
    }

    private fun mostrarDialogo() {
        val dialogo = activity?.let { Dialog(it, R.style.CustomDialogStyle) }
        dialogo?.setContentView(R.layout.dialogo_cambio_exitoso)
        val titulo = dialogo?.findViewById<TextView>(R.id.dialogo_correcto)
        titulo?.text = "Sus datos se actualizaron correctamente"
        dialogo?.setCancelable(true)
        dialogo?.show()
        Handler().postDelayed({
            dialogo?.dismiss()
        }, 5000)
    }

}