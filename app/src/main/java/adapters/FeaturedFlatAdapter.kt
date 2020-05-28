package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flattingreview.R
import domain.NewFlat
import kotlinx.android.synthetic.main.flat_icon_layout.view.*
import java.util.*

class FeaturedFlatAdapter(private val exampleList: ArrayList<NewFlat>) : RecyclerView.Adapter<FeaturedFlatAdapter.FeaturedFlatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedFlatViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.flat_icon_layout,
            parent, false)
        return FeaturedFlatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeaturedFlatViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.textView1.text = currentItem.address!!.split(",")[0]
    }

    override fun getItemCount() = exampleList.size

    class FeaturedFlatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView1: TextView = itemView.flat_icon_address

    }

}