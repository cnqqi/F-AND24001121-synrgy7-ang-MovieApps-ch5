package com.synrgy.movieapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.synrgy.movieapp.R
import com.synrgy.movieapp.model.Movie

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class DetailFragment : Fragment() {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolbarDetail)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Detail Movie"

        backdrop = view.findViewById(R.id.movie_backdrop)
        poster = view.findViewById(R.id.movie_poster)
        title = view.findViewById(R.id.movie_title)
        rating = view.findViewById(R.id.movie_rating)
        releaseDate = view.findViewById(R.id.movie_release_date)
        overview = view.findViewById(R.id.movie_overview)

        val extras = arguments

        if (extras != null) {
            populateDetails(extras)
        } else {
            activity?.onBackPressed()  // Changed to go back in fragment
        }
    }

    private fun populateDetails(extras: Bundle) {
        movie = Movie(
            id = 0, // Use a default ID here if not provided
            backdropPath = extras.getString(MOVIE_BACKDROP) ?: "",
            posterPath = extras.getString(MOVIE_POSTER) ?: "",
            title = extras.getString(MOVIE_TITLE) ?: "",
            rating = extras.getFloat(MOVIE_RATING, 0f),
            releaseDate = extras.getString(MOVIE_RELEASE_DATE) ?: "",
            overview = extras.getString(MOVIE_OVERVIEW) ?: ""
        )

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w1280${movie.backdropPath}")
            .transform(CenterCrop())
            .into(backdrop)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
            .transform(CenterCrop())
            .into(poster)

        title.text = movie.title
        rating.rating = movie.rating / 2
        releaseDate.text = movie.releaseDate
        overview.text = movie.overview
    }
}


