package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flatchat.app.HomeScreen
import com.flatchat.app.R
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
    private val ratingList: HashMap<String, Double>,
    private var clickListener: HomeScreen,
    private var layout: String
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
            if(layout == "flat_layout"){
                R.layout.flat_layout
            } else {
                R.layout.flat_layout_fill_width
            },
            parent, false
        )
        return FeaturedFlatViewHolder(itemView)
    }

    /**
     * Clears the current list so that duplicate reviews
     * are not printed.
     */
    private fun clear() {
        exampleList.removeAll(exampleList)
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
        holder.textView2.text  = context.getString(R.string.one_decimal).format(ratingList[currentItem.flatID])
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

        /**
         * A method containing a click listener for users to view a featured flat.
         *
         * @param item the item being selected
         * @param action the action to take place after the button has been pressed.
         */
        fun initialize(item: Flat, action: HomeScreen) {
            itemView.setOnClickListener {
                action.onItemClick(item)
            }
        }
    }
}




