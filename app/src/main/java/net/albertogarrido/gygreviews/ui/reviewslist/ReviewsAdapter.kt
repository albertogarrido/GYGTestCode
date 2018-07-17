package net.albertogarrido.gygreviews.ui.reviewslist

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.review_item.view.*
import net.albertogarrido.gygreviews.R
import net.albertogarrido.gygreviews.repository.Review
import net.albertogarrido.gygreviews.util.gone

class ReviewsAdapter : ListAdapter<Review, ReviewViewHolder>(diffUtilCallback) {

    companion object {
        private val diffUtilCallback = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ReviewViewHolder(inflater.inflate(R.layout.review_item, parent, false))
    }
}

class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleView = itemView.title
    private val ratingView = itemView.rating
    private val dateView = itemView.date
    private val messageView = itemView.message
    private val authorView = itemView.author

    fun bind(review: Review?) {
        review?.apply {
            titleView.text = title
            dateView.text = date
            if (message.isEmpty()) {
                messageView.gone()
            } else {
                messageView.text = message
            }
            authorView.text = author
            val stars = rating.toFloatOrNull()
            if (stars != null) {
                ratingView.rating = stars
            } else {
                ratingView.gone()
            }
        }
    }
}