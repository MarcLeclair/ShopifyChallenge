package com.shopifychallenge.cardgame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.shopifychallenge.cardgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.host_frag_nav)

        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)

        NavigationUI.setupWithNavController(binding.navView, navController)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.host_frag_nav)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
