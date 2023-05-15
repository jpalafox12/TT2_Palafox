package com.example.ttmrcan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mrcantt2.Citas.CitasAdapter
import com.example.mrcantt2.R
import com.example.mrcantt2.databinding.FragmentCitasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentoCitas.newInstance] factory method to
 * create an instance of this fragment.
 */
class CitasFragment : Fragment(), CitasAdapter.OnItemClicked {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //private lateinit var adatador: CitasAdapter
    //private var listaCitas = arrayListOf<Cita>()

    private lateinit var binding: FragmentCitasBinding
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
        this.binding = FragmentCitasBinding.inflate(inflater, container, false)

        return binding.root
    }

    lateinit var adaptador: CitasAdapter
    var listaCitas = arrayListOf<Cita>()
    //var mascotaNueva = Mascota(-1,"","","","","","",0,"",0)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferencesUsuario = requireContext().getSharedPreferences("idUsuario", Context.MODE_PRIVATE)
        val valorUsuarioId = sharedPreferencesUsuario.getInt("id",2)

        //obtenemos por id el boton
        val abrirCitaVacunacion = view.findViewById<Button>(R.id.btnAbrirCitaVacunacion)
        // definimos el setOnClickListener del boton
        abrirCitaVacunacion.setOnClickListener {
            // Aquí se abrirá el fragmento de vacunación sin pasar ningún valor específico
            val fragment = AgendarVacunacionFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, fragment) // Reemplaza "fragmentContainer" con el ID del contenedor donde deseas mostrar el fragmento de vacunación
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        //obtenemos por id el boton
        val abrirCitaMedica = view.findViewById<Button>(R.id.btnAbrirCitaMedica)

        abrirCitaMedica.setOnClickListener {
            // Aquí se abrirá el fragmento de vacunación sin pasar ningún valor específico
            val fragment = AgendarMedicaFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, fragment) // Reemplaza "fragmentContainer" con el ID del contenedor donde deseas mostrar el fragmento de vacunación
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }




        binding.recyclerviewCitasProximas.layoutManager = LinearLayoutManager(activity)
        //la sigueinte linea de codigo esta en una fila con dos columnas del gridlayout
        // binding.recyclerviewCitasProximas.layoutManager = GridLayoutManager(activity,2)
        setupRecyclerView()

        obtenerCitasPendientes(valorUsuarioId)

        // Configurar el botón Atrás del teléfono para volver al FragmentoA
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Realizar la transacción de fragmento para ir al FragmentoA
            requireActivity().supportFragmentManager.popBackStack()


            binding.recyclerviewCitasProximas.layoutManager = LinearLayoutManager(requireContext())
            setupRecyclerView()
            obtenerCitasPendientes(valorUsuarioId)
        }
    }

    private fun obtenerCitasPendientes(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webServ.obtenerCitasPendientes(id)
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



    fun setupRecyclerView() {
        adaptador = CitasAdapter(listaCitas)
        adaptador.setOnClick(this@CitasFragment)
        binding.recyclerviewCitasProximas.adapter = adaptador
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
            CitasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun cancelarCita(cita: Cita) {
        // Aquí puedes realizar la lógica para cancelar la cita según tus necesidades
        // Por ejemplo, puedes mostrar un cuadro de diálogo para confirmar la cancelación
        // y realizar las operaciones correspondientes en la base de datos

        // Ejemplo de código para mostrar un Toast con información de la cita cancelada
        val mensaje = "Cita cancelada: ${cita.tipo_cita} - ${cita.fecha_cita}"
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()

        // Ejemplo de código para eliminar la cita de la lista y notificar al adaptador
        listaCitas.remove(cita)
        adaptador.notifyDataSetChanged()
    }

}
