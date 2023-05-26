package com.example.nutfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class IngregientsName : AppCompatActivity() {

    private var ingredientCount=0

    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserDataIng>
    private lateinit var userAdapter: UserAdapterIng
    val ingredientNames = ArrayList<String>()
    val ingredientPrix = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingregients_name)

        val nextButton = findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener {
            displayIngNameArray()
            val ingredientCountDelete = userAdapter.ingredientCountDelete
            val resultIng =ingredientCount-ingredientCountDelete

            val it = Intent(this, NutrientsName::class.java).also {
                it.putExtra("resultIng", resultIng)

                //name of ingredient
                val nameIngArray = mutableListOf<String>()
                for (user in userList) {
                    nameIngArray.add(user.ingName)
                }
                println("new name table ing $nameIngArray")
                it.putStringArrayListExtra("nameIngArray", ArrayList(nameIngArray))
                //price of ingredient
                val prixIngArray = mutableListOf<String>()
                for (user in userList) {
                    prixIngArray.add(user.ingPrix)
                }
                println("new price table ing $prixIngArray")
                it.putStringArrayListExtra("prixIngArray", ArrayList(prixIngArray))

                startActivity(it)
            }

            //println("1*** nbre des ingredients est : $resultIng")
            displayIngPrxArray()
        }

        /**set List*/
        userList = ArrayList()
        /**set find Id*/
        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        /**set adapter*/
        userAdapter = UserAdapterIng(this,userList)
        /**setRecycler view adapter*/
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter
        /**set Dialog*/
        addsBtn.setOnClickListener{addInfo()}
    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_item_ing,null)
        /**set view*/
        val ingName = v.findViewById<EditText>(R.id.ingName)
        val ingPrix = v.findViewById<EditText>(R.id.ingPrix)
        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)

        addDialog.setPositiveButton("Ok"){
                dialog,_->
            ingredientCount++
            val nameing = ingName.text.toString()
            val prxing = ingPrix.text.toString()
            userList.add(UserDataIng("name: $nameing","price: $prxing"))
            ingredientNames.add(nameing)
            ingredientPrix.add(prxing)
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

    private fun displayIngNameArray(): ArrayList<String> {
        // affichage des valeurs stockées dans le tableau
        for (ingName in ingredientNames) {
            Log.d("ingredient Name :", ingName)
        }
        return ingredientNames
    }
    private fun displayIngPrxArray(): ArrayList<String> {
        // affichage des valeurs stockées dans le tableau
        for (ingPrix in ingredientPrix) {
            Log.d("ingredient price :", ingPrix)
        }
        return ingredientPrix
    }
}