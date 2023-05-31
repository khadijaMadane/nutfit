package com.example.nutfit;

import androidx.appcompat.app.AppCompatActivity;
import  com.example.nutfit.nutValues;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class simplexe extends AppCompatActivity {
    int i,j,cpt1=0,l,c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplexe);
        Intent intent = getIntent();
        int resultIng = intent.getIntExtra("resultIng", 0);
        int resultNut = intent.getIntExtra("resultNut", 0);
        Double[][] nutrientMatrix = (Double[][]) intent.getSerializableExtra("nutrientMatrix");
        //ArrayList<String> optArray = getIntent().getStringArrayListExtra("optArray");
        String[] optArray = getIntent().getStringArrayListExtra("optArray").toArray(new String[0]);
        String[] nameIngArray = getIntent().getStringArrayListExtra("nameIngArray").toArray(new String[0]);
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
        phase2 f2 = new phase2(table5, new_l2, new_c2, resultNut, resultIng, basi,optArray);
        f2.solve();
        basi = f2.basis();
        table5 = f2.dl();
/*****************************************************************************************/
        int cpt_opt=0;
        for(i=0;i<resultNut;i++)
            //System.out.println(optArray[i]+"     ");
            if(optArray[i].compareTo("egal") == 0)
                cpt_opt++;
        //fin////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println("le prix est= " + table5[new_l2 - 1][new_c2 - 1]);
        if(cpt_opt == 0) {
            for (int i = 0; i < resultNut; i++) {
                if (basi[i] < resultIng)
                    System.out.println("x_" + basi[i] + " = " + table5[i][resultNut + resultIng]);
            }
        }else if(cpt_opt != 0){
            for (int i = 0; i < resultNut; i++) {
                System.out.println("x_" + basi[i] + " = " + table5[i][resultNut + resultIng-cpt_opt]);
            }
        }
        System.out.println();


        TextView outputTextView = findViewById(R.id.outputTextView);
        outputTextView.setText("value = " + table5[new_l2 - 1][new_c2 - 1] + "\n");

        if(cpt_opt==0){
            for(i=0;i<resultNut;i++){
                if (basi[i] < resultIng) {
                    String message = "la quantité de " + nameIngArray[basi[i]] + " = " + table5[i][resultNut + resultIng] + "\n";
                    outputTextView.append(message);
                }
            }
        }else if(cpt_opt !=0){
            for(i=0;i<resultNut;i++){
                if (basi[i] < resultIng) {
                    String messagee = "la quantité de " + nameIngArray[basi[i]] + " = " + table5[i][resultNut + resultIng-cpt_opt] + "\n";
                    outputTextView.append(messagee);
                }
            }
        }

        outputTextView.append("\n");


    }
}



