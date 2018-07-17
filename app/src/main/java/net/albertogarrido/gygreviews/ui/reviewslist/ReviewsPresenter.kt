package net.albertogarrido.gygreviews.ui.reviewslist

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.util.Log
import net.albertogarrido.gygreviews.di.getNetworkStatusLiveData
import net.albertogarrido.gygreviews.repository.Resource
import net.albertogarrido.gygreviews.repository.Review
import net.albertogarrido.gygreviews.repository.network.NetworkStatus
import net.albertogarrido.gygreviews.repository.persistence.ReviewsDatabase
import kotlin.concurrent.thread

class ReviewsPresenter(private val view: ReviewsView,
                       private val lifecycleOwner: LifecycleOwner,
                       private val reviewListViewModel: ReviewListViewModel) {

    fun observeNetworkChanges(callback: (NetworkStatus) -> Unit) {
        getNetworkStatusLiveData(view.getContext()).observe(lifecycleOwner, Observer {
            callback(it!!)
        })
    }

    fun observeReviews(networkStatus: NetworkStatus, database: ReviewsDatabase, callback: (Resource<List<Review>>) -> Unit) {
        view.toggleLoading(true)
        reviewListViewModel.fetchData(networkStatus, database)
        reviewListViewModel.getReviews().observe(lifecycleOwner, Observer {
            if (it!!.data.isNotEmpty()) {
                callback(it)
                thread {
                    database.reviewDao().insertAll(*it.data.toTypedArray())
                }
            }
        })
    }

    fun handleResponse(reviewsResponse: Resource<List<Review>>) {
        view.populateResults(reviewsResponse.data)
        view.toggleLoading(false)
    }

    interface ReviewsView {
        fun populateResults(reviews: List<Review>)
        fun toggleLoading(show: Boolean)
        fun getContext(): Context
    }
}
