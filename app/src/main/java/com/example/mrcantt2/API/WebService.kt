package com.example.mrcantt2

import com.example.mrcantt2.Citas.CitasResponse
import com.example.ttmrcan.Mascotas.MascotasResponse
import com.example.ttmrcan.Token
import com.example.ttmrcan.Usuario
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object AppConstantes {
    const val BASE_URL = "http://192.168.0.7:3000/"
}

interface WebService {
    //USUARIOS
    @GET("/usuario/{email_usuario}")
    suspend fun obtenerIdUsuario(
        @Path("email_usuario") email_usuario: String?
    ): Response<Usuario>

    @GET("/mascotas/{id_usuario}")
    suspend fun obtenerMascotasUsuario(
        @Path("id_usuario") id_usuario: Int
    ): Response<MascotasResponse>

    @GET("/usuarioLogin/{email_usuario}/{password_usuario}")
    suspend fun checarLogin(
        @Path("email_usuario") email_usuario: String,
        @Path("password_usuario") password_usuario: String
    ): Response<Token>

    @POST("/usuario/add")
    suspend fun agregarUsuario(
        @Body usuario: Usuario
    ): Response<String>

    @PUT("/usuario/update/{id}")
    suspend fun actualizarUsuario(
        @Path("id_usuario") id_usuario: Int,
        @Body usuario: Usuario
    ): Response<String>


    @POST("/enviarCorreo")
    suspend fun enviarCorreo(
        @Body correo: String
    ): Response<String>


    // Obtener todas las citas
    @GET("/citas")
    suspend fun obtenerCitas(): Response<CitasResponse>

    // Obtener las citas de una mascota específica
    @GET("/citas/{id_mascota}")
    suspend fun obtenerCitasPendientes(
        @Path("id_mascota") id_mascota: Int
    ): Response<CitasResponse>

    @DELETE("/cita/delete/{id_cita}")
    suspend fun cancelarCita(
        @Path("id_cita") id_cita: Int
    ): Response<String>
}

object RetrofitClient{
    val webServ: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}