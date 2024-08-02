package com.example.domain.model

data class DetailMountainModel(
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val altitude: Pair<Double, Double>? = null,
    val location: String? = null,
    val firstClimbedDate: String? = null,
    val firstClimber: String? = null,
    val imageMountain: String? = null,
    val imageCountry: String? = null
)
