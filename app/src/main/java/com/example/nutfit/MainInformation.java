package com.example.nutfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainInformation extends AppCompatActivity {

    RecyclerView recycler_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_information);

        Data_Info[] my_data = new Data_Info[]{
                new Data_Info("Diabetes", "*Limit the consumption of simple carbohydrates such as sweets and sugary drinks.\n" +
                        "*Choose foods with a low glycemic index, such as non-starchy vegetables, whole grains, and legumes.\n" +
                        "*Control portions and avoid excesses.\n" +
                        "*Include sources of lean protein in your meals to help stabilize blood sugar levels.\n" +
                        "*Engage in regular exercise to improve insulin sensitivity.", R.drawable.diabetes),

                new Data_Info("Hypertension", "*Reduce sodium intake by avoiding processed foods and cooking with fresh ingredients.\n" +
                        "*Opt for a diet rich in fruits, vegetables, and low-fat dairy products to increase potassium intake.\n" +
                        "*Limit alcohol consumption.\n" +
                        "*Engage in regular exercise to maintain a healthy blood pressure.\n" +
                        "*Manage your weight by adopting a balanced diet and engaging in regular physical activity.", R.drawable.hypertension),

                new Data_Info("High Cholesterol", "*Limit foods high in saturated fats and cholesterol, such as fatty meats, high-fat dairy products, and fried foods.\n" +
                        "*Choose healthy fats such as monounsaturated and polyunsaturated fats found in avocados, nuts, seeds, and fatty fish.\n" +
                        "*Increase consumption of soluble fibers found in fruits, vegetables, and legumes.\n" +
                        "*Engage in regular exercise to raise good cholesterol (HDL) levels and lower bad cholesterol (LDL) levels.", R.drawable.cholesterol),

                new Data_Info("Cardiovascular Diseases", "*Adopt a diet rich in vegetables, fruits, whole grains, fatty fish, nuts, and seeds.\n" +
                        "*Reduce consumption of processed foods high in saturated fats and sodium.\n" +
                        "*Control your blood pressure by limiting salt intake and engaging in regular physical activity.\n" +
                        "*Maintain a healthy weight by choosing appropriate portion sizes and avoiding excessive intake.\n" +
                        "*Avoid smoking and limit alcohol consumption.", R.drawable.cardiovascular),

                new Data_Info("Food Allergies", "*Familiarize yourself with specific allergens and learn how to read food labels.\n" +
                        "*Avoid foods that contain allergens you are sensitive to.\n" +
                        "*Seek out safe alternatives and substitutes for allergenic foods.\n" +
                        "*Inform others about your food allergy to prevent cross-contamination risks.\n" +
                        "*Consult an allergist for allergy testing and an appropriate management plan.", R.drawable.food_allergies),

                new Data_Info("Celiac Disease", "*Strictly avoid gluten by eliminating wheat, barley, rye, and foods containing these grains from your diet.\n" +
                        "*Look for gluten-free alternatives such as gluten-free grains (rice, quinoa, buckwheat), vegetables, fruits, and unprocessed dairy products.\n" +
                        "*Read food labels carefully and be cautious of cross-contamination.\n" +
                        "*Inform restaurants and individuals about your gluten intolerance to prevent adverse reactions.", R.drawable.celiac),

                new Data_Info("Irritable Bowel Syndrome (IBS)", "*Identify food triggers by keeping a food journal and noting which foods worsen your symptoms.\n" +
                        "*Avoid foods that can cause bloating, abdominal pain, or digestive issues, such as spicy foods, fried foods, dairy products, and high-FODMAP foods.\n" +
                        "*Opt for a diet rich in soluble fiber from fruits, vegetables, and whole grains.\n" +
                        "*Consider consulting a healthcare professional to discuss other management options, such as probiotics or lifestyle modifications.", R.drawable.irritable_bowel_syndrome),

                new Data_Info("Kidney Disease", "*Limit sodium intake to help control blood pressure and reduce water retention.\n" +
                        "*Monitor your intake of potassium, phosphorus, and protein according to the recommendations from your healthcare professional.\n" +
                        "*Ensure adequate fluid intake based on your doctor's advice to maintain proper hydration.\n" +
                        "*Avoid foods high in oxalate if you are prone to kidney stones.\n" +
                        "*Regularly consult with your doctor for blood tests and kidney function evaluations.", R.drawable.kidney_disease),

                new Data_Info("Osteoporosis", "*Ensure an adequate intake of calcium by consuming low-fat dairy products, leafy green vegetables, and calcium-fortified foods.\n" +
                        "*Increase your vitamin D intake by spending time outdoors or taking supplements as recommended by your doctor.\n" +
                        "*Include foods rich in vitamin K, such as spinach, broccoli, and kale.\n" +
                        "*Engage in regular exercise, especially weight-bearing and moderate impact activities, to strengthen your bones.\n" +
                        "*Avoid smoking and limit alcohol consumption as they can weaken bones.", R.drawable.osteoporosis)
        };
        recycler_info=findViewById(R.id.recycler_info);
        Data_Adapter_Info myadapterinfo=new Data_Adapter_Info(my_data);

        recycler_info.setLayoutManager(new LinearLayoutManager(this));
        recycler_info.setAdapter(myadapterinfo);
    }
}