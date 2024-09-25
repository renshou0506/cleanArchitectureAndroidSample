package com.example.learningcleanarchitecture.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.learningcleanarchitecture.ui.HomeViewModel
import com.example.learningcleanarchitecture.ui.theme.background

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    snackBar: (String) -> Unit
) {
    val weatherResponse by viewModel.weatherResponse.collectAsStateWithLifecycle()

    Column(
        Modifier
            .fillMaxSize()
            .background(background)
            .padding(40.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Test Screen")

            weatherResponse.title?.let { Text(text = it) }
            val telop = if (weatherResponse.forecasts.isNotEmpty()){
                weatherResponse.forecasts.first().telop
            } else {
                ""
            }
            Text(text = telop)

            Text(
                text = "天気取得",
                modifier = Modifier.clickable(onClick = { viewModel.getWeather() })
            )
        }
    }
}