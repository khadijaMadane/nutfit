package com.example.nutfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class menuuPage : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuu_page)
//

        val drawerLayout: DrawerLayout= findViewById(R.id.drawerLayout)
        val navView: NavigationView=findViewById(R.id.nav_view)
        toggle= ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> Toast.makeText(applicationContext,"Clicked Home",Toast.LENGTH_SHORT).show()
                R.id.sitting-> Toast.makeText(applicationContext,"Clicked Sittings",Toast.LENGTH_SHORT).show()
                R.id.aide -> Toast.makeText(applicationContext,"Clicked aide",Toast.LENGTH_SHORT).show()
                R.id.recommencer -> Toast.makeText(applicationContext,"Clicked recommencer",Toast.LENGTH_SHORT).show()
                R.id.signout -> Toast.makeText(applicationContext,"Clicked signout",Toast.LENGTH_SHORT).show()

            }
            true

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}