package com.example.mpipapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception

class NavigationActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView
    lateinit var user: String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        user = intent.extras?.getString("username")!!

        loadFragment(HomeFragment(), user)
        bottomNav = findViewById<BottomNavigationView>(R.id.BottomNavigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment(), user)
                    true
                }
                R.id.search -> {
                    loadFragment(SearchFragment(), user)
                    true
                }
                R.id.profile -> {
                    loadFragment(ProfileFragment(), user)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }
    private  fun loadFragment(fragment: Fragment, user: String?){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Frame,fragment)
        transaction.commit()

    }

}