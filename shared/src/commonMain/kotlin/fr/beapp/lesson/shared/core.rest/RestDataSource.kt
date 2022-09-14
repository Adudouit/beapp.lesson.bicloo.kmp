package fr.beapp.lesson.shared.core.rest

import fr.beapp.lesson.bicloo.core.rest.StationDTO
import fr.beapp.lesson.shared.logic.ContractEntity
import fr.beapp.lesson.shared.logic.StationEntity
import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
val json by lazy {
	Json {
		isLenient = true
		ignoreUnknownKeys = true
	}
}

class HttpLogger : Logger {
	override fun log(message: String) {
		println("[HTTP] $message")
	}
}

object RestDataSource {

	private val client = HttpClient {
		install(Logging) {
			logger = HttpLogger()
			level = LogLevel.ALL
		}

	}
	private const val rootUrl = "api.jcdecaux.com"

	suspend fun getContracts(): List<ContractEntity> {
		val responseData = get("contracts", serializer = ListSerializer(ContractDto.serializer()))
		return responseData.map { it.toEntity() }
	}

	suspend fun getStationsOfCity(city: String): List<StationEntity> {
		val responseData = get(
			"stations",
			params = mapOf("contract" to city),
			serializer = ListSerializer(StationDTO.serializer())
		)
		return responseData.map { it.toEntity() }
	}

	private suspend fun <T : Any> get(
		path: String,
		params: Map<String, String?> = emptyMap(),
		serializer: KSerializer<T>
	): T {
		val response = client.get {
			url {
				protocol = URLProtocol.HTTPS
				host = rootUrl
				path("vls/v3")
				appendEncodedPathSegments(path)
				params.forEach { param -> param.value?.let { parameter(param.key, it) } }
				parameters.append("apiKey", "d94ebd2490a22f90451dbc3eb183b341acd0355d")

			}
		}
		return json.decodeFromString(serializer, response.bodyAsText())
	}
}