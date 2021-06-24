package com.example.foodbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodbook.R
import com.example.foodbook.adapter.BesinRecylerAdapter
import com.example.foodbook.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_food_lists.*


class FoodListsFragment : Fragment() {

    private lateinit var viewModel: BesinListesiViewModel
    private val recyclerBesinAdapter = BesinRecylerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)
        viewModel.refleshData()
        foodListRecycler.layoutManager=LinearLayoutManager(context)
        foodListRecycler.adapter=recyclerBesinAdapter
        swipeRefleshLayout.setOnRefreshListener {
            foodErrorMesagge.visibility= View.GONE
            foodUploading.visibility=View.VISIBLE
            foodListRecycler.visibility=View.GONE

            viewModel.refleshfromInternet()
            swipeRefleshLayout.isRefreshing=false

        }
        observerLiveData()
    }

    fun observerLiveData(){

        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let {
                foodListRecycler.visibility=View.VISIBLE
                recyclerBesinAdapter.besinListesiniGuncelle(besinler)

            }
        })
        viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer { hata->
            hata?.let {
                if (it){
                    foodErrorMesagge.visibility=View.VISIBLE
                    foodListRecycler.visibility=View.GONE}
                    else
                    {
                        foodErrorMesagge.visibility= android.view.View.GONE

                    }
                }


        })
        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor ->
            yukleniyor?.let {
                if (it){
                    foodListRecycler.visibility=View.GONE
                    foodErrorMesagge.visibility=View.GONE
                    foodUploading.visibility=View.VISIBLE
                }else
                {
                    foodUploading.visibility=View.GONE
                }
            }
        })
    }

}