package com.meneses.weatherapp

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.meneses.weatherapp.common.Constants
import com.meneses.weatherapp.databinding.ActivityMainBinding
import com.meneses.weatherapp.models.mockservice.ResponseData
import com.meneses.weatherapp.models.weathermap.weatherResponse

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        validateLocation()

        /*mBinding.cvWeather.setOnClickListener {
            getWeather()
        }*/


    }

    private fun validateLocation() {
        if (isLocationEnabled()) {
            Toast.makeText(this, "Ubicación activada", Toast.LENGTH_SHORT).show()
            getInitialPeticion()

            mBinding.rfresh.setOnRefreshListener {
                mBinding.rfresh.isRefreshing = false
                getWeather()
            }


        } else {
            Toast.makeText(this, "Ubicación desactivada", Toast.LENGTH_SHORT).show()
            val i = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(i)
        }
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getInitialPeticion() {

        val url = Constants.BASE_URL_MOCKY + Constants.LOGIN

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->

            Log.e("Response", response.toString())

            val jsonResponse = Gson().fromJson<ResponseData>(response.toString(), ResponseData::class.java)

            val msg = jsonResponse.message

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        }, {
            it.printStackTrace()
            Log.e("Response", "Error Peticion ${it.message}")
        })

        MainApplication.API.addToRequestQueue(jsonObjectRequest)

        getWeather()

    }

    private fun getWeather(){
        val url = Constants.BASE_UR_WEATHER

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->

            Log.e("Response", response.toString())

            val jsonResponse = Gson().fromJson<weatherResponse>(response.toString(), weatherResponse::class.java)

            //val msg = jsonResponse.message
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        }, {
            it.printStackTrace()
            Log.e("Response", "Error Peticion ${it.message}")
        })

        MainApplication.API.addToRequestQueue(jsonObjectRequest)
    }

}