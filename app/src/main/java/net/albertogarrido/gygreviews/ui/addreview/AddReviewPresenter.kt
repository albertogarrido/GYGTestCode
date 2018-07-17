package net.albertogarrido.gygreviews.ui.addreview

import android.os.Handler
import android.os.Looper
import net.albertogarrido.gygreviews.repository.Review

class AddReviewPresenter(private val view: AddReviewView) {

    interface AddReviewView {
        fun toggleLoading(show: Boolean)
    }

    fun saveReview(review: Review, callback: () -> Unit) {
        view.toggleLoading(true)
        // Simulating long running operation
        Handler(Looper.getMainLooper())
                .post {
                    Thread.sleep(2000)
                    callback()
                }
    }
}
