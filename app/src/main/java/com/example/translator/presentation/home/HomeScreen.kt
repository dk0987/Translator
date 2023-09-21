package com.example.translator.presentation.home

import android.annotation.SuppressLint
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeechService
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.translator.R
import com.example.translator.presentation.util.StandardTextField
import com.example.translator.util.LanguageList
import com.example.translator.util.LanguageList.languageList
import com.example.translator.util.Quotes
import com.example.translator.util.Quotes.afternoon
import com.example.translator.util.Quotes.evening
import com.example.translator.util.Quotes.morning
import com.example.translator.util.Quotes.night
import com.example.translator.util.UserGreetings
import com.example.translator.util.UserGreetings.userGreeting
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@androidx.compose.runtime.Composable
fun Home(
    padding : PaddingValues,
    screenHeight : Dp,
    snackBar : SnackbarHostState,
    viewModel : HomeScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state = viewModel.homeState.collectAsState()
    val fromText = viewModel.fromTextState.collectAsState()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val currentTime = remember { Calendar.getInstance() }
    val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)

    val greeting = when{
        currentHour < 12 -> morning[Random.nextInt(0 , 6)]
        currentHour <  17 -> afternoon[Random.nextInt(0 ,6)]
        currentHour < 20 -> evening[Random.nextInt(0 , 6)]
        else -> night[Random.nextInt(0, 6)]
    }

    val iconForHour = when{
        currentHour < 12 -> R.drawable.sunrise
        currentHour <  17 -> R.drawable.afternoon
        currentHour < 20 -> R.drawable.night
        else -> R.drawable.sleeping
    }

    val textToSpeech = remember { TextToSpeech(context){ status ->
        when(status){
            TextToSpeech.SUCCESS -> {

            }
            else -> {
                scope.launch {
                    snackBar.showSnackbar("Oops!! Something went wrong .")
                }
            }
        }
    } }


    LaunchedEffect(key1 = true ){
        state.value.error?.let { error ->
            snackBar.showSnackbar(error)
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Box(modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.7f)
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

                        items(languageList.size){language ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        if (languageList[language] == state.value.toLanguage) Color(
                                            0xff4997d0
                                        ).copy(alpha = 0.25f) else Color(0xfff0f0f0)
                                    )
                                    .border(
                                        1.dp,
                                        if (languageList[language] == state.value.toLanguage) Color(
                                            0xff4997d0
                                        ).copy(alpha = 0.25f) else Color(0xfff0f0f0),
                                        RoundedCornerShape(4.dp)
                                    )
                                    .clickable {
                                        viewModel.onEvent(HomeScreenEvent.ChangeToLanguage(language))
                                        scope.launch {
                                            modalBottomSheetState.hide()
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = languageList[language].uppercase(),
                                    fontSize = 14.sp,
                                    letterSpacing = 1.sp,
                                    color = Color.Black,
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xfffdfdfd))
                .padding(
                    top = padding.calculateTopPadding(),
                    start = 10.dp,
                    end = 10.dp,
                    bottom = padding.calculateBottomPadding()
                )
        ){
            LazyColumn{

                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween ,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)
                    ) {
                        Column {
                            Text(
                                text = userGreeting[Random.nextInt(0 , 8)],
                                fontWeight = FontWeight.Bold ,
                                fontSize = 20.sp ,
                                color = Color.Black,
                                letterSpacing = 1.sp ,
                            )
                            Text(
                                text = greeting,
                                fontSize = 14.sp ,
                                color = Color.Black,
                                letterSpacing = 1.sp ,
                            )
                        }

                        Image(
                            painter = painterResource(id = iconForHour),
                            contentDescription = "Image" ,
                            modifier = Modifier.size(50.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                    }

                }

                item {
                    Spacer(modifier = Modifier.height(15.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Column {
                            Text(
                                text = "From",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = Color.Black
                            )

                            Text(
                                text = state.value.fromLanguage,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 1.sp,
                                color = Color.Black
                            )
                        }


                        Image(
                            painter = painterResource(id = R.drawable.opposite_arrows),
                            contentDescription = "Opposite Arrows",
                            modifier = Modifier
                                .size(40.dp)
                        )

                        StandardTextField(
                            value = state.value.toLanguage,
                            onValueChange ={

                            } ,
                            modifier = Modifier.fillMaxWidth(0.5f),
                            onClick = {
                                scope.launch {
                                    if (modalBottomSheetState.isVisible) modalBottomSheetState.hide() else modalBottomSheetState.show()
                                }
                            },
                            readOnly = true,
                            maxLines = 1,
                            enabled = false
                        )
                    }

                }

                item {
                    Spacer(modifier = Modifier.height(15.dp))
                    StandardTextField(
                        value = fromText.value.value,
                        onValueChange ={
                            viewModel.onEvent(HomeScreenEvent.FromText(it))
                        } ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = (screenHeight / (100 / 30))),
                        placeholder = "What you wanna listen ?"
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(15.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shadow(10.dp)
                        .background(Color(0xff4997d0))
                        .clickable {
                            scope.launch {
                                viewModel.onEvent(HomeScreenEvent.Process)

                                viewModel.homeState.collectLatest {
                                    textToSpeech.speak(
                                        it.toText,
                                        TextToSpeech.QUEUE_FLUSH,
                                        null,
                                        null
                                    )
                                }
                            }
                        },
                        contentAlignment = Alignment.Center
                    ){
                        if (state.value.loading){
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Interpreting...",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp,
                                    color = Color(0xfffdfdfd)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                CircularProgressIndicator(color = Color(0xfffdfdfd) , modifier = Modifier.size(20.dp))
                            }
                        }
                        else{
                            Text(
                                text = "Interpret",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = Color(0xfffdfdfd)
                            )
                        }

                    }
                }

                item {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Result:",
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(15.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .shadow(5.dp)
                        .background(Color(0xfff0f0f0))
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                    ){
                        if (state.value.toText.isNotBlank()){
                            SelectionContainer{
                                Text(
                                    text = state.value.toText,
                                    fontSize = 14.sp,
                                    letterSpacing = 1.sp,
                                    color = Color.Black,
                                    textAlign = TextAlign.Justify,
                                )
                            }

                        }
                        else{
                            Text(
                                text = "Input is required for result",
                                fontSize = 14.sp,
                                letterSpacing = 1.sp,
                                color = Color.LightGray,
                                textAlign = TextAlign.Justify,
                                fontStyle = FontStyle.Italic
                            )
                        }

                    }
                }

            }
        }
    }


}