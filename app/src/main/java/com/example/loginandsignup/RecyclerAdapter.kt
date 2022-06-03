package com.example.loginandsignup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter:RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    private var title= arrayOf("User One","User Two","User Three","User Four","User Five","User Six","User Seven","User Eight")
    private var detail= arrayOf("User Detail One","","","","","","","")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
       var itemImage:ImageView
       var itemTitle:TextView
       var itemDetail:TextView
       init{
           itemImage=itemView.findViewById(R.id.item_image)
           itemTitle=itemView.findViewById(R.id.item_title)
           itemDetail=itemView.findViewById(R.id.item_detail)
       }
    }

}