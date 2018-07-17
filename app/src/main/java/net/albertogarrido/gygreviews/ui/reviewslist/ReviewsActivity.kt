package net.albertogarrido.gygreviews.ui.reviewslist

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_reviews.*
import kotlinx.android.synthetic.main.content_reviews.*
import net.albertogarrido.gygreviews.R
import net.albertogarrido.gygreviews.di.Database
import net.albertogarrido.gygreviews.repository.Review
import net.albertogarrido.gygreviews.repository.network.NetworkStatus
import net.albertogarrido.gygreviews.repository.network.NetworkStatusLiveData
import net.albertogarrido.gygreviews.ui.addreview.AddReviewActivity
import net.albertogarrido.gygreviews.util.fadeOut
import net.albertogarrido.gygreviews.util.gone
import net.albertogarrido.gygreviews.util.visible

class ReviewsActivity : AppCompatActivity(), ReviewsPresenter.ReviewsView {

    private val presenter: ReviewsPresenter by lazy {
        ReviewsPresenter(view = this, lifecycleOwner = this, reviewListViewModel = reviewsViewModel)
    }

    private val reviewsViewModel: ReviewListViewModel by lazy {
        ViewModelProviders.of(this).get(ReviewListViewModel::class.java)
    }
    private val reviewsAdapter: ReviewsAdapter by lazy {
        ReviewsAdapter()
    }

    private lateinit var currentNetworkStatus: NetworkStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)
        setSupportActionBar(toolbar)
        configureRecyclerView()
        observeNetwork()
        observeReviews()

        fab.setOnClickListener {
            startActivity(AddReviewActivity.createIntent(this))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_reviews_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                Snackbar.make(rootView, "Coming soon", Snackbar.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeReviews() {
        currentNetworkStatus = NetworkStatusLiveData.getConnectionStatus(this)
        handleNetworkStatus(currentNetworkStatus)
        presenter.observeReviews(currentNetworkStatus, Database.provideDatabase(this)!!) {
            presenter.handleResponse(it)
        }
    }

    private fun observeNetwork() {
        presenter.observeNetworkChanges {
            handleNetworkStatus(it)
        }
    }

    private fun handleNetworkStatus(networkStatus: NetworkStatus) {
        if (!::currentNetworkStatus.isInitialized) {
            currentNetworkStatus = networkStatus
        } else {
            if (networkStatus == NetworkStatus.OFFLINE) {
                toggleLoading(false)
                offlineIndicatorContainer.visible()
                offlineIndicatorText.text = resources.getString(R.string.offline_indicator)
            } else {
                offlineIndicatorText.text = resources.getString(R.string.online_indicator)
                offlineIndicatorContainer.setOnClickListener {
                    offlineIndicatorContainer.fadeOut()
                    observeReviews()
                }
            }
        }
        currentNetworkStatus = networkStatus
    }

    override fun populateResults(reviews: List<Review>) {
        reviewsAdapter.submitList(reviews)
        toolbar.title = resources.getQuantityString(R.plurals.reviews_title, reviews.size, reviews.size)
    }

    private fun configureRecyclerView() {
        reviewsList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        reviewsList.layoutManager = layoutManager
        reviewsList.adapter = reviewsAdapter
    }

    override fun toggleLoading(show: Boolean) {
        if (show) {
            progressBarContainer.visible()
        } else {
            progressBarContainer.gone()
        }
    }

    override fun getContext(): Context = this
}
