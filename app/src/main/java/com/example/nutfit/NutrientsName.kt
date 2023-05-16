package com.example.nutfit

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Half.toFloat
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.nutfit.UserAdapterNut

class NutrientsName : AppCompatActivity() {
    private var nutrientCount = 0


    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserDataNut>
    private lateinit var userAdapter: UserAdapterNut
    val nutrientNames = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrients_name)
        //val aa= UserAdapterNut()

        val nextButton = findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener {
            displayNutNameArray()
            val intent = Intent(this,nutValues::class.java).also{
                it.putExtra("nutrientCount", nutrientCount)
                startActivity(it)
            }

        }

        /**set List*/
        userList = ArrayList()
        /**set find Id*/
        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        /**set adapter*/
        userAdapter = UserAdapterNut(this,userList)
        /**setRecycler view adapter*/
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter
        /**set Dialog*/
        addsBtn.setOnClickListener{

            addInfo()

        }
    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_item_nut,null)
        /**set view*/
        val nutName = v.findViewById<EditText>(R.id.nutName)
        val nutObj = v.findViewById<EditText>(R.id.nutObj)
        val nutSym = v.findViewById<EditText>(R.id.nutSym)
        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)

        addDialog.setPositiveButton("Ok"){
                dialog,_->
            nutrientCount++
            val namenut = nutName.text.toString()
            val nobjectif = nutObj.text.toString()
            val nsymbole = nutSym.text.toString()


            userList.add(UserDataNut("name: $namenut","objectif: $nobjectif","operateur: $nsymbole"))
            nutrientNames.add(namenut)
            nutrientNames.add(nobjectif)
            nutrientNames.add(nsymbole)
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Adding User Information Success", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"cancel", Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()


    }


    private fun displayNutNameArray(): ArrayList<String> {
        // affichage des valeurs stockées dans le tableau
        for (nutName in nutrientNames) {
            Log.d("Nutrient Name", nutName)
        }
        return nutrientNames
    }












}