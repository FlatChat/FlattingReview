package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flattingreview.R
import domain.Review
import kotlinx.android.synthetic.main.featured_rev_layout.view.*
import java.util.*

class FeatReviewAdapter(private val exampleList: ArrayList<Review>) : RecyclerView.Adapter<FeatReviewAdapter.FeatRevViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatRevViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.featured_rev_layout,
            parent, false)
        return FeatRevViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeatRevViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.textView1.text = currentItem.userID
        holder.textView2.text = currentItem.comment
        holder.textView3.text = currentItem.date
    }

    override fun getItemCount() = exampleList.size

    class FeatRevViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView1: TextView = itemView.featured_name
        val textView2: TextView = itemView.featured_comment
        var textView3: TextView = itemView.featured_date
    }

}