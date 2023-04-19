package com.meneses.weatherapp.models.mockservice

data class ResponseData(
    val data_list: List<DataListDetails>,
    val email: String,
    val message: String,
    val mobile: Int,
    val name: String,
    val profile_details: ProfileDetails,
    val user_id: Int
)