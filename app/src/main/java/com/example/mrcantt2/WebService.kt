package com.example.mrcantt2

import com.example.mrcantt2.Citas.Cita
import com.example.mrcantt2.Citas.CitasResponse
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object AppConstantes {
    const val BASE_URL = "http://192.168.0.7:3000/"
}

interface WebService {

    //poner que reciba el ID
    @GET("/citas")
    suspend fun obtenerCitas(): Response<CitasResponse>

    @GET("/cita/{id_mascota}")
    suspend fun obtenerUsuario(
        @Path("id_mascota") id_mascota: Int
    ): Response<Cita>

    @POST("/cita/add")
    suspend fun agregarCita(
        @Body cita: Cita
    ): Response<String>


    @DELETE("/cita/delete/{id_cita}")
    suspend fun cancelarCita(
        @Path("id_cita") id_cita: Int
    ): Response<String>
}

object RetrofitClient {
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}