package com.example.nutfit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.nutfit.R

class nutValues : AppCompatActivity() {
    private lateinit var ingredientRowsLinearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nut_values)
        ingredientRowsLinearLayout = findViewById(R.id.ingredientRowsLinearLayout)

        val numIngredientsEditText = findViewById<EditText>(R.id.numIngredientsEditText)
        val numNutrientsEditText = findViewById<EditText>(R.id.numNutrientsEditText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val numIngredients = numIngredientsEditText.text.toString().toIntOrNull() ?: 0
            val numNutrients = numNutrientsEditText.text.toString().toIntOrNull() ?: 0

            Log.d("IngredientsActivity", "Num Ingredients: $numIngredients")
            Log.d("IngredientsActivity", "Num Nutrients: $numNutrients")

            createIngredientRows(numIngredients, numNutrients)
        }
    }


    private fun createIngredientRows(numIngredients: Int, numNutrients: Int) {
        ingredientRowsLinearLayout.removeAllViews()

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

                nutrientEditTexts.add(nutrientEditText)
                horizontalLayout.addView(nutrientEditText)
            }

            horizontalScrollView.addView(horizontalLayout)
            ingredientRowsLinearLayout.addView(horizontalScrollView)
        }
    }



    companion object {
        const val NUM_INGREDIENTS = "num_ingredients"
        const val NUM_NUTRIENTS = "num_nutrients"
    }
}
