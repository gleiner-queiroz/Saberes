package com.example.saberesestudy.util

import com.example.saberesestudy.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    // LOGIN
    @POST("login.php")
    fun login(@Body req: LoginRequest): Call<LoginResponse>

    // LISTA DE CONTEÚDOS
    @GET("listar_conteudos.php")
    fun listarConteudos(
        @Query("materia") materia: String? = null,
        @Query("serie") serie: String? = null
    ): Call<List<Conteudo>>

    // DETALHE DO CONTEÚDO
    @GET("conteudo_detalhe.php")
    fun conteudoDetalhe(
        @Query("id") id: Int
    ): Call<ConteudoDetalhe>

    // CHAT - LISTAR MENSAGENS
    @GET("listar_mensagens.php")
    fun listarMensagens(
        @Query("conteudo_id") conteudoId: Int,
        @Query("aluno_id") alunoId: Int
    ): Call<List<Mensagem>>

    // CHAT - ENVIAR MENSAGEM
    @POST("enviar_mensagem.php")
    fun enviarMensagem(
        @Body req: MensagemRequest
    ): Call<MensagemResponse>

    // FAVORITAR / DESFAVORITAR (toggle)
    @POST("favoritar.php")
    fun favoritar(
        @Body req: FavoritoRequest
    ): Call<FavoritoResponse>

    // LISTAR FAVORITOS DO ALUNO
    @GET("favoritos.php")
    fun listarFavoritos(
        @Query("aluno_id") alunoId: Int
    ): Call<List<Conteudo>>

    // VERIFICAR SE JÁ É FAVORITO
    @GET("favorito_status.php")
    fun favoritoStatus(
        @Query("aluno_id") alunoId: Int,
        @Query("conteudo_id") conteudoId: Int
    ): Call<FavoritoResponse>
}

object ApiClient {

    // Ajuste se seu backend estiver em outro IP/porta
    private const val BASE_URL = "http://10.0.2.2/saberes/api/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
