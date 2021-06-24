package com.example.foodbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.inflate
import com.example.foodbook.R
import com.example.foodbook.model.Besin
import com.example.foodbook.util.gorselindir
import com.example.foodbook.util.placeholderYap
import com.example.foodbook.view.FoodListsFragmentDirections
import kotlinx.android.synthetic.main.food_recyler_row.view.*

class BesinRecylerAdapter(val besinListesi: ArrayList <Besin>) : Adapter<BesinRecylerAdapter.BesinViewHolder>() {

    class BesinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.food_recyler_row,parent,false)
        return BesinViewHolder(view)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.itemView.isim.text = besinListesi.get(position).besinIsım
        holder.itemView.kalori.text = besinListesi.get(position).besinKalori
        holder.itemView.setOnClickListener {
            val action =FoodListsFragmentDirections.actionFoodListsFragmentToFoodDetailFragment(besinListesi.get(position).uuid)
        Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.imageView.gorselindir(besinListesi.get(position).besinGorsel, placeholderYap(holder.itemView.context))
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    fun besinListesiniGuncelle(yeniBesinListesi :List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }
}