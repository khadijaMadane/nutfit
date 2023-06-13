package com.example.nutfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class simplexe extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;
    int i,j,cpt1=0,l,c,resultIngfinal,resultNutfinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_simplexe);



        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference userRef = db.collection("users").document(userID);

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {
                if (document != null) {
                    String username = document.getString("Name");
                    Log.d("TAG", "Username: " + username);
                    if (username != null) {
                        TextView usernameTextView = headerView.findViewById(R.id.username);
                        usernameTextView.setText(username);
                    }
                }
            }
        });

        FirebaseAuth authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        String userEmail = firebaseUser.getEmail();
        Log.d("TAG", "Email: " + userEmail);

        TextView emailTextView = headerView.findViewById(R.id.email_nav);
        emailTextView.setText(userEmail);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutId);
        NavigationView navView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent homeIntent = new Intent(simplexe.this, changePasswrord.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.sitting:
                        Intent settingsIntent = new Intent(simplexe.this, changeEmail.class);
                        startActivity(settingsIntent);
                        return true;
                    case R.id.aide:
                        Intent helpIntent = new Intent(simplexe.this, deleteAccount.class);
                        startActivity(helpIntent);
                        return true;
                    case R.id.recommencer:
                        Intent restartIntent = new Intent(simplexe.this, IngregientsName.class);
                        startActivity(restartIntent);
                        return true;
                    case R.id.signout:
                        showSignOutConfirmationDialog();
                        return true;
                    default:
                        return false;
                }
            }
        });







        Intent intent = getIntent();
        int resultIng = intent.getIntExtra("resultIng", 0);
        int resultNut = intent.getIntExtra("resultNut", 0);
        Double[][] nutrientMatrix = (Double[][]) intent.getSerializableExtra("nutrientMatrix");
        //ArrayList<String> optArray = getIntent().getStringArrayListExtra("optArray");
        String[] optArray = getIntent().getStringArrayListExtra("optArray").toArray(new String[0]);
        String[] nameIngArray = getIntent().getStringArrayListExtra("nameIngArray").toArray(new String[0]);
        String[] nameArray = getIntent().getStringArrayListExtra("nameArray").toArray(new String[0]);

        for (int i = 0; i < resultNut; i++) {
            System.out.print(optArray[i] + "  ");
        }

        System.out.println();
        for (int i = 0; i < resultIng; i++) {
            System.out.print(nameIngArray[i] + "  ");
        }

        System.out.println();


        for (i = 0; i < resultNut + 1; i++) {
            for (j = 0; j < resultIng + 1; j++) {
                System.out.print(nutrientMatrix[i][j] + "  ");
            }
            System.out.println();
        }

        ///////C BIEN
        l = resultNut + 1;
        c = resultIng + 1;
        ////////////////////////////////////////minimisation & all <=(max) /*=(equal) >=(min)*\////////////////////////////////////////
        //***********************************************
        for (i = 0; i < resultNut; i++)
            if (optArray[i].compareTo("egal") == 0 || optArray[i].compareTo("min") == 0)
                cpt1++;//combien de contrainte de >= =
        int[] index = new int[cpt1];
        int cpt_index = 0, y = 0;
        //***********************************************
        int cpt = 0, l2, c2;
        for (i = 0; i < resultNut; i++) {
            if (optArray[i].compareTo("max") == 0 || optArray[i].compareTo("egal") == 0)
                cpt++;
            else if (optArray[i].compareTo("min") == 0)
                cpt += 2;
        }
        l2 = l + 1;
        c2 = c + cpt;
        //double [][] table2=new double[l][c2];//ces contraintes tous <=
        double[][] table3 = new double[l2][c2];//contient les var art ajoutees
        //z*(-1)
        for (i = 0; i < resultIng; i++)
            table3[l - 1][i] = nutrientMatrix[l - 1][i] * (-1);
        //all operateur max (pas de var artificielle)
        int cpt_max = 0;
        for (i = 0; i < resultNut; i++) {
            if (optArray[i].compareTo("max") == 0)
                cpt_max++;
        }
        if (cpt_max == resultNut) {
            System.out.println("1");
            //********************afficher table2 if all max*************************//
            System.out.println("Z=0");
            System.out.println();
        } else {
            //min max equal (presence des var artificielles)
            System.out.print("2");
            //********************************************************************************************
            //affecter les var d'ecart et artifitielle
            for (i = 0; i < resultNut; i++) {
                if (optArray[i].compareTo("max") == 0)
                    table3[i][resultIng + i + y] = 1;
                else if (optArray[i].compareTo("egal") == 0) {
                    table3[i][resultIng + i + y] = 1;
                    index[cpt_index] = resultIng + i + y;
                    cpt_index++;
                } else if (optArray[i].compareTo("min") == 0) {
                    table3[i][resultIng + i + y] = 1;
                    table3[i][resultIng + i + 1 + y] = -1;
                    index[cpt_index] = resultIng + i + y;
                    cpt_index++;
                    y++;
                }
            }
            //affecter la matrice initiale
            for (i = 0; i < resultNut; i++)
                for (j = 0; j < resultIng; j++)
                    table3[i][j] = nutrientMatrix[i][j];
            //affecter s.m
            for (i = 0; i < resultNut; i++)
                table3[i][c2 - 1] = nutrientMatrix[i][c - 1];
            //la ligne A de la somme(pour var artifitielle)
            ///cpt1 :les contraintes contenant les var artif
            cpt1 = 0;
            System.out.println("TEST" + resultNut);
            System.out.println("TEST" + optArray[0]);
            System.out.println("TEST" + optArray[1]);
            for (i = 0; i < resultNut; i++) {
                if (optArray[i].compareTo("egal") == 0) {
                    cpt1++;
                }
                System.out.println("TEST1:  " + cpt1);
                if (optArray[i].compareTo("min") == 0) {
                    cpt1++;
                }
                System.out.println("TEST2:  " + cpt1);
            }
            System.out.println("TEST  " + cpt1);
            //affecter les elements du nouveau tableau
            double[][] somme = new double[cpt1][c2];
            ///affecter les equations
            int l_tmp = 0, c_tmp;
            for (i = 0; i < resultNut; i++) {
                if (optArray[i].compareTo("egal") == 0 || optArray[i].compareTo("min") == 0) {
                    for (c_tmp = 0; c_tmp < c2; c_tmp++) {
                        somme[l_tmp][c_tmp] = table3[i][c_tmp];
                    }
                    l_tmp++;
                }
            }
            ///affectation de 0 au var de base (artificielle) pour faire la somme a
            cpt_index = 0;
            for (j = 0; j < cpt1; j++) {
                somme[j][index[cpt_index]] = 0;
                cpt_index++;
            }
            ///somme * (-1)
            for (i = 0; i < cpt1; i++)
                for (j = 0; j < c2; j++)
                    somme[i][j] *= (-1);
            ///realiser l'addition
            for (c = 0; c < c2; c++) {
                table3[l2 - 1][c] = 0;
                l_tmp = 0;
                while (l_tmp < cpt1) {
                    table3[l2 - 1][c] += somme[l_tmp][c];
                    l_tmp++;
                }
            }
            ////////////////////////////////somme////////////////////////////////////
            System.out.println();
            for (i = 0; i < cpt1; i++) {
                for (j = 0; j < c2; j++)
                    System.out.printf("%7.4f ", somme[i][j]);
                System.out.println();
            }
            System.out.println(cpt1);
            System.out.println(c2);
            //********************afficher table3 for fisrt table of phase 1*************************//
            System.out.println();
            System.out.println("M = " + resultNut);
            System.out.println("N = " + resultIng);
            for (i = 0; i < l2; i++) {
                for (j = 0; j < c2; j++) {
                    System.out.printf("%7.4f ", table3[i][j]);
                }
                System.out.println();
            }
        }
        ///////C BIEN

        System.out.println("//////////////////////////phase 1////////////////////");
        phase1 f1 = new phase1(table3, l2, c2, resultNut, resultIng);
        int[] basi = f1.basis();
        f1.solve();
        ///////C BIEN
        ////////////////////////////////////////phase 2////////////////////////////////////////
        int new_l2 = l2 - 1;
        int new_c2 = c2 - cpt1;
        //transposee//////////////////////////////////////////////////////////////////////
        int x = 0;
        double[][] transpose = new double[c2][l2];
        double[][] table4 = new double[new_c2][new_l2];
        double[][] table_tmp = new double[c2][l2];
        for (i = 0; i < l2; i++)
            transpose[c2 - 1][i] = table3[i][c2 - 1];
        for (i = 0; i < c2; i++)
            transpose[i][l2 - 1] = table3[l2 - 1][i];
        for (i = 0; i < c2 - 1; i++)
            for (j = 0; j < l2 - 1; j++)
                transpose[i][j] = table3[j][i];

        System.out.println("***");
        for (i = 0; i < cpt1; i++)
            System.out.print(index[i] + "   ");
        System.out.println("***");
        for (i = 0; i < c2; i++) {
            for (j = 0; j < l2; j++) {
                System.out.printf("%7.4f ", transpose[i][j]);
            }
            System.out.println();
        }

        System.out.println();
        //supprimer les var artificielles
      /*
      for(i=0;i<c2;i++) {
         if(transpose[i][l2-1]!=1) {
            for(j=0;j<l2;j++) {
               table_tmp[x][j]=transpose[i][j];
            }
            x++;
         }
      }
      */
        int cpt2;
        cpt2 = cpt1 + 1;
        int[] index2 = new int[cpt2];
        for (i = 0; i < cpt1; i++)
            index2[i] = index[i];
        index2[i] = -1;

        y = 0;
        for (i = 0; i < c2; i++) {
            System.out.println("1/  i=" + i + " y=" + y + " index=" + index2[y]);
            if (i == index2[y])
                y++;
            else {
                System.out.println("2/  i=" + i + " y=" + y + " index=" + index2[y]);
                for (j = 0; j < l2; j++)
                    table_tmp[x][j] = transpose[i][j];
                x++;
            }
        }

        for (i = 0; i < new_c2; i++)
            for (j = 0; j < new_l2; j++)
                table4[i][j] = table_tmp[i][j];
        //********************afficher table4 for transpose of last table of phase 1(wiyhout var art)*************************//
        for (i = 0; i < new_c2; i++) {
            for (j = 0; j < new_l2; j++) {
                System.out.printf("%7.4f ", table4[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        //********************afficher table5 for fisrt table of phase 2*************************//
        double[][] table5 = new double[new_l2][new_c2];
        for (i = 0; i < new_l2; i++)
            table5[i][new_c2 - 1] = table4[new_c2 - 1][i];
        for (i = 0; i < new_c2; i++)
            table5[new_l2 - 1][i] = table4[i][new_l2 - 1];
        for (i = 0; i < new_l2 - 1; i++)
            for (j = 0; j < new_c2 - 1; j++)
                table5[i][j] = table4[j][i];

        System.out.println();
        for (i = 0; i < new_l2; i++) {
            for (j = 0; j < new_c2; j++) {
                System.out.printf("%7.4f ", table5[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        ///////////////////////////////////////////////
        System.out.println("//////////////////////////phase 2////////////////////");
        phase2 f2 = new phase2(table5, new_l2, new_c2, resultNut, resultIng, basi);
        f2.solve();
        basi = f2.basis();
        table5 = f2.dl();

        //fin////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println("le prix est= " + table5[new_l2 - 1][new_c2 - 1]);
        for (int i = 0; i < resultNut; i++)
            if (basi[i] < resultIng)
                System.out.println("la quantité de " + nameIngArray[basi[i]] + " = " + table5[i][resultNut + resultIng]);
        System.out.println();


        TextView outputTextView = findViewById(R.id.outputTextView);
        outputTextView.setText("value = " + table5[new_l2 - 1][new_c2 - 1] + "\n");

        for (int i = 0; i < resultNut; i++) {
            if (basi[i] < resultIng) {
                String message = "la quantité de " + nameIngArray[basi[i]] + " = " + table5[i][resultNut + resultIng] + "\n";
                outputTextView.append(message);
            }
        }

        resultIngfinal=resultIng+4;
        resultNutfinal=resultNut+2;
        Object[][] matrix = new Object[resultNutfinal][resultIngfinal];
        System.out.println("long est(ing):"+resultIngfinal+"   largeur est(nut):"+resultNutfinal);

//affecter nom nut
        for(i=1;i<resultNutfinal-1;i++)
            matrix[i][0]=nameArray[i-1];
        matrix[resultNutfinal-1][0]="Prix";
        matrix[0][0]="NutIng";

//affecter nom ing
        for(i=1;i<resultIngfinal-3;i++)
            matrix[0][i]=nameIngArray[i-1];
        matrix[0][resultIngfinal-3]="Objectif";
        matrix[0][resultIngfinal-2]="Operateur";
        matrix[0][resultIngfinal-1]="Result";
//affecter la colonne des operateurs
        for(i=1;i<resultNutfinal-1;i++)
            matrix[i][resultIngfinal-2]=optArray[i-1];
        matrix[resultNutfinal-1][resultIngfinal-2]="_";

//affecter la matrice
        for(i=1;i<resultNutfinal;i++){
            for(j=1;j<resultIngfinal-2;j++){
                matrix[i][j]=nutrientMatrix[i-1][j-1];
            }
        }
//affecter le resultat
        for (i=1;i<resultNutfinal-2;i++)
            matrix[i][resultIngfinal-1]=table5[i-1][resultNut + resultIng];
        matrix[resultNutfinal-1][resultIngfinal-1]=table5[new_l2 - 1][new_c2 - 1];


        for (i=0;i<resultNutfinal;i++){
            for (j=0;j<resultIngfinal;j++){
                System.out.print(matrix[i][j]+"\t");
            }
            System.out.println("\n");
        }
        TextView butSave = findViewById(R.id.buttonSave);

        butSave.setOnClickListener(v -> {
            showSaveDialog(matrix);

        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void performSignOut() {
        // Perform sign out logic here
        // For example, navigate to the login screen or clear user session
        Intent intent = new Intent(this, SignIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showSignOutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle sign out
                        performSignOut();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showSaveDialog(Object[][] matrix) {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(simplexe.this);
        mydialog.setTitle("You want to save Recipe");

        final EditText input = new EditText(simplexe.this);
        mydialog.setView(input);

        mydialog.setPositiveButton("Save", (dialogInterface, i) -> {
            String recipeName = input.getText().toString();
            saveRecipe(recipeName, matrix); // Appeler la méthode pour enregistrer la recette
            Toast.makeText(simplexe.this, "Recipe saved", Toast.LENGTH_LONG).show();
        });

        mydialog.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss(); // Fermer la boîte de dialogue
            Toast.makeText(simplexe.this, "Recipe not saved", Toast.LENGTH_LONG).show();
        });

        mydialog.show();
    }


    private void retrieveAndNavigateToTargetActivity(String uid, String recipeId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(uid).collection("recipes").document(recipeId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Map<String, Object> recipeData = documentSnapshot.getData();
                            if (recipeData != null && recipeData.containsKey("matrix")) {
                                List<Map<String, Object>> matrixList = (List<Map<String, Object>>) recipeData.get("matrix");
                                // Pass the matrixList to the target activity
                                Intent intent = new Intent(simplexe.this, MainRegister.class);
                                intent.putExtra("matrixList", (Serializable) matrixList);
                                startActivity(intent);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error retrieving document
                        Log.e("Firestore", "Error retrieving document: " + e.getMessage());
                    }
                });
    }



    private void saveRecipe(String recipeName, Object[][] matrix) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Check if the recipe name already exists for the user
        if (user != null) {
            String uid = user.getUid();
            if (uid != null) {
                db.collection("users").document(uid).collection("recipes")
                        .whereEqualTo("name", recipeName)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshot) {
                                // Check if a recipe with the same name already exists
                                if (querySnapshot.isEmpty()) {
                                    // The recipe name is unique, proceed to save the recipe
                                    saveNewRecipe(uid, recipeName, db,matrix);
                                } else {
                                    // A recipe with the same name already exists, show an error message
                                    Toast.makeText(simplexe.this, "Recipe with the same name already exists", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error querying recipes collection
                                Log.e("Firestore", "Error querying recipes collection: " + e.getMessage());
                            }
                        });
            }
        }
    }

    private void saveNewRecipe(String uid, String recipeName, FirebaseFirestore db, Object[][] matrix) {


        // Convert the matrix into a list of maps
        List<Map<String, Object>> matrixList = new ArrayList<>();
        for (Object[] row : matrix) {
            Map<String, Object> rowMap = new HashMap<>();
            for (int i = 0; i < row.length; i++) {
                rowMap.put("col" + i, row[i]);
            }
            matrixList.add(rowMap);
        }

        // Create a new recipe document and save it
        Map<String, Object> recipeMap = new HashMap<>();
        recipeMap.put("name", recipeName);
        recipeMap.put("matrix", matrixList);

        db.collection("users").document(uid).collection("recipes")
                .add(recipeMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Recipe successfully saved
                        Log.d("Firestore", "Recipe successfully saved");
                        // Retrieve the saved recipe and navigate to the target activity
                        retrieveAndNavigateToTargetActivity(uid, documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error saving recipe
                        Log.e("Firestore", "Error saving recipe: " + e.getMessage());
                    }
                });
    }



}



