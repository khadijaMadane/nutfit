package com.example.nutfit



import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow

class nutValues : AppCompatActivity() {
    private lateinit var ingredientRowsLinearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nut_values)

        val nutrientCount = intent.getIntExtra("nutrientCount", 0)
        val nutrientCountDelete = intent.getIntExtra("nutrientCountDelete",0)
        val resultIng = intent.getIntExtra("resultIng",0)
        val resultNut =nutrientCount-nutrientCountDelete
        println("nbre des nutriment est : $resultNut")
        println("nbre des ingredient est : $resultIng")

        ingredientRowsLinearLayout = findViewById(R.id.ingredientRowsLinearLayout)

        val numIngredientsEditText = findViewById<EditText>(R.id.numIngredientsEditText)
        val numNutrientsEditText = findViewById<EditText>(R.id.numNutrientsEditText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val numIngredients = numIngredientsEditText.text.toString().toIntOrNull() ?: 0
            val numNutrients = numNutrientsEditText.text.toString().toIntOrNull() ?: 0

            createIngredientRows(numIngredients, numNutrients)
        }
    }

    private fun createIngredientRows(numIngredients: Int, numNutrients: Int) {
        ingredientRowsLinearLayout.removeAllViews()

        for (i in 1..numIngredients) {
            val ingredientEditText = EditText(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                hint = "Ingredient $i"
                setPadding(16, 16, 16, 16)
            }

            val nutrientEditTexts = mutableListOf<EditText>()

            for (j in 1..numNutrients) {
                val nutrientEditText = EditText(this).apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                    hint = "Nutrient $j"
                    setPadding(16, 16, 16, 16)
                }

                nutrientEditTexts.add(nutrientEditText)
            }

            val tableRow = TableRow(this).apply {
                layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
                addView(ingredientEditText)
                nutrientEditTexts.forEach { addView(it) }
            }

            ingredientRowsLinearLayout.addView(tableRow)
        }
    }

    companion object {
        const val NUM_INGREDIENTS = "num_ingredients"
        const val NUM_NUTRIENTS = "num_nutrients"
    }
}


