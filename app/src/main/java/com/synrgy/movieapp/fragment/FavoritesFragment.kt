package com.synrgy.movieapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.movieapp.R
import com.synrgy.movieapp.adapter.FavoritesAdapter
import com.synrgy.movieapp.util.FavoritesRepository

class FavoritesFragment : Fragment() {

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        // Set up the RecyclerView and its adapter
        favoritesRecyclerView = view.findViewById(R.id.recyclerViewFavorites)
        favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoritesAdapter = FavoritesAdapter(mutableListOf())
        favoritesRecyclerView.adapter = favoritesAdapter

        // Load the favorite movies into the RecyclerView
        loadFavoriteMovies()

        return view
    }

    private fun loadFavoriteMovies() {
        // Get the favorite movies from the repository
        val favoriteMovies = FavoritesRepository.getFavoriteMovies(requireContext())
        // Update the adapter with the favorite movies
        favoritesAdapter.updateMovies(favoriteMovies)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
