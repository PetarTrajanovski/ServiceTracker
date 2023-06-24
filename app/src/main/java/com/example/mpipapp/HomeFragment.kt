package com.example.mpipapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.mpipapp.databinding.FragmentHomeBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    // assign the _binding variable initially to null and
    // also when the view is destroyed again it has to be set to null
    private var _binding: FragmentHomeBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!

    private var mContext: Context? = null
    fun FirstFragment() {
        // Required empty public constructor
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val username = (activity as NavigationActivity).user
        binding.currUserEDT.text= username.toString()

        binding.root.setOnClickListener {
            it.hideKeyboard()
            binding.customerName.clearFocus()
            binding.phone.clearFocus()
            binding.Address.clearFocus()
            binding.modelEditText.clearFocus()
        }
        binding.dateEditText.transformIntoDatePicker(requireContext(), "dd/MM/yyyy", Date())
        var brand = "Vivax"
        val spinner = binding.spinner
        val brands = arrayOf ("Vivax", "Hisense", "Beko", "Tesla", "Aux", "Tosot", "Toshiba", "Carrier", "Fuego", "Other")
        val arrayAdapter =
            mContext?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, brands) }

        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                brand = brands[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


        binding.AddButton.setOnClickListener {
            val name = binding.customerName.text.toString()
            val phone = binding.phone.text.toString()
            val address = binding.Address.text.toString()
            val model = binding.modelEditText.text.toString()
            val date = binding.dateEditText.text.toString()
            val customer = Customer(name, phone, address, brand, date, model, username)

            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val current = formatter.format(time)

            val database =
                FirebaseDatabase.getInstance("https://mpipapp-86173-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Customers")
            if (name.isBlank() || phone.isBlank() || address.isBlank() || model.isBlank() || date == "Date") {
                Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {
                database.child(current.toString()).setValue(customer).addOnSuccessListener {
                    binding.customerName.text.clear()
                    binding.phone.text.clear()
                    binding.Address.text.clear()
                    binding.modelEditText.text.clear()

                    Toast.makeText(context, "Successfully added to database", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed to add to database", Toast.LENGTH_SHORT).show()
                }
            }
        }




        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
              //  maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }

        }
    }

    fun View.hideKeyboard() = this.let { view ->
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.apply { hideSoftInputFromWindow(view.windowToken, 0) }
    }

}