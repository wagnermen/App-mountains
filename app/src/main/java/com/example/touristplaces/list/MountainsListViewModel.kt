package com.example.touristplaces.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MountainsListModel
import com.example.domain.tourist.Resource
import com.example.domain.tourist.TouristUseCaseImpl
import com.example.touristplaces.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MountainsListViewModel @Inject constructor(private val touristUseCaseImpl: TouristUseCaseImpl) : BaseViewModel(){

    private val _mountains = MutableLiveData<List<MountainsListModel>?>()
    val mountains: LiveData<List<MountainsListModel>?> = _mountains

    val error = MutableLiveData<String?>()

    fun getMountains() {
        viewModelScope.launch {
            isViewLoading.value = true
            when (val result = touristUseCaseImpl.getMountains()) {
                is Resource.Success -> {
                    _mountains.value = result.data
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