package net.albertogarrido.gygreviews.repository

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
        @SerializedName("status") val status: Boolean,
        @SerializedName("total_reviews_comments") val totalReviewsComments: Int,
        @SerializedName("data") val reviews: List<Review>
)

@Entity
data class Review(
        @PrimaryKey @SerializedName("review_id") val id: Long?,
        @SerializedName("rating") val rating: String,
        @SerializedName("title") val title: String? = "",
        @SerializedName("message") val message: String,
        @SerializedName("author") val author: String,
        @SerializedName("foreignLanguage") val foreignLanguage: Boolean,
        @SerializedName("date") val date: String,
        @SerializedName("languageCode") val languageCode: String,
        @SerializedName("traveler_type") val traveler_type: String? = "",
        @SerializedName("reviewerName") val reviewerName: String,
        @SerializedName("reviewerCountry") val reviewerCountry: String
)