package com.example.learningcleanarchitecture.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.WeatherResponse
import com.example.domain.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext applicationContext: Context,
    val getWeatherUseCase: GetWeatherUseCase
):ViewModel() {
    private val _weatherResponse = MutableStateFlow(WeatherResponse())

    /**
     * 天気の取得APIの通知用state.
     */
    val weatherResponse: StateFlow<WeatherResponse> = _weatherResponse


    fun getWeather() {
        viewModelScope.launch {
            getWeatherUseCase().catch {
                Log.e("HomeViewModel","getWeather()",it)
            }.take(1).collect { weatherResponse->
                this@HomeViewModel._weatherResponse.value = weatherResponse
            }
        }
    }
}

//sealed interface GetWeatherState {
//    data object Default:GetWeatherState
//    data class Success(val weatherResponse: WeatherResponse):GetWeatherState
//    data object Error:GetWeatherState
//}
