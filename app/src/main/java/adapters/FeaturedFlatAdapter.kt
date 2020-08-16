package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flattingreview.HomeScreen
import com.example.flattingreview.R
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.flat_layout.view.*
import models.Flat

/**
 * This class receives a list of Flat objects and displays them in the recycler view.
 *
 * @property exampleList list of Flat objects to display
 */
class FeaturedFlatAdapter(
    private val context: Context,
    private val exampleList: ArrayList<Flat>,
    private val ratingList: HashMap<String, ArrayList<Double>>,
    private var clickListener: HomeScreen
) : RecyclerView.Adapter<FeaturedFlatAdapter.FeaturedFlatViewHolder>() {

    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

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
        val gsReference =
            storage.getReferenceFromUrl("gs://flattingreview.appspot.com/flats/image${currentItem.flatID}.jpg")
        Glide.with(context).load(gsReference).into(holder.imageView1)
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
        val imageView1: ImageView = itemView.flat_image
        fun initialize(item: Flat, action: HomeScreen) {
            itemView.setOnClickListener {
                action.onItemClick(item)
            }
        }
    }
}




