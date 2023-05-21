package com.example.nutfit

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class UserAdapterNut(val c: Context, val userList: ArrayList<UserDataNut>): RecyclerView.Adapter<UserAdapterNut.UserViewHolder>() {
     var nutrientCountDelete = 0
    inner class UserViewHolder(val v: View):RecyclerView.ViewHolder(v){
        var namenut: TextView
        var nobjectif: TextView
        var nsymbole: TextView


        var mMenus: ImageView
        init {
            namenut = v.findViewById<TextView>(R.id.mNut)
            nobjectif = v.findViewById<TextView>(R.id.objNut)
            nsymbole = v.findViewById<TextView>(R.id.symNut)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener{popupMenus(it)}
        }

        @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
        private fun popupMenus(v: View){
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu_nut)
            popupMenus.setOnMenuItemClickListener{
                when(it.itemId){
                    R.id.editText->{
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item_nut,null)
                        val namenut = v.findViewById<EditText>(R.id.nutName)
                        val nobjectif = v.findViewById<EditText>(R.id.nutObj)
                        val nsymbole = v.findViewById<EditText>(R.id.nutSym)
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                position.nutName = namenut.text.toString()
                                position.nutObj = nobjectif.text.toString()
                                position.nutSym = nsymbole.text.toString()
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
                                nutrientCountDelete++
                                userList.removeAt(adapterPosition)
                                println("indice supp $adapterPosition")
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
        val v = inflater.inflate(R.layout.list_item_nut,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder:UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.namenut.text = newList.nutName
        holder.nobjectif.text = newList.nutObj
        holder.nsymbole.text = newList.nutSym
    }

    override fun getItemCount(): Int {
        return userList.size


    }

}