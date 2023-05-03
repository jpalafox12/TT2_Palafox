package com.example.mrcantt2

import android.annotation.SuppressLint
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.safetynet.SafetyNet

class Captcha : AppCompatActivity() {
    private val RECAPTCHA_SITE_KEY = "6LdVM6ckAAAAANUKfd4LU5-qPAEMvdwSAE-oehe_"
    lateinit var recaptcha: CheckBox
    lateinit var texoo: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_captcha)

        recaptcha = findViewById(R.id.checkbox_capcha)
        texoo = findViewById(R.id.textView14)
        texoo.setText("holiwis")
        recaptcha.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                SafetyNet.getClient(this).verifyWithRecaptcha(RECAPTCHA_SITE_KEY)
                    .addOnSuccessListener { response ->
                        // El recaptcha se ha aprobado, realiza la acción requerida por tu aplicación
                        // (por ejemplo, envía el formulario o habilita el botón de enviar)
                        texoo.setText("hola wenas")
                        //val iniciar = Intent(this, PerfilNoR::class.java)
                        //startActivity(iniciar)
                    }
                    .addOnFailureListener { e ->
                        if (e is ApiException) {
                            val statusCode = e.statusCode
                            if (statusCode != CommonStatusCodes.CANCELED) {
                                if (e is ResolvableApiException) {
                                    // Si el recaptcha no se ha aprobado, muestra una alerta para pedir al usuario que complete el recaptcha
                                    texoo.setText("error")
                                    try {
                                        e.startResolutionForResult(this, 0)
                                    } catch (e: IntentSender.SendIntentException) {
                                        // No se puede iniciar el intent, manejar el error aquí
                                        texoo.setText("error1")
                                    }
                                } else {
                                    // El recaptcha no se ha aprobado, manejar el error aquí
                                    texoo.setText("error2")
                                }
                            }
                        } else {
                            // El recaptcha no se ha aprobado, manejar el error aquí
                            texoo.setText("error3")
                        }
                    }
            }
        }
    }
}