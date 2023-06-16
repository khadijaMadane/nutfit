package com.example.nutfit

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView

class MainAcceuil : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_acceuil)

        ////////////////////////////////orange
        val builder1 = AlertDialog.Builder(this)
        val inflater1 = LayoutInflater.from(this)
        val customView1 = inflater1.inflate(R.layout.orange, null)
        builder1.setView(customView1)

        val card1 = findViewById<CardView>(R.id.card1)
        card1.setOnClickListener {
            val alertDialog1 = builder1.create()

            val parent = customView1.parent as? ViewGroup
            parent?.removeView(customView1) // Supprimer la vue du parent actuel

            alertDialog1.setView(customView1)
            alertDialog1.show()
        }
        ///////////////////////////////////////appel
        val builder2 = AlertDialog.Builder(this)
        val inflater2 = LayoutInflater.from(this)
        val customView2 = inflater2.inflate(R.layout.appel, null)
        builder2.setView(customView2)

        val card2 = findViewById<CardView>(R.id.card2)
        card2.setOnClickListener {
            val alertDialog = builder2.create()

            val parent = customView2.parent as? ViewGroup
            parent?.removeView(customView2) // Supprimer la vue du parent actuel

            alertDialog.setView(customView2)
            alertDialog.show()
        }

        ///////////////////////////////////////////carrot
        val builder3 = AlertDialog.Builder(this)
        val inflater3 = LayoutInflater.from(this)
        val customView3 = inflater3.inflate(R.layout.carrot, null)
        builder3.setView(customView3)

        val card3 = findViewById<CardView>(R.id.card3)
        card3.setOnClickListener {
            val alertDialog = builder3.create()

            val parent = customView3.parent as? ViewGroup
            parent?.removeView(customView3) // Supprimer la vue du parent actuel

            alertDialog.setView(customView3)
            alertDialog.show()
        }


        ///////////////////////////////////////////potato
        val builder4 = AlertDialog.Builder(this)
        val inflater4 = LayoutInflater.from(this)
        val customView4 = inflater4.inflate(R.layout.potato, null)
        builder4.setView(customView4)

        val card4 = findViewById<CardView>(R.id.card4)
        card4.setOnClickListener {
            val alertDialog = builder4.create()

            val parent = customView4.parent as? ViewGroup
            parent?.removeView(customView4) // Supprimer la vue du parent actuel

            alertDialog.setView(customView4)
            alertDialog.show()
        }


        ///////////////////////////////////////////zucchini
        val builder5 = AlertDialog.Builder(this)
        val inflater5 = LayoutInflater.from(this)
        val customView5 = inflater5.inflate(R.layout.zucchini, null)
        builder5.setView(customView5)

        val card5 = findViewById<CardView>(R.id.card5)
        card5.setOnClickListener {
            val alertDialog = builder5.create()

            val parent = customView5.parent as? ViewGroup
            parent?.removeView(customView5) // Supprimer la vue du parent actuel

            alertDialog.setView(customView5)
            alertDialog.show()
        }


        //////////////////////////////////////////6/artichoke
        val builder6 = AlertDialog.Builder(this)
        val inflater6 = LayoutInflater.from(this)
        val customView6 = inflater6.inflate(R.layout.artichoke, null)
        builder6.setView(customView6)

        val card6 = findViewById<CardView>(R.id.card6)
        card6.setOnClickListener {
            val alertDialog = builder6.create()

            val parent = customView6.parent as? ViewGroup
            parent?.removeView(customView6) // Supprimer la vue du parent actuel

            alertDialog.setView(customView6)
            alertDialog.show()
        }


        //////////////////////////////////////////7/tomato
        val builder7 = AlertDialog.Builder(this)
        val inflater7 = LayoutInflater.from(this)
        val customView7 = inflater7.inflate(R.layout.tomato, null)
        builder7.setView(customView7)

        val card7 = findViewById<CardView>(R.id.card7)
        card7.setOnClickListener {
            val alertDialog = builder7.create()

            val parent = customView7.parent as? ViewGroup
            parent?.removeView(customView7) // Supprimer la vue du parent actuel

            alertDialog.setView(customView7)
            alertDialog.show()
        }


        //////////////////////////////////////////8/boroccoli
        val builder8 = AlertDialog.Builder(this)
        val inflater8 = LayoutInflater.from(this)
        val customView8 = inflater8.inflate(R.layout.broccoli, null)
        builder8.setView(customView8)

        val card8 = findViewById<CardView>(R.id.card8)
        card8.setOnClickListener {
            val alertDialog = builder8.create()

            val parent = customView8.parent as? ViewGroup
            parent?.removeView(customView8) // Supprimer la vue du parent actuel

            alertDialog.setView(customView8)
            alertDialog.show()
        }


        //////////////////////////////////////////9/pepper
        val builder9 = AlertDialog.Builder(this)
        val inflater9 = LayoutInflater.from(this)
        val customView9 = inflater9.inflate(R.layout.pepper, null)
        builder9.setView(customView9)

        val card9 = findViewById<CardView>(R.id.card9)
        card9.setOnClickListener {
            val alertDialog = builder9.create()

            val parent = customView9.parent as? ViewGroup
            parent?.removeView(customView9) // Supprimer la vue du parent actuel

            alertDialog.setView(customView9)
            alertDialog.show()
        }


        //////////////////////////////////////////10/banana
        val builder10 = AlertDialog.Builder(this)
        val inflater10 = LayoutInflater.from(this)
        val customView10 = inflater10.inflate(R.layout.banana, null)
        builder10.setView(customView10)

        val card10 = findViewById<CardView>(R.id.card10)
        card10.setOnClickListener {
            val alertDialog = builder10.create()

            val parent = customView10.parent as? ViewGroup
            parent?.removeView(customView10) // Supprimer la vue du parent actuel

            alertDialog.setView(customView10)
            alertDialog.show()
        }


        //////////////////////////////////////////11/pineapple
        val builder11 = AlertDialog.Builder(this)
        val inflater11 = LayoutInflater.from(this)
        val customView11 = inflater11.inflate(R.layout.pineappel, null)
        builder11.setView(customView11)

        val card11 = findViewById<CardView>(R.id.card11)
        card11.setOnClickListener {
            val alertDialog = builder11.create()

            val parent = customView11.parent as? ViewGroup
            parent?.removeView(customView11) // Supprimer la vue du parent actuel

            alertDialog.setView(customView11)
            alertDialog.show()
        }

        ///////////////////////////////buton to start calcul
        val pass = findViewById<Button>(R.id.pass)
        pass.setOnClickListener {
            val intent = Intent(this, IngregientsName::class.java)
            startActivity(intent)
        }

        //////////////////////////////button more informations
        val moreInfo = findViewById<Button>(R.id.moreInfo)
        moreInfo.setOnClickListener {
            val intent1 = Intent(this, MainInformation::class.java)
            startActivity(intent1)
        }

    }
}