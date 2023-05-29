package com.example.nutfit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
class UserAdapterIng (val c: Context, val userList: ArrayList<UserDataIng>): RecyclerView.Adapter<UserAdapterIng.UserViewHolder>(){
    var ingredientCountDelete = 0
    inner class UserViewHolder(val v: View):RecyclerView.ViewHolder(v){
        var nameing: TextView
        var prxing: TextView

        /***/
        var mMenus: ImageView
        init {
            nameing = v.findViewById<TextView>(R.id.mIng)
            prxing = v.findViewById<TextView>(R.id.prixIng)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener{popupMenus(it)}
        }

        @SuppressLint("MissingInflatedId")
        private fun popupMenus(v: View){
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu_ing)
            popupMenus.setOnMenuItemClickListener{
                when(it.itemId){
                    R.id.editText->{
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item_ing,null)
                        val nameing = v.findViewById<EditText>(R.id.ingName)
                        val prxing = v.findViewById<EditText>(R.id.ingPrix)
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                position.ingName = nameing.text.toString()
                                position.ingPrix = prxing.text.toString().toDouble()
                                notifyDataSetChanged()
                                Toast.makeText(c,"User Information is Edited", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete->{
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sue delete this Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                ingredientCountDelete++
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted this Information", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder:UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.nameing.text = newList.ingName
        holder.prxing.text = newList.ingPrix.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}