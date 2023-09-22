package com.singaludra.weatherapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrentWeatherDetailRow(title1: String, value1: String, title2: String, value2: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CurrentWeatherDetailCard(title = title1, value = value1)
        CurrentWeatherDetailCard(title = title2, value = value2)
    }
}

@Composable
private fun CurrentWeatherDetailCard(title: String, value: String) {
    Card(
        modifier = Modifier.size(180.dp),
        shape = MaterialTheme.shapes.small,
        border = null
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 8.dp), Alignment.TopStart) {
            Text(text = title, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp))
        }
        Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 36.sp)
            )
        }
    }
}