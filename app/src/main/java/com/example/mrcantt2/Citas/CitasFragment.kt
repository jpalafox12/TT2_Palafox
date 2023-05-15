package com.example.mrcantt2.Citas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mrcantt2.*
import com.example.mrcantt2.AgendarEsteticaFragment
import com.example.mrcantt2.databinding.FragmentCitasBinding
import com.example.mrcantt2.databinding.FragmentPerfilUsuarioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CitasFragment : Fragment() {

    private lateinit var adatador: CitasAdapter
    private var listaCitas = arrayListOf<Cita>()

    private lateinit var binding: FragmentCitasBinding
    private lateinit var inflater: LayoutInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.inflater = inflater
        this.binding = FragmentCitasBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.citaVacunacionButton.setOnClickListener {
            val agendarVacunacionFragment = AgendarVacunacionFragment()
            //agendarVacunacionFragment.tipoCita = "vacunacion" // Aquí estableces el tipo de cita
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, agendarVacunacionFragment)
                .addToBackStack(null)
                .commit()
        }

        //USAR ESTE PARA PODER AGREGAR TRANSICIONES
        binding.citaMedicaButton.setOnClickListener {
            val fragment = AgendarMedicaFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.citaEsteticaButton.setOnClickListener {
            val fragment = AgendarEsteticaFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        binding.recyclerviewCitasProximas.layoutManager = LinearLayoutManager(requireContext())
        setupRecyclerView()

        obtenerCitasPendientes()
    }

    fun setupRecyclerView() {
        adatador = CitasAdapter(requireContext(), listaCitas) {
            // Acciones al hacer clic en una cita
        }
        binding.recyclerviewCitasProximas.adapter = adatador
    }



    private fun obtenerCitasPendientes() {
        val idUsuario = arguments?.getInt("id_usuario")
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webServ.obtenerCitasPendientes(id_mascota = idUsuario!!)
            requireActivity().runOnUiThread {
                if (call.isSuccessful) {
                    listaCitas = call.body()?.listaCitas ?: arrayListOf()
                    if (listaCitas.isEmpty()) {
                        // Muestra el mensaje "No tienes citas pendientes"
                        Toast.makeText(
                            requireContext(),
                            "No tienes citas pendientes",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        setupRecyclerView()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "ERROR CONSULTAR TODOS",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}

