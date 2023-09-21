package com.example.translator.presentation.home

sealed class HomeScreenEvent() {
    data class FromText(val data : String) : HomeScreenEvent()
    data class ChangeToLanguage(val index : Int) : HomeScreenEvent()
    object Process : HomeScreenEvent()
}