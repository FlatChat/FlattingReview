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

class ReviewAdaptor : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var reviews: List<Review> = ArrayList()

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ReviewViewHolder -> {
                holder.bind(reviews[position])
            }
        }
    }

    fun submitList(reviewList: List<Review>){
        reviews = reviewList
    }

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