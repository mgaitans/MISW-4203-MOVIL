package com.example.vinilos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.vinilos.R
import com.example.vinilos.databinding.ActivityCollectorBinding

class CollectorActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCollectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_collector) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController
        // Make sure actions in the ActionBar get propagated to the NavController
        Log.d("act", navController.toString())
        //setSupportActionBar(findViewById(R.id.my_toolbar))
        setupActionBarWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_menu, menu)
        supportActionBar!!.title = "Coleccionistas"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_inicio -> {
                // Create an intent with a destination of the other Activity
                val intent = Intent(this, InicioActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_album -> {
                // Create an intent with a destination of the other Activity
                val intent = Intent(this, AlbumActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_musician -> {
                // Create an intent with a destination of the other Activity
                val intent = Intent(this, MusicianActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}