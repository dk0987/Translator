package com.example.translator.presentation.home

import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.util.LanguageList
import com.example.translator.util.LanguageList.languageList
import com.example.translator.util.LanguageMapper
import com.example.translator.util.TextFieldState
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val languageMapper: LanguageMapper
) : ViewModel(){

    private val _homeState = MutableStateFlow(HomeScreenState())
    val homeState = _homeState.asStateFlow()

    private val _fromTextState = MutableStateFlow(TextFieldState())
    val fromTextState = _fromTextState.asStateFlow()

    fun onEvent(event: HomeScreenEvent ){
        when(event){
            is HomeScreenEvent.FromText -> {
                _fromTextState.value = fromTextState.value.copy(
                    value = event.data
                )
            }
            is HomeScreenEvent.Process -> {
                viewModelScope.launch {
                    translate(
                        fromLanguage = homeState.value.fromLanguage,
                        toLanguage = homeState.value.toLanguage
                    )
                }

            }
            is HomeScreenEvent.ChangeToLanguage -> {
                _homeState.value = homeState.value.copy(
                    toLanguage = languageList[event.index ],
                    toText = ""
                )

            }
        }
    }

    private fun translate(fromLanguage : String, toLanguage : String){



        _homeState.value = homeState.value.copy(
            error = null,
            loading = true,
            toText = ""
        )
        val option = TranslatorOptions.Builder()
            .setSourceLanguage(languageMapper(fromLanguage))
            .setTargetLanguage(languageMapper(toLanguage))
            .build()
        val translator = Translation.getClient(option)
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()


        translator
            .downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                if (fromTextState.value.value.isNotBlank()){
                    translator.translate(fromTextState.value.value)
                        .addOnSuccessListener {translatedText ->
                            _homeState.value = homeState.value.copy(
                                toText = translatedText,
                                loading = false
                            )
                        }
                        .addOnFailureListener{
                            _homeState.value = homeState.value.copy(
                                error = "Something went wrong",
                                loading = false
                            )
                        }
                }
                else{
                    _homeState.value = homeState.value.copy(
                        error = "Input is necessary",
                        loading = false
                    )
                }

            }
            .addOnFailureListener {
                _homeState.value = homeState.value.copy(
                    error = "Something went wrong",
                    loading = false
                )
            }


    }

}