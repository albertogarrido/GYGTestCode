package net.albertogarrido.gygreviews.ui.reviewslist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import net.albertogarrido.gygreviews.repository.GygRepository
import net.albertogarrido.gygreviews.repository.Resource
import net.albertogarrido.gygreviews.repository.Review
import net.albertogarrido.gygreviews.repository.network.NetworkStatus
import net.albertogarrido.gygreviews.repository.persistence.ReviewsDatabase

class ReviewListViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var reviewsLiveData: LiveData<Resource<List<Review>>>

    fun fetchData(networkStatus: NetworkStatus, database: ReviewsDatabase) {
        reviewsLiveData = when (networkStatus) {
            NetworkStatus.OFFLINE -> GygRepository.getDBReviews(database)
            NetworkStatus.ONLINE -> GygRepository.getApiReviews()
        }
    }

    fun getReviews(): LiveData<Resource<List<Review>>> =
            if (::reviewsLiveData.isInitialized) {
                reviewsLiveData
            } else {
                throw IllegalAccessException("reviewsLiveData should be initialized by calling fetchData before using it")
            }
}

