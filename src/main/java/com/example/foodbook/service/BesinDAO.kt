package com.example.foodbook.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodbook.model.Besin
import java.util.*
import java.util.logging.Handler
@Dao
interface BesinDAO {


    @Insert
    suspend fun insertAll(vararg besin: Besin) : List<Long>


    @Query("SELECT * FROM besin")
    suspend fun getAllBesin() : List<Besin>

    @Query("SELECT * FROM besin WHERE uuid = :besinId")
    suspend fun getBesin(besinId : Int) : Besin

    @Query("DELETE FROM besin")
    suspend fun deleteBesinAll()
}