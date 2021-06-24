package com.example.foodbook.service

import com.example.foodbook.model.Besin
import io.reactivex.Single
import retrofit2.http.GET

interface BesinApi{

    //BTK20-JSONVeriSeti/blob/master/besinler.json
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin(): Single<List<Besin>>




}