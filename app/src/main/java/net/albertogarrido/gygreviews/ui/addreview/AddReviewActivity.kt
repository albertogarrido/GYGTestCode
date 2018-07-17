package net.albertogarrido.gygreviews.ui.addreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_review.*
import kotlinx.android.synthetic.main.content_add_review.*
import net.albertogarrido.gygreviews.R
import net.albertogarrido.gygreviews.repository.Review
import net.albertogarrido.gygreviews.util.format
import net.albertogarrido.gygreviews.util.gone
import net.albertogarrido.gygreviews.util.visible
import java.util.*

class AddReviewActivity : AppCompatActivity(), AddReviewPresenter.AddReviewView {
    companion object {
        fun createIntent(context: Context): Intent =
                Intent(context, AddReviewActivity::class.java)
    }

    private val presenter: AddReviewPresenter by lazy {
        AddReviewPresenter(view = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_review)
    }

    override fun onResume() {
        super.onResume()
        configureToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_review_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.action_save -> {
                    hideKeyboard()
                    validate({
                        presenter.saveReview(it) {
                            Snackbar.make(root, "Success!", Toast.LENGTH_SHORT)
                                    .setAction("Close") { finish() }.show()
                            toggleLoading(false)
                        }
                    }, {
                        Snackbar.make(root, "Please review all the fields!", Toast.LENGTH_SHORT).show()
                    })

                    true
                }
                android.R.id.home -> {
                    finish()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    private fun validate(save: (Review) -> Unit, onError: () -> Unit) {
        var isValid = true
        val errorMessageStringEmpty = resources.getString(R.string.error_empty_string)
        val errorMessageRating = resources.getString(R.string.error_empty_rating)

        isValid = if (inputAuthor.text.isBlank()) {
            inputLayoutAuthor.error = errorMessageStringEmpty
            false
        } else {
            inputLayoutAuthor.error = null
            isValid
        }

        isValid = if (inputMessage.text.isBlank()) {
            inputLayoutMessage.error = errorMessageStringEmpty
            false
        } else {
            inputLayoutMessage.error = null
            isValid
        }

        isValid = if (inputTitle.text.isBlank()) {
            inputLayoutTitle.error = errorMessageStringEmpty
            false
        } else {
            inputLayoutTitle.error = null
            isValid
        }

        isValid = if (inputMessage.text.isBlank()) {
            inputLayoutMessage.error = errorMessageStringEmpty
            false
        } else {
            inputLayoutMessage.error = null
            isValid
        }

        isValid = if (ratingBar.rating == 0f) {
            ratingErrorIndicator.visible()
            ratingErrorIndicator.text = errorMessageRating
            false
        } else {
            ratingErrorIndicator.text = null
            ratingErrorIndicator.gone()
            isValid
        }

        if (isValid) {
            val review = Review(
                    id = null,
                    rating = ratingBar.rating.toString(),
                    title = inputTitle.text.toString(),
                    message = inputMessage.text.toString(),
                    author = inputAuthor.text.toString(),
                    foreignLanguage = true,
                    date = Date().format(),
                    languageCode = "",
                    traveler_type = "",
                    reviewerName = inputAuthor.text.toString(),
                    reviewerCountry = ""
            )
            save(review)
        } else {
            onError()
        }
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = resources.getString(R.string.add_review_title)
    }

    private fun hideKeyboard() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(inputLayoutMessage.windowToken, 0)
        }
    }

    override fun toggleLoading(show: Boolean) {
        if (show) {
            progressBarContainer.visible()
        } else {
            progressBarContainer.gone()
        }
    }

}
