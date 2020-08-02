package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flattingreview.R
import com.example.flattingreview.Search
import models.Flat
import kotlinx.android.synthetic.main.flat_search_layout.view.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * This class receives a list of Flat objects and displays them in the recycler view.
 *
 * @property exampleList list of Flat objects to display
 */
class SearchAdapter(
    private val exampleList: ArrayList<Flat>,
    private val ratingList: HashMap<String, ArrayList<Double>>,
    var clickListener: Search
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    /**
     * On create the layout for each flat object is created, it gets the layout from the
     * 'flat_layout' file.
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.flat_search_layout,
            parent, false
        )
        return SearchViewHolder(itemView)
    }

    /**
     * This binds the data from the database to the textView's in the holder.
     *
     * @param holder The holder that holds a single Flat object to display
     * @param position The position of the Object in the list
     */
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
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
    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.search_flat_address
        val textView2: TextView = itemView.search_flat_rating
        fun initialize(item: Flat, action: Search) {
            itemView.setOnClickListener() {
                action.onItemClick(item, adapterPosition)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(item: Flat, position: Int)
    }

}
