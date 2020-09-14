package adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.flattingreview.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.review_layout.view.*
import models.Review
import java.lang.Exception
import java.util.*

/**
 * This class receives a list of Review objects and displays them in the recycler view a.
 *
 * @property exampleList list of Review objects to display
 */
class ReviewAdapter(
    private val context: Context,
    private val exampleList: ArrayList<Review>
) :
    RecyclerView.Adapter<ReviewAdapter.ExampleViewHolder>() {

    private lateinit var reviewReference: DatabaseReference
    private val _result = MutableLiveData<Exception?>()
    
    /**
     * Clears the current list so that duplicate reviews
     * are not printed.
     */
    fun clear() {
        exampleList.removeAll(exampleList)
    }


    /**
     * On create the layout for each Review object is created, it gets the layout from the
     * 'popular_flats' file.
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.review_layout,
            parent, false
        )
        return ExampleViewHolder(itemView)
    }

    /**
     * This binds the data from the database to the textView's in the holder. It will only
     * display the review if the review has a comment attached.
     *
     * @param holder The holder that holds a single Review object to display
     * @param position The position of the Object in the list
     */
    @SuppressLint("SetTextI18n", "RestrictedApi")
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]
        if(!currentItem.anonymous){
            holder.textView1.text = currentItem.name
        } else holder.textView1.text = "Anonymous"
        holder.textView2.text = currentItem.comment
        holder.textView3.text = currentItem.date

        reviewReference = FirebaseDatabase.getInstance().getReference("reviews")


        holder.buttonViewOption.setOnClickListener {
            val popup = PopupMenu(context, holder.buttonViewOption)
            popup.inflate(R.menu.review_menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.report ->

                        true
                    R.id.delete_review -> {
                        AlertDialog.Builder(context).also {
                            it.setTitle(context.getString(R.string.delete_confirmation))
                            it.setPositiveButton(context.getString(R.string.yes)) { dialog, which ->
                                deleteReview(position)
                                clear()
                                Toast.makeText(context,"Review Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }.create().show()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

    }

    /**
     * Returns the size of the list of Reviews as an int.
     *
     */
    override fun getItemCount() = exampleList.size


    /**
     * Deletes a given review from the database.
     *
     * @param position the position of the review to be deleted.
     */
    fun deleteReview(position: Int) {
        reviewReference.child(exampleList[position].reviewID!!).setValue(null)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
        notifyDataSetChanged()
    }

    /**
     * Sets the text in the layout cardView.
     *
     * @param itemView The itemView is a single Flat object in its layout cardView.
     */
    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.name
        val textView2: TextView = itemView.comment
        var textView3: TextView = itemView.date
        var buttonViewOption: TextView = itemView.textViewOptions
    }

    interface OnItemClickListener {

    }

}