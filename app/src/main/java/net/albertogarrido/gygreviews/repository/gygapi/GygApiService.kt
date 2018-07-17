package net.albertogarrido.gygreviews.repository.gygapi

import net.albertogarrido.gygreviews.repository.ReviewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GygApiService {

    companion object {
        private const val E_TOUR_REVIEWS = "{city}/{tourCode}/reviews.json"
        const val DEFAULT_CITY = "berlin-l17"
        const val DEFAULT_TOUR_CODE = "tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776"
    }

    @Headers("User-Agent: net.albertogarrido.gygreviews:test-code")
    @GET(E_TOUR_REVIEWS)
    fun reviews(@Path("city") city: String = DEFAULT_CITY,
                @Path("tourCode") tourCode: String = DEFAULT_TOUR_CODE,
                @Query("count") count: Int = 25,
                @Query("page") page: Int = 0,
                @Query("sortBy") sortBy: String = "date_of_review",
                @Query("direction") direction: String = "DESC"): Call<ReviewsResponse>

}