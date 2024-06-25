package com.example.currentlocation

import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var latitude: TextView
    private lateinit var longtitude: TextView
    private lateinit var address:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        latitude = findViewById(R.id.latitude)
        longtitude = findViewById(R.id.longitude)
        val button = findViewById<Button>(R.id.button)
        address = findViewById(R.id.address)

        button.setOnClickListener {
            getLocation()
        }

    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
            return
        }
        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if (it != null) {
                val textLatitude = "Latitude"+it.latitude.toString()
                val textLongitude = "Longitude"+it.longitude.toString()
                val textAddress = "Address: "+getAddressName(it.latitude , it.longitude)
                latitude.text = textLatitude
                longtitude.text = textLongitude
                address.text = textAddress
            }
        }

    }

    private fun getAddressName(lat: Double, long: Double): String {
            var addressName = ""
        val geoCoder = Geocoder(this, Locale.getDefault())
        val address= geoCoder.getFromLocation(lat,long,1)
        addressName= address!![0].locality
        return addressName

    }
}