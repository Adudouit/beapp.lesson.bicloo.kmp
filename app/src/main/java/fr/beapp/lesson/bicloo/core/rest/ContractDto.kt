package fr.beapp.lesson.bicloo.core.rest

import fr.beapp.lesson.bicloo.logic.ContractEntity

class ContractDto {

    var cities: List<String>? = null
    var commercial_name: String? = null
    var country_code: String? = null
    var name: String = ""

    fun toEntity(): ContractEntity = ContractEntity(cities, commercial_name?:"", country_code?:"", name)
}