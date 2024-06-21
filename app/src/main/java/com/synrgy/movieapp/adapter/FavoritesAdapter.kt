package com.synrgy.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synrgy.movieapp.R
import com.synrgy.movieapp.model.Movie

class FavoritesAdapter(private var favoriteMovies: MutableList<Movie>) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moviePoster: ImageView = view.findViewById(R.id.moviePoster)
        val movieTitle: TextView = view.findViewById(R.id.movieTitle)
        val movieReleaseDate: TextView = view.findViewById(R.id.movieReleaseDate)
        val movieRating: RatingBar = view.findViewById(R.id.movieRating)
        val movieOverview: TextView = view.findViewById(R.id.movieOverview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_movie, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val movie = favoriteMovies[position]

        Glide.with(holder.moviePoster.context)
            .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
            .into(holder.moviePoster)

        holder.movieTitle.text = movie.title
        holder.movieReleaseDate.text = movie.releaseDate
        holder.movieRating.rating = movie.rating / 2 // Assuming rating is out of 10
        holder.movieOverview.text = movie.overview
    }

    override fun getItemCount() = favoriteMovies.size

    fun updateMovies(newMovies: List<Movie>) {
        favoriteMovies.clear()
        favoriteMovies.addAll(newMovies)
        notifyDataSetChanged()
    }
}