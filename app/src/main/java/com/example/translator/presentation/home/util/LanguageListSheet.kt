package com.example.translator.presentation.home.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.translator.presentation.home.HomeScreenEvent
import com.example.translator.util.LanguageList
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun LanguageListSheet(
    selectedLanguage : String,
    onLanguageClick : (Int) -> Unit ,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier
            .fillMaxWidth(0.95f)
            .fillMaxHeight(0.75f)
            .clip(RoundedCornerShape(20.dp))
            .shadow(5.dp)
            .background(Color(0xfffdfdfd))
        ){
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 20.dp , vertical = 5.dp)
            ){
                stickyHeader {
                    Text(
                        text = "Language",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xfffdfdfd))
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(LanguageList.languageList.size) { language ->
                    LanguageListSheetItem(
                        selected = LanguageList.languageList[language] == selectedLanguage,
                        language = language,
                        onLanguageClick = onLanguageClick
                    )
                }
            }
        }
    }
}