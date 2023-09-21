package com.example.translator.util

import com.example.translator.util.LanguageList.languageList
import com.google.mlkit.nl.translate.TranslateLanguage


class LanguageMapper {

    operator fun invoke(language : String) : String{
        when(language){
            languageList[0] -> { return TranslateLanguage.ENGLISH }
            languageList[1] -> { return TranslateLanguage.HINDI }
            languageList[2] -> { return TranslateLanguage.JAPANESE }
            languageList[3] -> { return TranslateLanguage.CHINESE }
            languageList[4] -> { return TranslateLanguage.ITALIAN }
            languageList[5] -> { return TranslateLanguage.AFRIKAANS }
            languageList[6] -> { return TranslateLanguage.ARABIC }
            languageList[7] -> { return TranslateLanguage.BELARUSIAN }
            languageList[8] -> { return TranslateLanguage.BULGARIAN}
            languageList[9] -> { return TranslateLanguage.BENGALI }
            languageList[10] -> { return TranslateLanguage.CATALAN }
            languageList[11] -> { return TranslateLanguage.KOREAN }
            languageList[12] -> { return TranslateLanguage.KANNADA }
            languageList[13] -> { return TranslateLanguage.DUTCH}
            languageList[14] -> { return TranslateLanguage.MARATHI }
            languageList[15] -> { return TranslateLanguage.TAMIL}
            languageList[16] -> { return TranslateLanguage.TELUGU }
            languageList[17] -> { return TranslateLanguage.VIETNAMESE }
            languageList[18] -> { return TranslateLanguage.PORTUGUESE }
            languageList[19] -> { return TranslateLanguage.ROMANIAN}
            languageList[20] -> { return TranslateLanguage.RUSSIAN}
            languageList[21] -> { return TranslateLanguage.DANISH}
            languageList[22] -> { return TranslateLanguage.GERMAN }
            languageList[23] -> { return TranslateLanguage.GREEK }
            languageList[24] -> { return TranslateLanguage.SPANISH }
            languageList[25] -> { return TranslateLanguage.IRISH}
            languageList[26] -> { return TranslateLanguage.FRENCH }
            else -> { return ""}
        }
    }
}