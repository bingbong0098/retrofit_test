package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.model.ResponseMoviesList
import com.example.retrofit.server.ApiClient
import com.example.retrofit.server.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding



    private val moviesAdapter by lazy { MyAdapter() }

    // ApiServices
    private val api : ApiServices by lazy {
        ApiClient().getClient().create(ApiServices::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InitViews
        binding.apply {
            //Show Loading
            progressBar.visibility = View.VISIBLE

            //Call Movies Api
            val callMoviesApi = api.moviesList(3)
            callMoviesApi.enqueue(object : Callback<ResponseMoviesList>{
                override fun onResponse(call: Call<ResponseMoviesList>, response: Response<ResponseMoviesList>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        response.body()?.let { itBody->
                            itBody.data?.let { itData ->
                                if (itData.isNotEmpty()){
                                    moviesAdapter.differ.submitList(itData)

                                    recyclerView.apply {
                                        layoutManager = LinearLayoutManager(this@MainActivity)
                                        adapter = moviesAdapter
                                    }
                                }

                            }
                        }
                    }

                }

                override fun onFailure(call: Call<ResponseMoviesList>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Log.e("onFailure","Err : ${t.message}")

                }

            })
        }




    }
}