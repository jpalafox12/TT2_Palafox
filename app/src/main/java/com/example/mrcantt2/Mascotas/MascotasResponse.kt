package com.example.ttmrcan.Mascotas

import com.google.gson.annotations.SerializedName

data class MascotasResponse(
    @SerializedName("listaMascotas") var listaMascotas: ArrayList<Mascota>
)
