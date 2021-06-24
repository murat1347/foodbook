package com.example.foodbook.util

import android.content.Context
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodbook.R

fun ImageView.gorselindir(url : String?, placeholder: CircularProgressDrawable){

    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher) // hata ekranı görüntüsü olustur.
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}
fun placeholderYap(context : Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth=8f // progess bar kalınlığı
        centerRadius=40f //yarıçapı
        start()

    }
}