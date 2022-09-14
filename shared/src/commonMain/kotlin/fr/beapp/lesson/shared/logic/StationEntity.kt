package fr.beapp.lesson.shared.logic

import fr.beapp.lesson.bicloo.core.rest.StationDTO

data class StationEntity(
    val number: Number,
    val name: String,
    val longitude:Double,
    val latitude: Double,
    val state:StationDTO.State,
    val address: String,
    val contractName: String
)