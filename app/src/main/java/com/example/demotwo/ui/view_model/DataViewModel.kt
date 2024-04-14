package com.example.demotwo.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demotwo.model.ResponseEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataViewModel(private val repository: Repository) : ViewModel(){

    private val _uiStateFlow = MutableStateFlow<ResponseEvent>(ResponseEvent.Loader)
    val uiStateFlow: StateFlow<ResponseEvent> = _uiStateFlow
    fun getHomePageData(){
        viewModelScope.launch {
            val response = repository.getHomePageData()
            when (response?.isSuccessful == true) {
                true -> {
                    val responseBody = response?.body()
                    _uiStateFlow.emit(ResponseEvent.ResponseEventData(responseBody!!))
                }
                false -> {
                    _uiStateFlow.emit(
                        ResponseEvent.Failure(
                            response?.message() ?: ""
                        )
                    )
                }
            }
            _uiStateFlow.emit(ResponseEvent.RemoveLoader)
        }
    }

    fun getFoodInfoData(){
        viewModelScope.launch {
            val response = repository.getFoodInfoData()
            when (response?.isSuccessful == true) {
                true -> {
                    val responseBody = response?.body()
                    _uiStateFlow.emit(ResponseEvent.ResponseEventData(responseBody!!))
                }
                false -> {
                    _uiStateFlow.emit(
                        ResponseEvent.Failure(
                            response?.message() ?: ""
                        )
                    )
                }
            }
            _uiStateFlow.emit(ResponseEvent.RemoveLoader)
        }
    }
}