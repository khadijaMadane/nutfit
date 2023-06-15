package com.example.nutfit

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class nutValues : AppCompatActivity() {
    private lateinit var ingredientRowsLinearLayout: LinearLayout
    private lateinit var nutrientMatrix: Array<Array<Double>>
    private lateinit var nutrientMatrixx: Array<Array<Double>>
    private lateinit var transposedMatrix: Array<Array<Double>>
    lateinit var toggle: ActionBarDrawerToggle
    private var resultNutTmp = 0
    private var percentOrNot = 0

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nut_values)


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)

        val db = FirebaseFirestore.getInstance()
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef = db.collection("users")
            .document(userID) // Remplacez "users" par le nom de votre collection et "userId" par l'ID de l'utilisateur

        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val usernam = document.getString("Name")
                println("userName est : $usernam")// Remplacez "username" par le nom du champ contenant le nom d'utilisateur dans votre document Firestore
                if (usernam != null) {
                    val usernameTextView =
                        headerView.findViewById<TextView>(R.id.username) // Remplacez "usernameTextView" par l'ID de votre TextView
                    usernameTextView.text = usernam
                    usernameTextView.setText(usernam)
                }
            }
        }


        val authProfile = FirebaseAuth.getInstance()
        val firebaseUser = authProfile.currentUser

        val userEmail = firebaseUser?.getEmail()

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
                R.id.home, R.id.sitting, R.id.aide, R.id.recommencer, R.id.signout,R.id.enreg -> {
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
                R.id.enreg -> {
                    val intent = Intent(this, MainRegister::class.java)
                    startActivity(intent)
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
        var resultNut = nutrientCount - nutrientCountDelete
        println("nbre des nutriment est : $resultNut")
        println("nbre des ingredient est : $resultIng")

        println("/*/* new table name :" + nameArray)
        println("/*/* new table obj :" + objArray)
        println("/*/* new table opt :" + optArray)
        println("/*/* new table name ing :" + nameIngArray)
        println("/*/* new table price ing :" + prixIngArray)
        val nameTable = nameIngArray?.toTypedArray()

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





        createIngredientRows(resultIng, resultNut, nameTable)

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {

            transposedMatrix = transposeMatrix(nutrientMatrix)

            nutrientMatrixx = createNutrientMatrix(
                resultIng,
                resultNut,
                prixDoubleArray?.toList() as ArrayList<Any>,
                objDoubleArray.toList() as ArrayList<Any>,
                transposedMatrix
            )

            for (i in nutrientMatrixx.indices) {
                for (j in nutrientMatrixx[i].indices) {
                    Log.d("Nutrient Matrix", "Ingredient $i, Nutrient $j: ${nutrientMatrixx[i][j]}")
                }
            }

            nutrientMatrixx = createNutrientMatrix(
                resultIng, resultNut,
                prixIngArray as ArrayList<Double>, objArray as ArrayList<Double>, transposedMatrix
            )
            for (i in nutrientMatrixx.indices) {
                for (j in nutrientMatrixx[i].indices) {
                    Log.d(
                        "Nutrient MatrixResultat",
                        "Ingredient $i, Nutrient $j: ${nutrientMatrixx[i][j]}"
                    )
                }
            }

            percentOrNot = 0

            val intent = Intent(this, simplexe::class.java).apply {
                putExtra("optArray", optArray)
                putExtra("nameArray", nameArray)
                putExtra("nameIngArray", nameIngArray)
                putExtra("resultNut", resultNut)
                putExtra("resultIng", resultIng)
                putExtra("percentOrNot", percentOrNot)
                putExtra("nutrientMatrix", nutrientMatrixx)
            }
            startActivity(intent)
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        val submitButtonPercent = findViewById<Button>(R.id.submitButtonPercent)
        submitButtonPercent.setOnClickListener {

            transposedMatrix = transposeMatrix(nutrientMatrix)


            val row1 =
                Array(transposedMatrix[0].size) { 100.0 } // Crée une ligne avec des valeurs 100
            val row2 =
                Array(transposedMatrix[0].size) { 100.0 } // Crée une autre ligne avec des valeurs 100
            transposedMatrix += arrayOf(row1, row2) // Ajoute les deux lignes à nutrientMatrixx

            for (i in transposedMatrix.indices) {
                for (j in transposedMatrix[i].indices) {
                    Log.d(
                        "transposedMatrix",
                        "Ingredient $i, Nutrient $j: ${transposedMatrix[i][j]}"
                    )
                }
            }

            val objArray = getObjArray()
            objArray.add("100.0")
            objArray.add("100.0")


            val objDoubleArray = objArray.map {
                try {
                    it.toDouble()
                } catch (e: NumberFormatException) {
                    0.0
                }
            }.toDoubleArray()

            resultNutTmp = resultNut + 2
            resultNut = resultNutTmp


            nutrientMatrixx = createNutrientMatrix(
                resultIng,
                resultNut,
                prixDoubleArray?.toList() as ArrayList<Any>,
                objDoubleArray.toList() as ArrayList<Any>,
                transposedMatrix
            )


            nutrientMatrixx = createNutrientMatrix(
                resultIng,
                resultNut,
                prixIngArray as ArrayList<Double>,
                objArray as ArrayList<Double>,
                transposedMatrix
            )


            for (i in nutrientMatrixx.indices) {
                for (j in nutrientMatrixx[i].indices) {
                    Log.d(
                        "Nutrient MatrixResultat",
                        "Ingredient $i, Nutrient $j: ${nutrientMatrixx[i][j]}"
                    )
                }
            }

            val optArray = getOptArray()
            optArray.add("min")
            optArray.add("max")

            val nameArray = getNameArray()
            nameArray.add("MIN%")
            nameArray.add("MAX%")

            percentOrNot = 1

            val intent = Intent(this, simplexe::class.java).apply {
                putExtra("optArray", optArray)
                putExtra("nameArray", nameArray)
                putExtra("nameIngArray", nameIngArray)
                putExtra("resultNut", resultNut)
                putExtra("resultIng", resultIng)
                putExtra("percentOrNot", percentOrNot)
                putExtra("nutrientMatrix", nutrientMatrixx)
                putExtra("prixIngArray", prixIngArray)
            }
            startActivity(intent)
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    }

        private fun createIngredientRows(
            numIngredients: Int,
            numNutrients: Int,
            nameTable: Array<String>?
        ) {
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
                    // hint = "Ingredient $i"
                    //  hint = "nameTable["
                    text = nameTable?.get(i - 1)
                    setTextColor(Color.WHITE)
                    //  setHintTextColor(Color.WHITE)
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
                        setHintTextColor(Color.WHITE)
                        setPadding(16, 16, 16, 16)
                        inputType =
                            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                        setTextColor(Color.WHITE) // Set text color to white


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
                                nutrientMatrix[i - 1][j - 1] =
                                    0.0 // Ou une valeur par défaut appropriée
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

        private fun createNutrientMatrix(
            numIngredients: Int,
            numNutrients: Int
        ): Array<Array<String>> {
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
            val nutrientMatrix = Array(numNutrients + 1) { Array(numIngredients + 1) { 0.0 } }
            nutrientMatrix[numNutrients][numIngredients] = 0.0

            for (i in 0 until numNutrients) {
                for (j in 0 until numIngredients) {
                    nutrientMatrix[i][j] = transposedMatrix[i][j]
                }
                val convertedValue = objArray[i].toString().toDoubleOrNull() ?: 0.0
                nutrientMatrix[i][numIngredients] = convertedValue
                // nutrientMatrix[i][numNutrients]=objArray[i]

            }

            for (j in 0 until numIngredients) {
                val convertedValuee = prixIngArray[j].toString().toDoubleOrNull() ?: 0.0
                nutrientMatrix[numNutrients][j] = convertedValuee
                // nutrientMatrix[numIngredients][j] = prixIngArray[j]
            }

            return nutrientMatrix
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

        private fun getOptArray(): ArrayList<String> {
            val optArray: ArrayList<String> =
                intent.getStringArrayListExtra("optArray") ?: ArrayList()
            return optArray
        }

        private fun getNameArray(): ArrayList<String> {
            val nameArray: ArrayList<String> =
                intent.getStringArrayListExtra("nameArray") ?: ArrayList()
            return nameArray
        }

        private fun getObjArray(): ArrayList<String> {
            val objArray: ArrayList<String> =
                intent.getStringArrayListExtra("objArray") ?: ArrayList()
            return objArray
        }

    }

