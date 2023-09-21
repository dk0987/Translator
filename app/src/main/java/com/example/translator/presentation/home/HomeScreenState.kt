package com.example.translator.presentation.home

import com.example.translator.util.LanguageList
import com.example.translator.util.LanguageList.languageList

data class HomeScreenState(
    val toText : String = "" ,
    val loading : Boolean = false ,
    val error : String? = null,
    val fromLanguage : String = languageList[0],
    val toLanguage : String = languageList[1],
)
