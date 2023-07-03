package com.example.mpipapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.mpipapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment() {
    private lateinit var dbref: DatabaseReference
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)


        val username = (activity as NavigationActivity).user
        binding.UsernamePlaceHolder.text= username.toString()

        binding.findTotalServicesButton.setOnClickListener {
            dbref =
                FirebaseDatabase.getInstance("https://mpipapp-86173-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Customers")
            dbref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var i = 0
                        for (customerSnapShot in snapshot.children) {
                            val customer = customerSnapShot.getValue(Customer::class.java)
                            if (customer?.servicer.toString() == username.toString())
                                i++
                        }
                        binding.NoofServices.text =
                            "Total amount of services done by " + username + ": " + i.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
        auth = FirebaseAuth.getInstance()
        binding.SignOut.setOnClickListener{
            auth.signOut()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}