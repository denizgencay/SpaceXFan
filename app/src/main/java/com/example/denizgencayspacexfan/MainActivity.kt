package com.example.denizgencayspacexfan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.denizgencayspacexfan.ui.authentication.signin.SignInFragment
import com.example.denizgencayspacexfan.ui.favorites.FavoritesFragment
import com.example.denizgencayspacexfan.ui.rockets.RocketsFragment
import com.example.denizgencayspacexfan.ui.upcoming.UpcomingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val signInFragment = SignInFragment()
    private val rocketsFragment = RocketsFragment()
    private val upcomingFragment = UpcomingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        commitFragment(rocketsFragment)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            when(it.itemId){
                R.id.nav_bar_rockets -> commitFragment(rocketsFragment)
                R.id.nav_bar_favorites -> commitFragment(signInFragment)
                R.id.nav_bar_upcoming -> commitFragment(upcomingFragment)
            }
            true
        })
    }

    private fun commitFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }
}