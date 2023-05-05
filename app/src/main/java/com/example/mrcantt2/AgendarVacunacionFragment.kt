package com.example.mrcantt2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*

class AgendarVacunacionFragment : Fragment() {

    private lateinit var datePickerCV: DatePicker
    var tipoCita: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_vacunacion, container, false)

        // Obtener referencias a los elementos de la vista
        //val mascotasSpinner = view.findViewById<Spinner>

        return view
    }
}