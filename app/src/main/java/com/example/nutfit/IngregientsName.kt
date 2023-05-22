package com.example.nutfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class IngregientsName : AppCompatActivity() {

    private var ingredientCount=0
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserDataIng>
    private lateinit var userAdapter: UserAdapterIng
    val ingredientNames = ArrayList<String>()
    val ingredientObj = ArrayList<String>()
    val ingredientSym = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingregients_name)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutId)
        val navView: NavigationView =findViewById(R.id.nav_view)
        toggle= ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> Toast.makeText(applicationContext,"Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.sitting-> Toast.makeText(applicationContext,"Clicked Sittings", Toast.LENGTH_SHORT).show()
                R.id.aide -> Toast.makeText(applicationContext,"Clicked aide", Toast.LENGTH_SHORT).show()
                R.id.recommencer -> Toast.makeText(applicationContext,"Clicked recommencer", Toast.LENGTH_SHORT).show()
                R.id.signout -> Toast.makeText(applicationContext,"Clicked signout", Toast.LENGTH_SHORT).show()

            }
            true

        }




        val nextButton = findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener {
            displayIngNameArray()
            val ingredientCountDelete = userAdapter.ingredientCountDelete
            val resultIng =ingredientCount-ingredientCountDelete
            val it = Intent(this, NutrientsName::class.java).also {
                it.putExtra("ingredientCount", ingredientCount)
                it.putExtra("ingredientCountDelete", ingredientCountDelete)
                it.putExtra("resultIng", resultIng)
                startActivity(it)
            }

            //println("1*** nbre des ingredients est : $resultIng")
            displayIngObjArray()
            displayIngSymArray()
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
        val ingObj = v.findViewById<EditText>(R.id.ingObj)
        val ingSym = v.findViewById<EditText>(R.id.ingSym)
        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)

        addDialog.setPositiveButton("Ok"){
                dialog,_->
            ingredientCount++
            val nameing = ingName.text.toString()
            val iobjectif = ingObj.text.toString()
            val isymbole = ingSym.text.toString()
            userList.add(UserDataIng("name: $nameing","objectif: $iobjectif","operateur: $isymbole"))
            ingredientNames.add(nameing)
            ingredientObj.add(iobjectif)
            ingredientSym.add(isymbole)
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
    private fun displayIngObjArray(): ArrayList<String> {
        // affichage des valeurs stockées dans le tableau
        for (ingObj in ingredientObj) {
            Log.d("ingredient Objectif :", ingObj)
        }
        return ingredientObj
    }
    private fun displayIngSymArray(): ArrayList<String> {
        // affichage des valeurs stockées dans le tableau
        for (ingSym in ingredientSym) {
            Log.d("ingredient operator :", ingSym)
        }
        return ingredientSym
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}