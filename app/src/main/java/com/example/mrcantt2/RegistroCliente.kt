package com.example.mrcantt2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class RegistroCliente : AppCompatActivity() {
    //declaracion de los objetos -- textInput para cada uno de los campos

    //instancia de de la clase ReuestQueue
    private lateinit var requestQueue: RequestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        // Crear la instancia de la RequestQueue
        requestQueue = Volley.newRequestQueue(this)

        // Obtener la referencia al botón de envío del formulario
        val btnRegistrar = findViewById<Button>(R.id.buttonRegistrar)
        // obtener el texto/String del edit text de cada uno de los campos del formulario
        //val editTextNom = findViewById<EditText>(R.id.editNombre)


    }
}
