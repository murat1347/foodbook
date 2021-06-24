package com.example.foodbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.foodbook.R
import com.example.foodbook.util.gorselindir
import com.example.foodbook.util.placeholderYap
import com.example.foodbook.viewmodel.BesinDetayiViewModel
import kotlinx.android.synthetic.main.fragment_food_detail.*


class FoodDetailFragment : Fragment() {

    private lateinit var viewModel: BesinDetayiViewModel
    private var foodId=0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            foodId=FoodDetailFragmentArgs.fromBundle(it).foodId
            println(foodId)
        }


        viewModel = ViewModelProviders.of(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiniAL(foodId)


        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin->
            besin?.let {
                besinIsim.text=it.besinIsım
                besinkalori.text=it.besinKalori
                besinKarbonhidrad.text=it.besinKarbonhidrat
                besinProtein.text=it.besinProtein
                besinyag.text=it.besinYag
                context?.let {
                    foodImage.gorselindir(besin.besinGorsel, placeholderYap(it))
                }

            }

        })

    }
}