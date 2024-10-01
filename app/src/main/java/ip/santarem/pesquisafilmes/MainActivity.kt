package ip.santarem.pesquisafilmes

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter
    private val apiKey = "29badec9cc6cc14199ebf111b7ff8c86"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuração do RecyclerView e Adapter
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = MovieAdapter(mutableListOf())
        recyclerView.adapter = movieAdapter

        // Exemplo de chamada de pesquisa de filmes
        searchMovies("Matrix")
    }

    private fun searchMovies(query: String) {
        val call = RetrofitClient.instance.searchMovies(apiKey, query)

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        movieAdapter.setMovies(movies)
                    } else {
                        Toast.makeText(this@MainActivity, "Nenhum filme encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Erro na resposta da API", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("MainActivity", "Erro ao buscar filmes: ${t.message}")
                Toast.makeText(this@MainActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
