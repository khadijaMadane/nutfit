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
    val nutrientObj = ArrayList<String>()
    val nutrientSym = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrients_name)


        val ingredientCount = intent.getIntExtra("ingredientCount", 0)
        val ingredientCountDelete = intent.getIntExtra("ingredientCountDelete",0)

        val nameIngArray = intent.getStringArrayListExtra("nameIngArray")
        val prixIngArray = intent.getStringArrayListExtra("prixIngArray")

        val nextButton = findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener {
            //displayNutNameArray()
            val nutrientCountDelete = userAdapter.nutrientCountDelete
            val it = Intent(this,nutValues::class.java).also {
                it.putExtra("nutrientCount", nutrientCount)
                it.putExtra("nutrientCountDelete", nutrientCountDelete)
                //name of nutrient
                println("my user list"+userList)
                val nameArray = mutableListOf<String>()
                for (user in userList) {
                    nameArray.add(user.nutName)
                }
                println("my user list new table$nameArray")
                it.putStringArrayListExtra("nameArray", ArrayList(nameArray))
                //objectif of nutrient
                val objArray = mutableListOf<String>()
                for (user in userList) {
                    objArray.add(user.nutObj)
                }
                println("my user list new obj table$objArray")
                it.putStringArrayListExtra("objArray", ArrayList(objArray))
                //operateur of nutrient
                val optArray = mutableListOf<String>()
                for (user in userList) {
                    optArray.add(user.nutSym)
                }
                println("my user list new opt table$optArray")
                it.putStringArrayListExtra("optArray", ArrayList(optArray))

                it.putStringArrayListExtra("nameIngArray", ArrayList(nameIngArray))
                it.putStringArrayListExtra("prixIngArray", ArrayList(prixIngArray))

                it.putExtra("ingredientCount", ingredientCount)
                it.putExtra("ingredientCountDelete", ingredientCountDelete)
                startActivity(it)
            }
            //println("2*** nbre des ingredients est : $resultIng")
            //displayNutObjArray()
            //displayNutSymArray()

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
            //userList.add(UserDataNut(namenut,nobjectif,nsymbole))
            nutrientNames.add(namenut)
            nutrientObj.add(nobjectif)
            nutrientSym.add(nsymbole)
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

    private fun displayNutObjArray(): ArrayList<String> {
        // affichage des valeurs stockées dans le tableau
        for (nutObj in nutrientObj) {
            Log.d("Nutrient Objectif :", nutObj)
        }
        return nutrientObj
    }
    private fun displayNutSymArray(): ArrayList<String> {
        // affichage des valeurs stockées dans le tableau
        for (nutSym in nutrientSym) {
            Log.d("Nutrient operator :", nutSym)
        }
        return nutrientSym
    }


}

