package fr.beapp.lesson.bicloo.core.rest

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JCDecauxService {

    @GET("contracts")
    suspend fun getContracts(): List<ContractDto>

    @GET("stations")
    suspend fun getAllStationsOfCity(@Query("contract") city: String): List<StationDTO>

    @GET("stations/{number}")
    suspend fun getStationOfCityByNumber(@Query("contract") city: String, @Path("number") number: Int): StationDTO
}