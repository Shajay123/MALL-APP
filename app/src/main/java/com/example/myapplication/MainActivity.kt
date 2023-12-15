package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.setAlpha
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Change to your appropriate binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inflate the correct binding layout
        val view = binding.root
        setContentView(view)

        // Your button code remains here
        val buttonNext = findViewById<Button>(R.id.btnNext)
        buttonNext.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            startActivity(intent)
        }

        // Initialize and populate your card list
        val cardlist = ArrayList<CardView>() // Corrected syntax
            cardlist.add(CardView(R.drawable.homeless,"HOMELESS"))
            cardlist.add(CardView(R.drawable.egg_milk,"EGG & MILK"))
            cardlist.add(CardView(R.drawable.stray_dog,"STRAY DOG"))
            cardlist.add(CardView(R.drawable.wish_video,"WISH VIDEO"))
            cardlist.add(CardView(R.drawable.cake,"CAKE CUTTING"))
            cardlist.add(CardView(R.drawable.fourk,"CELEBRATION PARTY"))
            cardlist.add(CardView(R.drawable.biryani,"CHICKEN BRIYANI"))
            cardlist.add(CardView(R.drawable.blankets,"BLANKETS"))
            cardlist.add(CardView(R.drawable.education,"EDUCATION"))
            cardlist.add(CardView(R.drawable.gift,"CHRISTMAS GIFT"))
            cardlist.add(CardView(R.drawable.grocery_kit,"GROCERY KIT"))
            cardlist.add(CardView(R.drawable.orphanage,"ORPHANAGE"))
            cardlist.add(CardView(R.drawable.tree,"TREE PLANTING"))

        val adapter = CardAdapter(cardlist)

        binding.CarouselRecyclerview.adapter = adapter // Assuming CarouselRecyclerview exists in your layout

        // Apply attributes if provided by your custom implementation
        binding.CarouselRecyclerview.set3DItem(true)
        binding.CarouselRecyclerview.setAlpha(true)
        binding.CarouselRecyclerview.setInfinite(true)
    }
}
