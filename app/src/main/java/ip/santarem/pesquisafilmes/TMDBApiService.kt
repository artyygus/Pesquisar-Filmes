package ip.santarem.pesquisafilmes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiService {
    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<MovieResponse>
}