package com.example.mpipapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.mpipapp.databinding.FragmentProfileBinding
import com.example.mpipapp.databinding.FragmentSearchBinding
import com.google.firebase.database.*

class ProfileFragment : Fragment() {
    private lateinit var dbref: DatabaseReference
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

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
        return binding.root
    }

}