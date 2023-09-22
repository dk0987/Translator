package com.example.translator.presentation.home.util

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.translator.util.LanguageList

@Composable
fun LanguageListSheetItem(
    selected : Boolean ,
    language : Int,
    onLanguageClick : (Int) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(
                if (selected) Color(
                    0xff4997d0
                ).copy(alpha = 0.25f) else Color(0xfff0f0f0)
            )
            .border(
                1.dp,
                if (selected) Color(
                    0xff4997d0
                ).copy(alpha = 0.25f) else Color(0xfff0f0f0),
                RoundedCornerShape(4.dp)
            )
            .clickable {
                onLanguageClick(language)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = LanguageList.languageList[language].uppercase(),
            fontSize = 14.sp,
            letterSpacing = 1.sp,
            color = Color.Black,
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}
