package com.example.nutfit

import android.annotation.SuppressLint
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
import com.google.android.material.navigation.NavigationView




class nutValues : AppCompatActivity() {
    private lateinit var ingredientRowsLinearLayout: LinearLayout
    private lateinit var nutrientMatrix: Array<Array<Double>>
    private lateinit var transposedMatrix: Array<Array<Double>>
    lateinit var toggle: ActionBarDrawerToggle

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nut_values)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutId)
        val navView: NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT)
                    .show()
                R.id.sitting -> Toast.makeText(
                    applicationContext,
                    "Clicked Sittings",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.aide -> Toast.makeText(applicationContext, "Clicked aide", Toast.LENGTH_SHORT)
                    .show()
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

//

        val nutrientCount = intent.getIntExtra("nutrientCount", 0)
        val nutrientCountDelete = intent.getIntExtra("nutrientCountDelete", 0)
        val ingredientCount = intent.getIntExtra("ingredientCount", 0)
        val ingredientCountDelete = intent.getIntExtra("ingredientCountDelete", 0)

        val nameArray = intent.getStringArrayListExtra("nameArray")
        val objArray = intent.getStringArrayListExtra("objArray")
        val optArray = intent.getStringArrayListExtra("optArray")
        val nameIngArray = intent.getStringArrayListExtra("nameIngArray")
        val prixIngArray = intent.getStringArrayListExtra("prixIngArray")

        val resultIng = ingredientCount - ingredientCountDelete
        val resultNut = nutrientCount - nutrientCountDelete
        println("nbre des nutriment est : $resultNut")
        println("nbre des ingredient est : $resultIng")

        println("/*/* new table name :" + nameArray)
        println("/*/* new table obj :" + objArray)
        println("/*/* new table opt :" + optArray)
        println("/*/* new table name ing :" + nameIngArray)
        println("/*/* new table price ing :" + prixIngArray)


        nutrientMatrix = Array(resultIng) { Array(resultNut) { 0.0 } }

        for (i in objArray!!.indices) {
            Log.d("objectif array", "objectif $i,: ${objArray[i]}")
        }

        val objDoubleArray = objArray.map {
            try {
                it.toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }
        }.toDoubleArray()
        val prixDoubleArray = prixIngArray?.map {
            try {
                it.toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }
        }?.toDoubleArray()


        ingredientRowsLinearLayout = findViewById(R.id.ingredientRowsLinearLayout)





        createIngredientRows(resultIng, resultNut)

        val submitButton = findViewById<Button>(R.id.submitButton)


        submitButton.setOnClickListener {



            transposedMatrix = transposeMatrix(nutrientMatrix)

            for (i in transposedMatrix.indices) {
                for (j in transposedMatrix[i].indices) {
                    Log.d(
                        "transposed Matrix ",
                        "Ingredient $i, Nutrient $j: ${transposedMatrix[i][j]}"
                    )
                }
            }
            nutrientMatrix = createNutrientMatrix(
                resultIng,
                resultNut,
                prixDoubleArray?.toList() as ArrayList<Any>,
                objDoubleArray.toList() as ArrayList<Any>,
                transposedMatrix
            )


            for (i in nutrientMatrix.indices) {
                for (j in nutrientMatrix[i].indices) {
                    Log.d("Nutrient Matrix", "Ingredient $i, Nutrient $j: ${nutrientMatrix[i][j]}")
                }
            }

             nutrientMatrix = createNutrientMatrix(resultIng, resultNut,
                 prixIngArray as ArrayList<Double>,objArray as ArrayList<Double> , transposedMatrix)
            for (i in nutrientMatrix.indices) {
                for (j in nutrientMatrix[i].indices) {
                    Log.d("Nutrient MatrixResultat", "Ingredient $i, Nutrient $j: ${nutrientMatrix[i][j]}")
                }
            }
          //  numbIng(resultIng)
           // numbNut(resultNut)



            val intent = Intent(this, simplexe::class.java).apply {
                putExtra("resultIng", resultIng)
                putExtra("resultNut", resultNut)
                putExtra("nutrientMatrix", nutrientMatrix)
            }
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
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

                }


                nutrientEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                       // nutrientMatrix[i - 1][j - 1] = s.toString().toDouble()
                        val value = s.toString().toDoubleOrNull()
                        if (value != null) {
                            nutrientMatrix[i - 1][j - 1] = value
                        } else {
                            nutrientMatrix[i - 1][j - 1] = 0.0 // Ou une valeur par défaut appropriée
                        }
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
            val ingredientRowLayout =
                ingredientRowsLinearLayout.getChildAt(i) as? HorizontalScrollView
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
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun transposeMatrix(matrix: Array<Array<Double>>): Array<Array<Double>> {
        val numRows = matrix.size
        val numCols = matrix[0].size

        val transposedMatrix = Array(numCols) { Array(numRows) { 0.0 } }

        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                transposedMatrix[j][i] = matrix[i][j]
            }
        }


        return transposedMatrix
    }

    private fun createNutrientMatrix(
        numIngredients: Int,
        numNutrients: Int,
        prixIngArray: List<Any>,
        objArray: List<Any>,
        transposedMatrix: Array<Array<Double>>
    ): Array<Array<Double>> {
        val nutrientMatrix = Array(numIngredients + 1) { Array(numNutrients + 1) { 0.0 } }
        nutrientMatrix[numIngredients][numNutrients] = 0.0

        for (i in 0 until numIngredients) {
            for (j in 0 until numNutrients) {
                nutrientMatrix[i][j] = transposedMatrix[i][j]
            }
            val convertedValue = objArray[i].toString().toDoubleOrNull() ?: 0.0
            nutrientMatrix[i][numNutrients] = convertedValue
           // nutrientMatrix[i][numNutrients]=objArray[i]

        }

        for (j in 0 until numNutrients) {
            val convertedValuee = prixIngArray[j].toString().toDoubleOrNull() ?: 0.0
            nutrientMatrix[numIngredients][j] = convertedValuee
           // nutrientMatrix[numIngredients][j] = prixIngArray[j]
        }

        return nutrientMatrix
    }



}