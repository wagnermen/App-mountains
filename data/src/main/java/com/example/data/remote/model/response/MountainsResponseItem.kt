package com.example.data.remote.model.response

data class MountainsResponseItem(
    val _id: String,
    val altitude: String,
    val country_flag_img: String,
    val description: String,
    val first_climbed_date: String,
    val first_climber: String,
    val has_death_zone: Boolean,
    val location: String,
    val mountain_img: String,
    val name: String
)