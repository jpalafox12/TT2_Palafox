package com.example.mrcantt2.Citas

import com.google.gson.annotations.SerializedName

data class CitasResponse(
    @SerializedName("listaCitas") var listaCitas: ArrayList<Cita>
)
