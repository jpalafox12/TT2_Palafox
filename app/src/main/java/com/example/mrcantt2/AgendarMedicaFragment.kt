package com.example.mrcantt2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
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
    // TODO: Rename and change types of parameters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val datePicker = view?.findViewById<DatePicker>(R.id.date_picker_cita_vacunacion)
        val calendar = Calendar.getInstance()
        datePicker?.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
            Calendar.DAY_OF_MONTH), null)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agendar_medica, container, false)

    }

}