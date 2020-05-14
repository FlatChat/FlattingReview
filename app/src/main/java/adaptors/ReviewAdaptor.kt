package adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flattingreview.R
import domain.Review
import kotlinx.android.synthetic.main.layout_review.view.*
import java.lang.String.format

/**
 * Adapter for the recycling view to the reviews. The class will read out the data from the
 * database in realtime and input the data into the recycling view.
 * @author ryan
 */
class ReviewAdaptor : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var reviews: List<Review> = ArrayList()

    /**
     * Collects the layout format from layout_review.xml
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReviewViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_review,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    /**
     * Checks to see what type of review view holder should be user (what card format).
     * There is only one format so the when loop should only run once. It also calls the
     * bind function which binds the data.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ReviewViewHolder -> {
                holder.bind(reviews[position])
            }
        }
    }

    /**
     * This function is called to input the data into this class.
     */
    fun submitList(reviewList: List<Review>){
        reviews = reviewList
    }

    /**
     * declares the individual elements and and binds them to the holder.
     */
    class ReviewViewHolder constructor(
        reviewView: View
    ): RecyclerView.ViewHolder(reviewView){

    private val reviewUser: TextView = reviewView.user
    private val reviewComment: TextView = reviewView.comment
    private val reviewDate: TextView = reviewView.date
    private val report: String = "report"

    fun bind(Review: Review){
        reviewUser.setText(Review.userID)
        reviewComment.text = Review.comment
        reviewDate.text = format(Review.date.toString())
        report
    }

    }
}