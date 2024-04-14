package com.example.demotwo.model

sealed class ResponseEvent{

    object NoData : ResponseEvent()

    object Loader : ResponseEvent()

    object RemoveLoader : ResponseEvent()

    object ClearSection : ResponseEvent()

    data class ResponseEventData(val data: Any) : ResponseEvent()

    data class Failure(val msg: String) : ResponseEvent()

    data class Success(val msg: String,val status: String) : ResponseEvent()
}