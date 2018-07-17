package net.albertogarrido.gygreviews.repository

import android.arch.lifecycle.MutableLiveData
import net.albertogarrido.gygreviews.di.provideRetrofitClient
import net.albertogarrido.gygreviews.repository.gygapi.GygApiService
import net.albertogarrido.gygreviews.repository.persistence.ReviewsDatabase
import net.albertogarrido.gygreviews.util.enqueue
import retrofit2.Retrofit
import kotlin.concurrent.thread

object GygRepository {

    private val retrofit: Retrofit by lazy {
        provideRetrofitClient()
    }

    private val apiService: GygApiService by lazy {
        retrofit.create(GygApiService::class.java)
    }

    fun getDBReviews(database: ReviewsDatabase):
            MutableLiveData<Resource<List<Review>>> {
        val liveData = MutableLiveData<Resource<List<Review>>>()
        thread {
            liveData.postValue(resourceSuccess(database.reviewDao().getAll()))
        }
        return liveData
    }

    fun getApiReviews(): MutableLiveData<Resource<List<Review>>> {

        val liveData = MutableLiveData<Resource<List<Review>>>()

        liveData.postValue(resourceLoading(listOf()))

        apiService.reviews().enqueue(
                { response ->
                    when {
                        response.body() == null -> liveData.setValue(resourceEmpty(response.message(), listOf()))
                        response.isSuccessful -> {
                            // this cannot be null, but smart cast complaints, hence the double !!
                            val repositoriesResponse: ReviewsResponse = response.body()!!
                            if (repositoriesResponse.reviews.isEmpty()) {
                                liveData.setValue(resourceEmpty(response.message(), repositoriesResponse.reviews))
                            } else {
                                liveData.setValue(resourceSuccess(repositoriesResponse.reviews))
                            }
                        }
                        else -> liveData.setValue(resourceError(response.message(), listOf()))
                    }
                },
                { throwable ->
                    liveData.setValue(resourceError(throwable.message ?: "Unknown error", listOf()))
                }
        )
        return liveData
    }
}
