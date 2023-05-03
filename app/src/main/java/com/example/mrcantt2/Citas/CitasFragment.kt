package com.example.mrcantt2.Citas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mrcantt2.*
import com.example.mrcantt2.databinding.FragmentCitasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




class CitasFragment : Fragment() {

    private lateinit var binding: FragmentCitasBinding
    private lateinit var adatador: CitasAdapter
    private var listaCitas = arrayListOf<Cita>()
    //private var isEditando = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.citaVacunacionButton.setOnClickListener {
            val agendarVacunacionFragment = AgendarVacunacionFragment()
            agendarVacunacionFragment.tipoCita = "vacunacion" // Aquí estableces el tipo de cita
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


        obtenerCitas()
    }

//    private fun setupRecyclerView() {
//        adatador = CitasAdapter(requireContext(), listaCitas)
//        binding.root.findViewById<RecyclerView>(R.id.recyclerviewCitasProximas).adapter = adatador
//    }

    private fun setupRecyclerView() {
        adatador = CitasAdapter(requireContext(), listaCitas) { position ->
            borrarCita(position) // Paso 3: Llamar a cancelarCita
        }
        binding.root.findViewById<RecyclerView>(R.id.recyclerviewCitasProximas).adapter = adatador
    }


    private fun obtenerCitas() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webService.obtenerCitas()
            requireActivity().runOnUiThread {
                if (call.isSuccessful) {
                    listaCitas = call.body()?.listaCitas ?: arrayListOf()
                    setupRecyclerView()
                } else {
                    Toast.makeText(requireContext(), "ERROR CONSULTAR TODOS", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun borrarCita(position: Int) {
        val cita = listaCitas[position]
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.webService.cancelarCita(cita.id_cita)
            requireActivity().runOnUiThread {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Cita cancelada exitosamente", Toast.LENGTH_LONG).show()
                    listaCitas.removeAt(position)
                    adatador.notifyItemRemoved(position)
                } else {
                    Toast.makeText(requireContext(), "Error al cancelar cita", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}

