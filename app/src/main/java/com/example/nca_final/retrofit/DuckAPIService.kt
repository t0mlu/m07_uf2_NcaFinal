package com.example.nca_final.retrofit

import com.example.nca_final.model.entities.Duck
import retrofit2.Call
import retrofit2.http.GET

interface DuckAPIService {
    @GET("ducks")
    fun getAllDucks(): Call<List<Duck>>
}