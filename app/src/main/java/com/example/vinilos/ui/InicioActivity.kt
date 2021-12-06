package com.example.vinilos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.vinilos.R

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_menu, menu)
        supportActionBar!!.title = "Inicio"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_album -> {
                // Create an intent with a destination of the other Activity
                val intent = Intent(this, AlbumActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_coleccionista -> {
                // Create an intent with a destination of the other Activity
                val intent = Intent(this, CollectorActivity::class.java)
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