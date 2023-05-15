package com.example.mrcantt2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.example.mrcantt2.databinding.FragmentAgendarMedicaBinding
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AgendarMedicaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AgendarMedicaFragment : Fragment() {

    private lateinit var binding: FragmentAgendarMedicaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAgendarMedicaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tipoCita = arguments?.getString("tipo_cita")

        // Usa el valor de tipoCita para configurar la interfaz de usuario
    }
}
