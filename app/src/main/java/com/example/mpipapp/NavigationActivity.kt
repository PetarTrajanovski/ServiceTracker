package com.example.mpipapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

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

        val CHANNEL_ID = "channelID"
        val CHANNEL_NAME = "channelName"
        val NOTIF_ID = 0

        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
            lightColor = Color.BLUE
            enableLights(true)
        }
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        val intent=Intent(this,NavigationActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }

        val notif = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Welcome to ServiceTracker")
            .setContentText("Notification test test")
            .setSmallIcon(R.drawable.ic_baseline_person_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()


        val notifManger = NotificationManagerCompat.from(this)

        notifManger.notify(NOTIF_ID,notif)
    }
    private  fun loadFragment(fragment: Fragment, user: String?){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Frame,fragment)
        transaction.commit()
    }
   /* private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }*/
   // }
}