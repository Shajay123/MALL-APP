package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityEndBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import pl.droidsonroids.gif.GifImageView


class EndActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEndBinding
    private lateinit var database: DatabaseReference

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val btnSave: Button = binding.btnsave
        val btnShare: Button = binding.btnShare



        // Declare and initialize all TextViews using view binding
        val donorname: TextView = binding.donorname
        val dateofdonation: TextView = binding.dateofdonation
        val Parcel: TextView = binding.Parcel
        val DateOfService: TextView = binding.DateOfService
        val whatsno: TextView = binding.whatsno
        val category: TextView = binding.category
        val Amtdonated: TextView = binding.Amtdonated
        val NoofParcels: TextView = binding.NoofParcels
        val paymentmode: TextView = binding.paymentmode
        val RmName: TextView = binding.RmName
        val MallName: TextView = binding.MallName



        val bundle = intent.extras
        if (bundle != null) {
            donorname.text = "DONOR NAME = ${bundle.getString("donorname")}"
            Parcel.text = "PARCEL NAME = ${bundle.getString("Parcel")}"
            dateofdonation.text = "DATE OF DONATION = ${bundle.getString("dateofdonation")}"
            DateOfService.text = "DATE OF SERVICE = ${bundle.getString("DateOfService")}"
            whatsno.text = "WHATSAPP NUMBER = ${bundle.getString("whatsno")}"
            category.text = "CATEGORY = ${bundle.getString("category")}"
            Amtdonated.text = "AMOUNT DONATED = ${bundle.getString("Amtdonated")}"
            NoofParcels.text = "NO OF PARCELS = ${bundle.getString("NoofParcels")}"
            paymentmode.text = "MODE OF PAYMENT = ${bundle.getString("paymentmode")}"
            RmName.text = "RM NAME = ${bundle.getString("RmName")}"
            MallName.text = "MALL NAME = ${bundle.getString("MallName")}"

        }

        btnSave.setOnClickListener {
            saveData()
        }

        btnShare.setOnClickListener {
            shareData()
        }
    }

    private fun saveData() {
        // Retrieve values directly from the binding object
        val donorname = binding.donorname.text.toString()
        val parcel = binding.Parcel.text.toString()
        val dateofdonation = binding.dateofdonation.text.toString()
        val DateOfService = binding.DateOfService.text.toString()
        val whatsno = binding.whatsno.text.toString()
        val category = binding.category.text.toString()
        val Amtdonated = binding.Amtdonated.text.toString()
        val NoofParcels = binding.NoofParcels.text.toString()
        val paymentmode = binding.paymentmode.text.toString()
        val RmName = binding.RmName.text.toString()
        val MallName = binding.MallName.text.toString()

        val detailsMap = mapOf(
            "donorname" to donorname,
            "parcel" to parcel,
            "dateofdonation" to dateofdonation,
            "DateOfService" to DateOfService,
            "whatsno" to whatsno,
            "category" to category,
            "Amtdonated" to Amtdonated,
            "NoofParcels" to NoofParcels,
            "paymentmode" to paymentmode,
            "RmName" to RmName,
            "MallName" to MallName
        )

        // Use a consistent reference to the database
        val database = FirebaseDatabase.getInstance().getReference("Details")

        // Reference to the specific RM NAME node under Details
        val rmNameRef = database.child(RmName)

        // Save the details under the specific RM NAME -> DateOfDonation -> Id
        val dateOfDonationRef = rmNameRef.child(dateofdonation)
        val detailsId = dateOfDonationRef.push().key ?: ""

        // Save other details under the unique ID within DateOfDonation node
        val detailsRef = dateOfDonationRef.child(detailsId)
        detailsRef.setValue(detailsMap)
            .addOnSuccessListener {
                // Toast for successful save
                Toast.makeText(this, "Details successfully saved", Toast.LENGTH_SHORT).show()
                // Enable the gif image when the database save is successful
                enableGifImage(true)
                // Enable the "Share" button after successful save
                binding.btnShare.isEnabled = true
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }

        // Disable the "Save" button for a short duration after clicking
        binding.btnsave.isEnabled = false
        val handler = Handler()
        handler.postDelayed({
            binding.btnsave.isEnabled = true
        }, 300000)
    }
    private fun enableGifImage(enable: Boolean) {
        // Set the visibility of the GifImageView based on the parameter 'enable'
        binding.savegif.visibility = if (enable) View.VISIBLE else View.INVISIBLE
    }
    private fun shareData() {
        // Retrieve values directly from the binding object
        val donorname = binding.donorname.text.toString()
        val parcel = binding.Parcel.text.toString()
        val dateofdonation = binding.dateofdonation.text.toString()
        val DateOfService = binding.DateOfService.text.toString()
        val whatsno = binding.whatsno.text.toString()
        val category = binding.category.text.toString()
        val Amtdonated = binding.Amtdonated.text.toString()
        val NoofParcels = binding.NoofParcels.text.toString()
        val paymentmode = binding.paymentmode.text.toString()
        val RmName = binding.RmName.text.toString()
        val MallName = binding.MallName.text.toString()

        // Ensure data has been saved before sharing
        if (binding.btnShare.isEnabled) {
            val shareMessage = """
            $donorname
            $parcel
            $dateofdonation
            $DateOfService
            $whatsno
            $category
            $Amtdonated
            $NoofParcels
            $paymentmode
            $RmName
            $MallName
        """.trimIndent()

            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.whatsapp")

            try {
                startActivity(sendIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "WhatsApp is not installed or not available", Toast.LENGTH_SHORT).show()
            }
            finish()
        } else {
            Toast.makeText(this, "Please save the details before sharing", Toast.LENGTH_SHORT).show()
        }
    }
}
