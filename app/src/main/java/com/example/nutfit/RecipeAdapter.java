package com.example.nutfit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private List<String> recipeNames;
    private int selectedPosition = -1; // Variable to store the selected position

    public RecipeAdapter(Context context, List<String> recipeNames) {
        this.context = context;
        this.recipeNames = recipeNames;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        String recipeName = recipeNames.get(position);
        holder.txtRecipeName.setText(recipeName);
/*
        // Highlight the selected item
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_item_color));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }*/

        // Handle item click events
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    // Add your click event handling code here
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeNames.size();
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView txtRecipeName;
        ImageView saveMenus;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRecipeName = itemView.findViewById(R.id.txtRecipeName);
            saveMenus = itemView.findViewById(R.id.saveMenus);

            saveMenus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        // Handle click event here
                        popupMenus(v, adapterPosition);
                    }
                }
            });
        }

        private void popupMenus(View v, final int adapterPosition) {
            PopupMenu popupMenus = new PopupMenu(itemView.getContext(), v);
            popupMenus.inflate(R.menu.save_menu);

            popupMenus.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.afficher:
                            // Handle edit action
                         /*  Intent intent = new Intent(itemView.getContext(), afficheRepas.class);
                            itemView.getContext().startActivity(intent);*/
                            String recipeName = recipeNames.get(adapterPosition);
                            retrieveAndNavigateToTargetActivity(recipeName);
                            return true;
                        case R.id.delete:
                            // Handle delete action
                            deleteRecipe(adapterPosition);
                            return true;
                        default:
                            return false;
                    }
                }
            });

            // Show the popup menu
            popupMenus.show();
        }

        private void deleteRecipe(final int adapterPosition) {
            new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Delete")
                    .setIcon(R.drawable.ic_warning)
                    .setMessage("Are you sure you want to delete this Information?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                String recipeName = recipeNames.get(adapterPosition);
                                recipeNames.remove(adapterPosition);
                                notifyItemRemoved(adapterPosition);
                                Toast.makeText(itemView.getContext(), "Deleted this Information", Toast.LENGTH_SHORT).show();
                                deleteRecipeFromFirestore(recipeName);
                            }
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }

        private void deleteRecipeFromFirestore(String recipeName) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            if (user != null) {
                String uid = user.getUid();
                if (uid != null) {
                    db.collection("users").document(uid).collection("recipes")
                            .whereEqualTo("name", recipeName)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot querySnapshot) {
                                    for (QueryDocumentSnapshot document : querySnapshot) {
                                        document.getReference().delete();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error deleting recipe from Firestore
                                    Log.e("Firestore", "Error deleting recipe: " + e.getMessage());
                                }
                            });
                }
            }
        }


      private void retrieveAndNavigateToTargetActivity(String recipeName) {
          FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
          FirebaseFirestore db = FirebaseFirestore.getInstance();

          if (user != null) {
              String uid = user.getUid();
              if (uid != null) {
                  db.collection("users").document(uid).collection("recipes")
                          .whereEqualTo("name", recipeName)
                          .get()
                          .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                              @Override
                              public void onSuccess(QuerySnapshot querySnapshot) {
                                  for (QueryDocumentSnapshot document : querySnapshot) {
                                      if (document.exists()) {
                                          Map<String, Object> recipeData = document.getData();
                                          if (recipeData != null && recipeData.containsKey("matrix")) {
                                              List<List<Object>> matrixList = new ArrayList<>();

                                              // Retrieve the matrix as a list of lists
                                              List<Map<String, Object>> matrixMapList = (List<Map<String, Object>>) recipeData.get("matrix");
                                              for (Map<String, Object> rowMap : matrixMapList) {
                                                  List<Object> rowList = new ArrayList<>();
                                                  for (Object value : rowMap.values()) {
                                                      rowList.add(value);
                                                  }
                                                  matrixList.add(rowList);
                                              }

                                              Log.d("Firestore", "Retrieved matrixList: " + matrixList);

                                              // Retrieve quantite and priceTotale
                                              List<String> quantiteList = (List<String>) recipeData.get("quantite");
                                              String priceTotale = (String) recipeData.get("priceTotale");

                                              Log.d("Firestore", "QuantiteList: " + quantiteList);
                                              Log.d("Firestore", "PriceTotale: " + priceTotale);

                                              // Pass the matrixList, quantiteList, and priceTotale to the target activity
                                              Gson gson = new Gson();
                                              String matrixListJson = gson.toJson(matrixList);
                                              Log.d("Firestore", "Serialized matrixList: " + matrixListJson);

                                              Intent intent = new Intent(itemView.getContext(), afficheRepas.class);
                                              intent.putExtra("matrixList", matrixListJson);
                                              intent.putExtra("quantiteList", gson.toJson(quantiteList));
                                              intent.putExtra("priceTotale", priceTotale);
                                              itemView.getContext().startActivity(intent);
                                          }
                                      }
                                  }
                              }
                          })
                          .addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  // Error retrieving recipe from Firestore
                                  Log.e("Firestore", "Error retrieving recipe: " + e.getMessage());
                              }
                          });
              }
          }
      }

    }
}