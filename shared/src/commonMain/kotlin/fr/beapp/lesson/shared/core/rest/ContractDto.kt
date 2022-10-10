package fr.beapp.lesson.shared.core.rest

import fr.beapp.lesson.shared.logic.ContractEntity
import kotlinx.serialization.Serializable

@Serializable
class ContractDto {
	var cities: List<String>? = null
	var commercial_name: String? = null
	var country_code: String? = null
	var name: String = ""

	fun toEntity(): ContractEntity = ContractEntity(cities, commercial_name ?: "", country_code ?: "", name)
}