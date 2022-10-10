package fr.beapp.lesson.shared.logic

import kotlinx.serialization.Serializable

@Serializable
data class ContractEntity(
	val cities: List<String>?,
	val commercialName: String,
	val countryCode: String,
	val name: String
) {
	fun match(query: String): Boolean {
        val queryLowercased = query.lowercase()
		println("[SEARCH] commercialName: $commercialName")
		val matched = name.lowercase().contains(queryLowercased) ||
				commercialName.lowercase().contains(queryLowercased) ||
				cities?.contains(queryLowercased) == true
		if (matched) {
			println("[SEARCH] GOT a match $this")
		}
		return matched
	}
}