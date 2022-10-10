package fr.beapp.lesson.shared.core.rest

import fr.beapp.lesson.shared.core.cache.Storage
import fr.beapp.lesson.shared.logic.ContractEntity
import fr.beapp.lesson.shared.logic.StationEntity
import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
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

	private val storage = Storage()

	private val client = HttpClient {
		install(Logging) {
			logger = HttpLogger()
			level = LogLevel.ALL
		}

	}
	private const val rootUrl = "api.jcdecaux.com"

	suspend fun getContracts(fromCache: Boolean = false): List<ContractEntity> {
		val key = "contracts"
		val contractStorage = storage.read(key)

		return if (fromCache && contractStorage != null) {
			json.decodeFromString(ListSerializer(ContractEntity.serializer()), contractStorage)
		} else {
			val responseData = get("contracts", serializer = ListSerializer(ContractDto.serializer()))
			val contracts = responseData.map { it.toEntity() }
			val contractsEncodeToString = json.encodeToString(ListSerializer(ContractEntity.serializer()), contracts)
			storage.write(key, contractsEncodeToString)
			contracts
		}
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