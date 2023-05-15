package com.example.ttmrcan

data class Cita(
    val id_cita: Int,
    val tipo_cita: String,
    val descripcion_cita: String,
    val observaciones_cita: String?,
    val fecha_cita: String,
    val hora_cita: String,
    val id_mascota: Int,
    val id_usuario: Int,
    val id_veterinario: Int,
    val estatus_cita: Int
)


