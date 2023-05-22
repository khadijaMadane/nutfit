package com.example.nutfit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.drawerlayout.widget.DrawerLayout
import com.example.nutfit.R
import com.google.android.material.navigation.NavigationView

class nutValues : AppCompatActivity() {
    private lateinit var ingredientRowsLinearLayout: LinearLayout
    private lateinit var nutrientMatrix: Array<Array<String>>
    lateinit var toggle: ActionBarDrawerToggle
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nut_values)




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









        val nutrientCount = intent.getIntExtra("nutrientCount", 0)
        val nutrientCountDelete = intent.getIntExtra("nutrientCountDelete", 0)
        val resultIng = intent.getIntExtra("resultIng", 0)
        val resultNut = nutrientCount - nutrientCountDelete
        println("nbre des nutriment est : $resultNut")
        println("nbre des ingredient est : $resultIng")


        ingredientRowsLinearLayout = findViewById(R.id.ingredientRowsLinearLayout)

         nutrientMatrix = createNutrientMatrix(resultIng, resultNut)

        createIngredientRows(resultIng, resultNut)

        val submitButton = findViewById<Button>(R.id.submitButton)


        submitButton.setOnClickListener {

           for (i in nutrientMatrix.indices) {
                for (j in nutrientMatrix[i].indices) {
                    Log.d("Nutrient Matrix", "Ingredient $i, Nutrient $j: ${nutrientMatrix[i][j]}")
                }
            }
            val intent = Intent(this, menuuPage::class.java)
            startActivity(intent)

        }

    }
        private fun createIngredientRows(numIngredients: Int, numNutrients: Int) {
            ingredientRowsLinearLayout.removeAllViews()
           // val nutrientMatrix = Array(numIngredients) { Array(numNutrients) { "" } }

            for (i in 1..numIngredients) {
                val horizontalScrollView = HorizontalScrollView(this)
                val horizontalLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                }

                val ingredientEditText = TextView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    hint = "Ingredient $i"
                    setPadding(16, 16, 16, 16)
                }

                horizontalLayout.addView(ingredientEditText)

                val nutrientEditTexts = mutableListOf<EditText>()

                for (j in 1..numNutrients) {
                    val nutrientEditText = EditText(this).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        hint = "Nutrient $j"
                        setPadding(16, 16, 16, 16)
                    }


                    nutrientEditText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                        override fun afterTextChanged(s: Editable?) {
                            nutrientMatrix[i - 1][j - 1] = s.toString()
                        }
                    })

                    nutrientEditTexts.add(nutrientEditText)
                    horizontalLayout.addView(nutrientEditText)
                }

                horizontalScrollView.addView(horizontalLayout)
                ingredientRowsLinearLayout.addView(horizontalScrollView)


            }
        }

    private fun createNutrientMatrix(numIngredients: Int, numNutrients: Int): Array<Array<String>> {
        val nutrientMatrix = Array(numIngredients) { Array(numNutrients) { "" } }

        for (i in 0 until numIngredients) {
            val ingredientRowLayout = ingredientRowsLinearLayout.getChildAt(i) as? HorizontalScrollView
            val horizontalLayout = ingredientRowLayout?.getChildAt(0) as? LinearLayout

            for (j in 0 until numNutrients) {
                val nutrientEditText = horizontalLayout?.getChildAt(j + 1) as? EditText
                nutrientMatrix[i][j] = nutrientEditText?.text?.toString() ?: ""
            }
        }

        return nutrientMatrix
    }

    companion object {
        const val NUM_INGREDIENTS = "num_ingredients"
        const val NUM_NUTRIENTS = "num_nutrients"
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
