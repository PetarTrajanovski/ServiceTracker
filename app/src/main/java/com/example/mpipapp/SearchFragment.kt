package com.example.mpipapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mpipapp.databinding.FragmentHomeBinding
import com.example.mpipapp.databinding.FragmentSearchBinding
import com.google.firebase.database.*


class SearchFragment : Fragment() {

    private lateinit var dbref: DatabaseReference
    private lateinit var customerRecyclerView: RecyclerView
    private lateinit var customerArrayList: ArrayList<Customer>
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)


        binding.serviceList.setOnClickListener{
            it.hideKeyboard()
            binding.nameQueryEDT.clearFocus()
            binding.phoneQueryEDT.clearFocus()
            binding.dateQueryEDT.clearFocus()
        }



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        customerRecyclerView = binding.serviceList
        customerRecyclerView.layoutManager = LinearLayoutManager(context)
        customerRecyclerView.setHasFixedSize(true)
        customerArrayList = arrayListOf<Customer>()


        dbref =
            FirebaseDatabase.getInstance("https://mpipapp-86173-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Customers")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (customerSnapShot in snapshot.children) {
                        val customer = customerSnapShot.getValue(Customer::class.java)
                        customerArrayList.add(customer!!)
                    }
                   customerRecyclerView.adapter = RecyclerAdapter(customerArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.nameQueryButton.setOnClickListener{
            val filteredByNameItem = ArrayList<Customer>()
            for (item in customerArrayList) {
                if (item.name!!.lowercase().contains(binding.nameQueryEDT.text.toString().lowercase())) {
                    filteredByNameItem.add(item)
                }
            }
            customerRecyclerView.adapter = RecyclerAdapter(filteredByNameItem)
        }
        binding.phoneQueryButton.setOnClickListener{
            val filteredByPhoneItem = ArrayList<Customer>()
            for (item in customerArrayList) {
                if (item.phone!!.contains(binding.phoneQueryEDT.text.toString())) {
                    filteredByPhoneItem.add(item)
                }
            }
            customerRecyclerView.adapter = RecyclerAdapter(filteredByPhoneItem)

        }
        binding.dateQueryButton.setOnClickListener{
            val filteredByDateItem = ArrayList<Customer>()
            for (item in customerArrayList) {
                if (item.date!!.contains(binding.dateQueryEDT.text.toString())) {
                    filteredByDateItem.add(item)
                }
            }
            customerRecyclerView.adapter = RecyclerAdapter(filteredByDateItem)
        }
    }
    fun View.hideKeyboard() = this.let { view ->
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.apply { hideSoftInputFromWindow(view.windowToken, 0) }
    }
}

