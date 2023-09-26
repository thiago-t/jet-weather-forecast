package com.example.jetweatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherJson
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.formatDecimals

@Composable
fun WeatherDetailRow(item: WeatherJson) {
    val imageUrl = "https://openweathermap.org/img/wn/${item.weather.first().icon}.png"

    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDate(item.dt)
                    .split(",")
                    .first(),
                modifier = Modifier.padding(5.dp)
            )
            WeatherStateImage(imageUrl = imageUrl)

            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400),
            ) {
                Text(
                    text = item.weather.first().description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(formatDecimals(item.temp.max) + "°")
                }

                withStyle(style = SpanStyle(color = Color.LightGray)) {
                    append(formatDecimals(item.temp.min) + "")
                }
            })
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: Weather) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_humidity),
                contentDescription = "Humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.list.first().humidity}%")
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pressure),
                contentDescription = "Pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.list.first().pressure} psi")
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wind),
                contentDescription = "Wind power icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.list.first().humidity} mph")
        }
    }
}

@Composable
fun SunsetSunriseRow(weather: Weather) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_sun),
                contentDescription = "Sun icon"
            )
            Text(text = formatDateTime(weather.list.first().sunrise))
        }
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_moon),
                contentDescription = "Moon icon"
            )
            Text(text = formatDateTime(weather.list.first().sunset))
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = "Icon image",
        modifier = Modifier.size(80.dp)
    )
}
