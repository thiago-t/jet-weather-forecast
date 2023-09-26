@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetweatherforecast.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDecimals
import com.example.jetweatherforecast.widgets.HumidityWindPressureRow
import com.example.jetweatherforecast.widgets.SunsetSunriseRow
import com.example.jetweatherforecast.widgets.WeatherAppBar
import com.example.jetweatherforecast.widgets.WeatherDetailRow
import com.example.jetweatherforecast.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    city: String?
) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(isLoading = true)
    ) {
        value = viewModel.getWeatherData(city = city.orEmpty())
    }.value

    if (weatherData.isLoading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherData.data, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather?, navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "${weather?.city?.name}, ${weather?.city?.country}",
                navController = navController,
                elevation = 5.dp,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            ) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MainContent(weather = weather)
        }
    }
}

@Composable
fun MainContent(weather: Weather?) {
    val imageUrl =
        "https://openweathermap.org/img/wn/${weather?.list?.first()?.weather?.first()?.icon}.png"

    Text(
        text = formatDate(weather?.list?.first()?.dt ?: 0),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.secondary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(6.dp)
    )
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl)

                Text(
                    text = formatDecimals(weather?.list?.first()?.temp?.day ?: 0.0) + "°",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = weather?.list?.first()?.weather?.first()?.main.orEmpty(),
                    fontStyle = FontStyle.Italic
                )
            }
        }
        weather?.run {
            HumidityWindPressureRow(weather = this)
        }

        Divider()

        weather?.run {
            SunsetSunriseRow(this)
        }

        Text(
            text = "This Week", style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 6.dp),
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0XFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)) {
                items(items = weather?.list.orEmpty()) { item ->
                    WeatherDetailRow(item)
                }
            }
        }
    }
}


