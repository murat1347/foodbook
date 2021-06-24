package com.example.foodbook.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbook.model.Besin
import com.example.foodbook.service.BesinApiService
import com.example.foodbook.service.BesinDatabase
import com.example.foodbook.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor= MutableLiveData<Boolean>()
    private var guncellemeZamani = 10*60*1000*1000*1000L


    private val besinApiService = BesinApiService()
    private val disposable = CompositeDisposable()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())


    fun refleshData(){

        val kaydedilmeZamani = ozelSharedPreferences.zamanial()
        if (kaydedilmeZamani !=null && kaydedilmeZamani !=0L && System.nanoTime() - kaydedilmeZamani <guncellemeZamani)
        {
            //sql'dan al
            verileriSqltanAl()
        }else
        {
            verileriInternettenAl()
        }



    }

    fun refleshfromInternet(){
        verileriInternettenAl()
    }

    private fun verileriSqltanAl(){

        launch {
            val besinListesi = BesinDatabase(getApplication()).besinDao().getAllBesin()
            besinlerigoster(besinListesi)
            Toast.makeText(getApplication(),"Besinleri Roomdan aldık",Toast.LENGTH_LONG).show()
        }
    }
    private fun verileriInternettenAl(){
        besinYukleniyor.value=true
        disposable.add(
        besinApiService.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                override fun onSuccess(t: List<Besin>) {

                    sqlSakla(t)
                    //Başarılı Olursa
                    Toast.makeText(getApplication(),"Besinleri internetten aldık",Toast.LENGTH_LONG).show()

                }

                override fun onError(e: Throwable) {

                    besinHataMesaji.value=true
                    besinYukleniyor.value=false
                    Log.d("TAG", "onError:"+e)

                    //Başarısız Olursa
                }

            })
        )
    }

    private fun besinlerigoster(besinListesi : List<Besin>){

        besinler.value=besinListesi
        besinHataMesaji.value=false
        besinYukleniyor.value=false
    }

    private fun sqlSakla(besinListesi : List<Besin>){

        launch {
            val dao = BesinDatabase(context = getApplication()).besinDao()
            dao.deleteBesinAll()
            val uuidListesi =dao.insertAll(*besinListesi.toTypedArray())
            var i=0
            while (i<besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i = i+1
            }
                besinlerigoster(besinListesi)

        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}