package com.example.translator.presentation.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.translator.presentation.home.HomeScreenEvent

@ExperimentalMaterial3Api
@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    value : String,
    onValueChange : (String) -> Unit ,
    readOnly : Boolean = false,
    onClick : () -> Unit = {},
    placeholder : String = "",
    enabled : Boolean = true,
    maxLines : Int = Int.MAX_VALUE,
) {
    Box(modifier = Modifier.clickable { onClick() }, contentAlignment = Alignment.Center){
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                placeholderColor = Color.LightGray ,
                focusedLabelColor = Color(0xff4997d0),
                unfocusedLabelColor = Color.LightGray,
                focusedIndicatorColor = Color(0xff4997d0),
                containerColor = Color(0xfffafafa),
                cursorColor = Color(0xff4997d0),
                disabledTextColor = Color.Black
                ),
            maxLines = maxLines,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 2.sp,
                )
            },
            modifier = modifier,
            shape = TextFieldDefaults.filledShape,
            readOnly = readOnly,
            enabled = enabled
        )
    }

}