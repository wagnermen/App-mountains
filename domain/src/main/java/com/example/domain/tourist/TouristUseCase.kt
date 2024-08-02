package com.example.domain.tourist

import com.example.domain.model.DetailMountainModel
import com.example.domain.model.MountainsListModel

interface TouristUseCase {
    suspend fun getMountains(): Resource<ArrayList<MountainsListModel>>
    suspend fun getDetailMountain(id: String): Resource<DetailMountainModel>
}