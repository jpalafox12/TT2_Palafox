package com.example.mrcantt2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [MisMascotasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MisMascotasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view?.findViewById<Button>(R.id.listViewMisMascotas) //List View donde se van a mostrar las mascotas
        return inflater.inflate(R.layout.fragment_mis_mascotas, container, false)

        }
}