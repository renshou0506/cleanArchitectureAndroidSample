package com.example.domain

import com.example.data.repository.WeatherRepository
import com.example.data.repository.WeatherResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<WeatherResponse> =
        weatherRepository.weather()

}