package com.synrgy.movieapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.loginFragment, R.id.homeFragment))

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    invalidateOptionsMenu()  // Refresh the toolbar menu items
                }
                R.id.homeFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    invalidateOptionsMenu()  // Refresh the toolbar menu items
                }
                else -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    invalidateOptionsMenu()  // Refresh the toolbar menu items
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val navDestinationId = navController.currentDestination?.id

        val personMenuItem = menu.findItem(R.id.action_person)
        val favoriteMenuItem = menu.findItem(R.id.action_favorite)

        personMenuItem.isVisible = navDestinationId != R.id.loginFragment
        favoriteMenuItem.isVisible = navDestinationId != R.id.loginFragment

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_person -> {
                if (navController.currentDestination?.id == R.id.homeFragment) {
                    navController.navigate(R.id.action_homeFragment_to_ProfileFragment)
                }
                true
            }
            R.id.action_favorite -> {
                if (navController.currentDestination?.id == R.id.homeFragment) {
                    navController.navigate(R.id.action_homeFragment_to_FavoritesFragment)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
