package com.example.mrcantt2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LogIn : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        //referencia a tv Olvidaste Contraseña
        val tvRecuCon = findViewById<TextView>(R.id.tvOlvidasteContraseña)
        //set on-click listener
        tvRecuCon.setOnClickListener{
            val intentRecCon = Intent(this, RecuperarContrasena::class.java)
            startActivity(intentRecCon)
        }

        //referencia a tv Registrarse
        val tvRegis = findViewById<TextView>(R.id.tvRegistrarse)
        tvRegis.setOnClickListener {
            val intentRegis = Intent(this, RegistroCliente::class.java)
            startActivity(intentRegis)
        }
        // Obtener los elementos de la interfaz de usuario
        //val emailEditText = findViewById<EditText>(R.id.editTextCorreoLogIn)
        //val passwordEditText = findViewById<EditText>(R.id.editTextPasswordLogIn)
        val loginButton = findViewById<Button>(R.id.btnIngresar)

        //referencia del boton INGRESAR
        loginButton.setOnClickListener {

            val intentRegis = Intent(this, MainActivity::class.java)
            startActivity(intentRegis)


        }


    }
//********************************* Declaramos las funciones a usar ********************************


}