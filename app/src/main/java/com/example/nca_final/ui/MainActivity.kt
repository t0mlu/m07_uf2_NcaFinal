package com.example.nca_final.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.nca_final.model.AppDatabase
import com.example.nca_final.R
import com.example.nca_final.databinding.ActivityMainBinding
import com.example.nca_final.model.entities.Duck
import com.example.nca_final.retrofit.DuckAPIService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Tutorial ROOM flow: https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow
// Tutorial repo: https://github.com/google-developer-training/android-basics-kotlin-bus-schedule-app

// SearchView with flow: https://proandroiddev.com/implementing-search-filter-using-kotlin-channels-and-flows-in-your-android-application-df7c96e58b19

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {
            if (!database.DuckDAO().duckHasRecords()) {
                getRetrofit().create(DuckAPIService::class.java)
                    .getAllDucks().enqueue(object : Callback<List<Duck>> {
                        override fun onResponse(
                            call: Call<List<Duck>>,
                            response: retrofit2.Response<List<Duck>>
                        ) {
                            GlobalScope.launch {
                                response.body()?.let {
                                    database.DuckDAO().insertDuckList(it)
                                }
                            }
                        }

                        override fun onFailure(call: Call<List<Duck>>, t: Throwable) {
                            throw t
                        }
                    })
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/t0mlu/m07_uf1_nca2/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}