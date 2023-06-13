package com.example.nutfit

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Half.toFloat
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.nutfit.UserAdapterNut
import com.example.nutfit.nutValues
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NutrientsName : AppCompatActivity() {
    private var nutrientCount = 0

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserDataNut>
    private lateinit var userAdapter: UserAdapterNut
    val nutrientNames = ArrayList<String>()
    val nutrientObj = ArrayList<Double>()
    val nutrientSym = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrients_name)
//

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
                println("my user list" + userList)
                val nameArray = mutableListOf<String>()
                for (user in userList) {
                    nameArray.add(user.nutName)
                }
                println("my user list new table$nameArray")
                it.putStringArrayListExtra("nameArray", ArrayList(nameArray))
                //objectif of nutrient
                val objArray = mutableListOf<String>()
                for (user in userList) {
                    objArray.add(user.nutObj.toString())
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
            }}

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)

        val db = FirebaseFirestore.getInstance()
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef = db.collection("users").document(userID) // Remplacez "users" par le nom de votre collection et "userId" par l'ID de l'utilisateur

        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val usernam = document.getString("Name")
                println("userName est : $usernam")// Remplacez "username" par le nom du champ contenant le nom d'utilisateur dans votre document Firestore
                if (usernam != null) {
                    val usernameTextView = headerView.findViewById<TextView>(R.id.username) // Remplacez "usernameTextView" par l'ID de votre TextView
                    usernameTextView.text = usernam
                    usernameTextView.setText(usernam)
                }
            }}


        val authProfile = FirebaseAuth.getInstance()
        val firebaseUser = authProfile.currentUser

        val userEmail= firebaseUser?.getEmail()

        println("email est : $userEmail")


        val emailTextView = headerView.findViewById<TextView>(R.id.email_nav)
        // emailTextView.text = userEmail
        emailTextView.setText(userEmail)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutId)
        val navView: NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home, R.id.sitting, R.id.aide, R.id.recommencer, R.id.signout -> {
                    Toast.makeText(applicationContext, "Clicked ${menuItem.title}", Toast.LENGTH_SHORT).show()
                    menuItem.isChecked = true
                    menuItem.icon?.setTintList(ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.green))) // Change to the desired green color
                    navView.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.green)) // Change to the desired green color
                    true
                }
                else -> false
            }
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
                    val intent = Intent(this, deleteAccount::class.java)
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
                userAdapter = UserAdapterNut(this, userList)
                /**setRecycler view adapter*/
                recv.layoutManager = LinearLayoutManager(this)
                recv.adapter = userAdapter
                /**set Dialog*/
                addsBtn.setOnClickListener {

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
            val nobjectif = nutObj.text.toString().toDouble()
            val nsymbole = nutSym.text.toString()


            userList.add(UserDataNut(namenut,nobjectif, nsymbole))
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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
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