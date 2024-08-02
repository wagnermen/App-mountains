package com.example.domain.tourist

import com.example.data.remote.api.ApiTouristService
import com.example.data.remote.model.response.MountainsResponseItem
import com.example.domain.model.DetailMountainModel
import com.example.domain.mapper.MapResponseToMountains.Companion.mapEntityToMountains
import com.example.domain.mapper.MapResponseToMountains.Companion.mapResponseToDetailMountain
import com.example.domain.mapper.MapResponseToMountains.Companion.mapResponseToMountains
import com.example.domain.model.MountainsListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import local.MountainsDao
import local.MountainsEntity.Companion.toMountainsDataEntity
import javax.inject.Inject

class TouristUseCaseImpl @Inject constructor(
    private val apiTouristService: ApiTouristService,
    private val mountainsDao: MountainsDao
    ): TouristUseCase {

    override suspend fun getMountains(): Resource<ArrayList<MountainsListModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val mountainsFromRoom = mountainsDao.getMountains()
                if (mountainsFromRoom.isNotEmpty()) {
                    return@withContext Resource.Success(mapEntityToMountains(mountainsFromRoom))
                }
                val result = apiTouristService.getMountains()
                if (result.isSuccessful) {
                    val touristResponse = result.body()
                    val mountains = touristResponse?.let { mapResponseToMountains(it) }
                    insertRoom(touristResponse)
                    Resource.Success(mountains)
                } else {
                    Resource.Error(result.toString())
                }
            }catch (e: Exception) {
                Resource.Error(e.message)
            }
        }
    }

    override suspend fun getDetailMountain(id: String): Resource<DetailMountainModel> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiTouristService.getDetailMountain(id)
                if (result.isSuccessful) {
                    val detailMountainResponse = result.body()
                    val detailMountain = detailMountainResponse?.let { mapResponseToDetailMountain(it) }
                    Resource.Success(detailMountain)
                } else {
                    Resource.Error(result.toString())
                }
            }catch (e: Exception) {
                Resource.Error(e.message)
            }
        }
    }

    private fun insertRoom(mountains: ArrayList<MountainsResponseItem>?) {
        val mountainsInsert = mountains?.filter {
            it.mountain_img.contains("https://")
        }?.map {
            it.toMountainsDataEntity()
        }
        mountainsInsert?.let { mountainsDao.insertMountains(it) }
    }
}