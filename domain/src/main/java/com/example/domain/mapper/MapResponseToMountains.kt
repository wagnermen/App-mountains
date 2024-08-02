package com.example.domain.mapper

import com.example.data.remote.model.response.MountainsResponseItem
import com.example.domain.model.DetailMountainModel
import com.example.domain.model.MountainsListModel
import local.MountainsEntity
import kotlin.random.Random

class MapResponseToMountains {
    companion object {
        fun mapResponseToMountains(response: List<MountainsResponseItem>): ArrayList<MountainsListModel> {
            val mountains = ArrayList<MountainsListModel>()
            response.forEach {
                if (it.mountain_img.contains("https://")) {
                    val mountain = MountainsListModel(
                        id = it._id,
                        name = it.name,
                        image = it.mountain_img,
                        country = it.location
                    )
                    mountains.add(mountain)
                }
            }
            return mountains
        }

        fun mapEntityToMountains(response: List<MountainsEntity>): ArrayList<MountainsListModel> {
            val mountains = ArrayList<MountainsListModel>()
            response.forEach {
                if (it.image.contains("https://")) {
                    val mountain = MountainsListModel(
                        id = it.id,
                        name = it.name,
                        image = it.image,
                        country = it.country
                    )
                    mountains.add(mountain)
                }
            }
            return mountains
        }

        fun mapResponseToDetailMountain(response: MountainsResponseItem): DetailMountainModel {
            val mountain = DetailMountainModel(
                id = response._id,
                name = response.name,
                description = response.description,
                altitude = getRandomCoordinate(coordinates),
                location = response.location,
                firstClimbedDate = response.first_climbed_date,
                firstClimber = response.first_climber,
                imageMountain = response.mountain_img,
                imageCountry = response.country_flag_img
            )
            return mountain
        }

        private fun getRandomCoordinate(coordinates: List<Pair<Double, Double>>): Pair<Double, Double> {
            val randomIndex = Random.nextInt(coordinates.size)
            return coordinates[randomIndex]
        }

        private val coordinates = listOf(
            Pair(40.7128, -74.0060), // Nueva York
            Pair(34.0522, -118.2437), // Los Ángeles
            Pair(35.6895, 139.6917), // Tokio
            Pair(-33.8688, 151.2093), // Sídney
            Pair(-22.9068, -43.1729), // Río de Janeiro
            Pair(43.6532, -79.3832),
            Pair(-34.6037, -58.3816),
            Pair(-26.2041, 28.0473),
            Pair(30.0444, 31.2357),
            Pair(51.5074, 0.1278) // Londres
        )

    }
}