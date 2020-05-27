package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flattingreview.R
import domain.Review
import kotlinx.android.synthetic.main.review_layout.view.*
import java.util.*

class ReviewAdapter(private val exampleList: ArrayList<Review>) : RecyclerView.Adapter<ReviewAdapter.ExampleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.review_layout,
        parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.textView1.text = currentItem.name
        holder.textView2.text = currentItem.comment
        holder.textView3.text = currentItem.date
    }

    override fun getItemCount() = exampleList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView1: TextView = itemView.name
        val textView2: TextView = itemView.comment
        var textView3: TextView = itemView.date
    }

}