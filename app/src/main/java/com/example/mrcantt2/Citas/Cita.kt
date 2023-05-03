package com.example.mrcantt2.Citas

data class Cita(
    val id_cita : Int,
    val tipo_cita: String,
    val descripcion_cita : String,
    val observaciones_cita : String,
    val fecha_cita : String,
    val hora_cita : String,
    val id_mascota : String,
    val id_usuario : String,
    val id_veterinario : String,
    val estatus_cita : Int
)

