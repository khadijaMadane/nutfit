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
    val ingredientPrix = ArrayList<String>()

//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingregients_name)

        val nextButton = findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener {

            val ingredientCountDelete = userAdapter.ingredientCountDelete


            val it = Intent(this, NutrientsName::class.java).also {
                it.putExtra("ingredientCount", ingredientCount)
                it.putExtra("ingredientCountDelete", ingredientCountDelete)

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

        }


            val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutId)
            val navView: NavigationView = findViewById(R.id.nav_view)
            toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> Toast.makeText(
                        applicationContext,
                        "Clicked Home",
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.sitting -> Toast.makeText(
                        applicationContext,
                        "Clicked Sittings",
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.aide -> Toast.makeText(
                        applicationContext,
                        "Clicked aide",
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.recommencer -> Toast.makeText(
                        applicationContext,
                        "Clicked recommencer",
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.signout -> Toast.makeText(
                        applicationContext,
                        "Clicked signout",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                true

            }

            navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.home -> {
                        val intent = Intent(this, changePasswrord::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.sitting -> {
                        // Handle "Settings" click
                        val intent = Intent(this, changeEmail::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.aide -> {
                        // Handle "Help" click
                        val intent = Intent(this, IngregientsName::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.recommencer -> {
                        val intent = Intent(this, IngregientsName::class.java)
                        startActivity(intent)
                        // Handle "Restart" click
                        true
                    }
                    R.id.signout -> {
                        showSignOutConfirmationDialog()
                        // Handle "Sign Out" click
                        true
                    }
                    else -> false
                }
            }




            /**set List*/
            userList = ArrayList()
            /**set find Id*/
            addsBtn = findViewById(R.id.addingBtn)
            recv = findViewById(R.id.mRecycler)
            /**set adapter*/
            userAdapter = UserAdapterIng(this, userList)
            /**setRecycler view adapter*/
            recv.layoutManager = LinearLayoutManager(this)
            recv.adapter = userAdapter
            /**set Dialog*/
            addsBtn.setOnClickListener { addInfo() }

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







    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
    private fun performSignOut() {
        // Perform sign out logic here
        // For example, navigate to the login screen or clear user session
        val intent = Intent(this, SignIn::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    private fun showSignOutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Sign Out")
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("Yes") { dialog, _ ->
                // Handle sign out
                performSignOut()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}