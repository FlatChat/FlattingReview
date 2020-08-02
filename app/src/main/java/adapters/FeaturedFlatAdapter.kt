package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flattingreview.R
import models.Flat
import kotlinx.android.synthetic.main.flat_layout.view.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * This class receives a list of Flat objects and displays them in the recycler view.
 *
 * @property exampleList list of Flat objects to display
 */
class FeaturedFlatAdapter(
    private val exampleList: ArrayList<Flat>,
    private val ratingList: HashMap<String, ArrayList<Double>>,
    private var clickListener: OnItemClickListener
) : RecyclerView.Adapter<FeaturedFlatAdapter.FeaturedFlatViewHolder>() {

    /**
     * On create the layout for each flat object is created, it gets the layout from the
     * 'flat_layout' file.
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedFlatViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.flat_layout,
            parent, false
        )
        return FeaturedFlatViewHolder(itemView)
    }

    /**
     * This binds the data from the database to the textView's in the holder.
     *
     * @param holder The holder that holds a single Flat object to display
     * @param position The position of the Object in the list
     */
    override fun onBindViewHolder(holder: FeaturedFlatViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.textView1.text = currentItem.address!!.split(",")[0]
        val array  = ratingList[currentItem.flatID]
        var sum = 0.0
        if(!array.isNullOrEmpty()){
            for(item in array) sum += item
        }
        if (array != null) {
            holder.textView2.text = (sum / array.size).toString()
        } else {
            holder.textView2.text = "0"
        }
        holder.initialize(currentItem, clickListener)
    }

    /**
     * Returns the size of the list of flats as an int.
     *
     */
    override fun getItemCount() = exampleList.size

    /**
     * Sets the text in the layout cardView.
     *
     * @param itemView The itemView is as single Flat object in its layout cardView.
     */
    class FeaturedFlatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.flat_icon_address
        val textView2: TextView = itemView.flat_icon_rating
        fun initialize(item: Flat, action: OnItemClickListener) {
            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(item: Flat, position: Int)
    }

}
