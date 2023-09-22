package com.singaludra.weatherapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.singaludra.weatherapp.Greeting
import com.singaludra.weatherapp.R
import com.singaludra.weatherapp.ui.theme.WeatherAppTheme
import kotlin.text.Typography.degree

@Composable
fun CityWeatherCard(
    modifier: Modifier = Modifier,
    degree: String,
    city: String,
    country: String,
    description: String,
    weatherImage: Int,
    onClick: () -> Unit = {},
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 16.dp)
        ){
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(2f), horizontalAlignment = Alignment.Start) {
                Text(text = "${city}, $country")
            }
            Icon(
                painterResource(weatherImage),
                contentDescription = description,
                modifier = Modifier.size(24.dp)
            )
            Text(modifier = Modifier.padding(horizontal = 8.dp) ,text = degree)
        }
        Divider()
    }


}

@Preview(showBackground = true)
@Composable
fun WeatherCard() {
    WeatherAppTheme {
        CityWeatherCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            degree = "90",
            city = "Jakarta",
            country = "ID",
            description = "asdas",
            weatherImage = R.drawable.ic_clear_sky_day_48,
            onClick = { }
        )
    }
}