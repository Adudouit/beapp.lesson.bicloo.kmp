package fr.beapp.lesson.bicloo.logic

import fr.beapp.lesson.bicloo.core.rest.StationDTO

data class StationEntity(
    val number: Number,
    val name: String,
    val longitude:Number,
    val latitude: Number,
    val state:StationDTO.State,
    val address: String,
    val contractName: String
)