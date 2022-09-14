package fr.beapp.lesson.shared.logic

data class ContractEntity(
    val cities: List<String>?,
    val commercialName: String,
    val countryCode: String,
    val name: String
)