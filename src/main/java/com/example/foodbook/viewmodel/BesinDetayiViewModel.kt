package com.example.foodbook.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbook.model.Besin
import com.example.foodbook.service.BesinDatabase
import kotlinx.coroutines.launch
import java.util.*

class BesinDetayiViewModel(application: Application) : BaseViewModel(application){

    val besinLiveData= MutableLiveData<Besin>()

    fun roomVerisiniAL(uuıd: Int){

        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            val besin= dao.getBesin(uuıd)
            besinLiveData.value=besin
        }
    }


}