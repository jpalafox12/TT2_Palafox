package com.example.ttmrcan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.mrcantt2.databinding.FragmentAgendarVacunacionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AgendarVacunacionFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentAgendarVacunacionBinding
    private var listaMascotas: List<String> = emptyList()

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
        binding = FragmentAgendarVacunacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferencesUsuario =
            requireContext().getSharedPreferences("idUsuario", Context.MODE_PRIVATE)
        val valorUsuarioId = sharedPreferencesUsuario.getInt("id", 2)
        obtenerMascotasUsuario(valorUsuarioId)

        binding.btnCancelarCitaVacunacion.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnAgendarCitaVacunacion.setOnClickListener {
            //agregarCita()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().supportFragmentManager.popBackStack()
            binding.btnAgendarCitaVacunacion.setOnClickListener {
            }
        }
    }

    fun obtenerMascotasUsuario(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webServ.obtenerMascotasUsuario(id)
            requireActivity().runOnUiThread {
                if (call.isSuccessful) {
                    listaMascotas = call.body()?.listaMascotas?.map { it.nombre_mascota } ?: emptyList()
                    if (listaMascotas.isNotEmpty()) {
                        val mascotaAdapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            listaMascotas
                        )

                        mascotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerSMCV.adapter = mascotaAdapter
                    } else {
                        Toast.makeText(activity, "No tienes mascotas aún", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        activity,
                        "Error al obtener las mascotas del usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentoCitas.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AgendarVacunacionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
