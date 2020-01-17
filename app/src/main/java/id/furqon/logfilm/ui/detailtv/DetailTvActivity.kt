@file:Suppress("DEPRECATION")

package id.furqon.logfilm.ui.detailtv

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
import id.furqon.logfilm.data.tvmodel.TvDetails
import kotlinx.android.synthetic.main.movie_detail.background_poster
import kotlinx.android.synthetic.main.movie_detail.detailDurasi
import kotlinx.android.synthetic.main.movie_detail.detailGolongan
import kotlinx.android.synthetic.main.movie_detail.detailJudul
import kotlinx.android.synthetic.main.movie_detail.detailPoster
import kotlinx.android.synthetic.main.movie_detail.detailRating
import kotlinx.android.synthetic.main.movie_detail.detailRatingText
import kotlinx.android.synthetic.main.movie_detail.detailRilis
import kotlinx.android.synthetic.main.movie_detail.detailSinopsis
import kotlinx.android.synthetic.main.movie_detail.detailStatus
import kotlinx.android.synthetic.main.movie_detail.detailTagline
import kotlinx.android.synthetic.main.movie_detail.details_layout
import kotlinx.android.synthetic.main.movie_detail.toolbar
import kotlinx.android.synthetic.main.tv_detail.*
import java.util.*

class DetailTvActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailTvViewModel
    private lateinit var languange: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tv_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tvId: Int = intent.getIntExtra("id", 1)

        languange = Locale.getDefault().toLanguageTag().toString()

        viewModel =
            ViewModelProviders.of(this, ViewModelProvider.NewInstanceFactory())
                .get(DetailTvViewModel::class.java)

        viewModel.setTvDetails(tvId, languange)

        showLoading(true)

        viewModel.getTvDetails().observe(this, Observer {
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
            detail_tv_progressBar.visibility = View.VISIBLE
        } else {
            detail_tv_progressBar.visibility = View.GONE
        }
    }

    private fun bindUI(it: TvDetails) {
        detailJudul.text = it.name
        detailRilis.text = it.first_air_date
        detailRating.rating = it.vote_average!!.toFloat() / 2
        detailRatingText.text = it.vote_average.toString()
        detailDurasi.text = it.number_of_episodes.toString()
        detailTagline.text = it.number_of_seasons.toString()
        detailStatus.text = it.status
        detailSinopsis.text = it.overview
        detailGolongan.text = it.popularity

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
