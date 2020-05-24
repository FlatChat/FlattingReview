package com.example.flattingreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import domain.Review
import kotlinx.android.synthetic.main.review_layout.view.*

class ReviewAdapter(private val exampleList: ArrayList<Review>) : RecyclerView.Adapter<ReviewAdapter.ExampleViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.review_layout,
        parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.textView1.text = currentItem.userID
        holder.textView2.text = currentItem.comment
    }

    override fun getItemCount() = exampleList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView1: TextView = itemView.text_view_1
        val textView2: TextView = itemView.text_view_2
    }

}