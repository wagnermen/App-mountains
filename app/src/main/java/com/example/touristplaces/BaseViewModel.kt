package com.example.touristplaces

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    private val _isViewLoading= MutableLiveData<Boolean>()
    val isViewLoading: MutableLiveData<Boolean> = _isViewLoading

}