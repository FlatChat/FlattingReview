package adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flatchat.app.R
import kotlinx.android.synthetic.main.layout_rev_fixed_width.view.*
import models.Review
import java.util.*

/**
 * This class receives a list of Review objects and displays them in the recycler view.
 *
 * @property exampleList list of Review objects to display
 */
class FeaturedReviewsAdapter(private val exampleList: ArrayList<Review>) :
    RecyclerView.Adapter<FeaturedReviewsAdapter.FeatRevViewHolder>() {

    /**
     * On create the layout for each Review object is created, it gets the layout from the
     * 'featured_rev_layout' file.
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatRevViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_rev_fixed_width,
            parent, false
        )
        return FeatRevViewHolder(itemView)
    }

    /**
     * This binds the data from the database to the textView's in the holder. It will only
     * display the review if the review has a comment attached.
     *
     * @param holder The holder that holds a single Review object to display
     * @param position The position of the Object in the list
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatRevViewHolder, position: Int) {
        val currentItem = exampleList[position]
        if(!currentItem.anonymous){
            holder.textView1.text = currentItem.name
        } else {
            holder.textView1.text = "Anonymous"
        }
        holder.textView2.text = currentItem.comment
        holder.textView3.text = currentItem.date
    }

    /**
     * Returns the size of the list of Reviews as an int.
     *
     */
    override fun getItemCount() = exampleList.size

    /**
     * Sets the text in the layout cardView.
     *
     * @param itemView The itemView is as single Flat object in its layout cardView.
     */
    class FeatRevViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.featured_name
        val textView2: TextView = itemView.featured_comment
        var textView3: TextView = itemView.featured_date
    }

}