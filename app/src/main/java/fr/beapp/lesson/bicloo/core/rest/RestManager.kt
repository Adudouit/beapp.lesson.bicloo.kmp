package fr.beapp.lesson.bicloo.core.rest

import fr.beapp.lesson.bicloo.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestManager {

    private val service = Retrofit.Builder()
        .baseUrl(BuildConfig.JCDECAUX_URL)
        .client(OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor()).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(JCDecauxService::class.java)

    suspend fun getContracts() = service.getContracts().map { it.toEntity() }

    suspend fun getStationsOfCity(cityName: String) = service.getAllStationsOfCity(cityName).map { it.toEntity() }

    suspend fun getStationOfCityByNumber(cityName: String, number: Int) = service.getStationOfCityByNumber(cityName, number).toEntity()
}