package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class NextActivity : AppCompatActivity() {
    private lateinit var donorname: TextInputEditText
    private lateinit var Parcel: TextInputEditText
    private lateinit var dateofdonation: TextInputEditText
    private lateinit var DateOfService: TextInputEditText
    private lateinit var whatsno: TextInputEditText
    private lateinit var Amtdonated: TextInputEditText
    private lateinit var NoofParcels: TextInputEditText
    private lateinit var paymentmode: TextInputEditText
    private lateinit var RmName: TextInputEditText
    private lateinit var MallName: TextInputEditText
    private lateinit var btnSub: Button
    private lateinit var category: AppCompatSpinner


    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        donorname = findViewById(R.id.donorname)
        Parcel = findViewById(R.id.Parcel)
        dateofdonation = findViewById(R.id.dateofdonation)
        DateOfService = findViewById(R.id.DateOfService)
        whatsno = findViewById(R.id.whatsno)
        category = findViewById(R.id.category)
        Amtdonated = findViewById(R.id.Amtdonated)
        NoofParcels = findViewById(R.id.NoofParcels)
        paymentmode = findViewById(R.id.paymentmode)
        RmName = findViewById(R.id.RmName)
        MallName = findViewById(R.id.MallName)
        btnSub = findViewById(R.id.btnsub)




        val spinner: Spinner = findViewById(R.id.category)
        val categoriesArray = resources.getStringArray(R.array.category_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoriesArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter




        // Optional: Set a default selection (Select the Category) for the Spinner
        val defaultSelection = getString(R.string.select)
        val position = categoriesArray.indexOf(defaultSelection)
        spinner.setSelection(position)
        // Autofill the dateofdonation field with the current date
        val currentDate = getCurrentDate()
        dateofdonation.setText(currentDate)

        // Set up a click listener for the date field
        dateofdonation.setOnClickListener {
            showDatePickerDialog()
        }

        btnSub.setOnClickListener {
            if (areFieldsValid()) {
                // All required fields are filled, so proceed to the EndActivity
                val bundle = Bundle()
                bundle.putString("donorname", donorname.text.toString())
                bundle.putString("Parcel", Parcel.text.toString())
                bundle.putString("dateofdonation", dateofdonation.text.toString())
                bundle.putString("whatsno", whatsno.text.toString())
                bundle.putString("DateOfService", DateOfService.text.toString())

                // Getting the selected category from the spinner
                val selectedCategory = category.selectedItem.toString()
                bundle.putString("category", selectedCategory)

                bundle.putString("Amtdonated", Amtdonated.text.toString())
                bundle.putString("NoofParcels", NoofParcels.text.toString())
                bundle.putString("paymentmode", paymentmode.text.toString())
                bundle.putString("RmName", RmName.text.toString())
                bundle.putString("MallName", MallName.text.toString())

                val intent = Intent(this, EndActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        }
    }

    private fun areFieldsValid(): Boolean {
        val requiredFields = listOf(
            donorname, Parcel, dateofdonation, whatsno, DateOfService,
            Amtdonated, NoofParcels, paymentmode, RmName, MallName
        )

        val emptyField = requiredFields.firstOrNull { it.text.isNullOrBlank() }
        return if (emptyField != null) {
            // Display an error message or toast to inform the user to fill in all required fields.
            // For example, you can use a Toast message:
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }


    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                dateofdonation.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}

    
