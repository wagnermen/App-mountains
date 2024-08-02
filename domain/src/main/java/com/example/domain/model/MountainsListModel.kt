package com.example.domain.model

import java.io.Serializable

data class MountainsListModel(
    val id: String,
    val name: String? = null,
    val image: String? = null,
    val country: String? = null,
): Serializable
