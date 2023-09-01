package lv.semyonmoroshek.chililabstask.data.network

import lv.semyonmoroshek.chililabstask.data.model.GiphyApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String
    ): Response<GiphyApiResponse>
}