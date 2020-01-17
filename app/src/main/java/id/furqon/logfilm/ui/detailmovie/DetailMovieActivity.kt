package id.furqon.logfilm.ui.detailmovie

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import id.furqon.logfilm.R
import id.furqon.logfilm.data.BACKDROP_SIZE
import id.furqon.logfilm.data.BASE_IMAGE_URL
import id.furqon.logfilm.data.POSTER_SIZE
import id.furqon.logfilm.data.moviemodel.MovieDetails
import kotlinx.android.synthetic.main.movie_detail.*
import java.util.*

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var languange: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieId: Int = intent.getIntExtra("id", 1)

        languange = Locale.getDefault().toLanguageTag().toString()

        viewModel =
            ViewModelProviders.of(this, ViewModelProvider.NewInstanceFactory())
                .get(DetailMovieViewModel::class.java)

        viewModel.setMovieDetails(movieId, languange)

        showLoading(true)

        viewModel.getMovieDetails().observe(this, Observer {
            bindUI(it)
            showLoading(false)
            details_layout.visibility = View.VISIBLE
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(b: Boolean) {
        if (b) {
            detail_movie_progressBar.visibility = View.VISIBLE
        } else {
            detail_movie_progressBar.visibility = View.GONE
        }
    }

    private fun bindUI(it: MovieDetails) {
        detailJudul.text = it.title
        detailRilis.text = it.release_date
        detailRating.rating = it.vote_average!!.toFloat() / 2
        detailRatingText.text = it.vote_average.toString()
        detailDurasi.text = it.runtime.toString() + getString(R.string.minutes)
        detailTagline.text = it.tagline
        detailStatus.text = it.status
        detailSinopsis.text = it.overview

        if (it.adult.toString() == "false") detailGolongan.text =
            getString(R.string.all_ages) else getString(
            R.string.adult
        )

        val moviePosterURL = BASE_IMAGE_URL + POSTER_SIZE + it.poster_path
        Glide.with(this)
            .load(moviePosterURL)
            .into(detailPoster)

        val movieBackdropURL = BASE_IMAGE_URL + BACKDROP_SIZE + it.backdrop_path
        Glide.with(this)
            .load(movieBackdropURL)
            .into(background_poster)
    }
}
