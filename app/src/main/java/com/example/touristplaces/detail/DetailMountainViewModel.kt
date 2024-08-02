package com.example.touristplaces.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DetailMountainModel
import com.example.domain.tourist.Resource
import com.example.domain.tourist.TouristUseCaseImpl
import com.example.touristplaces.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMountainViewModel @Inject constructor(private val touristUseCaseImpl: TouristUseCaseImpl) : BaseViewModel(){

    private val _detailMountain = MutableLiveData<DetailMountainModel?>()
    val detailMountain: LiveData<DetailMountainModel?> = _detailMountain

    val error = MutableLiveData<String?>()

    fun getDetailMountain(id: String) {
        viewModelScope.launch {
            isViewLoading.value = true
            when (val result = touristUseCaseImpl.getDetailMountain(id)) {
                is Resource.Success -> {
                    _detailMountain.value = result.data
                    isViewLoading.value = false
                }
                is Resource.Error -> {
                    error.value = result.message
                    isViewLoading.value = false
                }
            }
        }
    }
}