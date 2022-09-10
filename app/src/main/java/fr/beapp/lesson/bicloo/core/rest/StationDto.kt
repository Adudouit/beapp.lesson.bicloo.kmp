package fr.beapp.lesson.bicloo.core.rest

import fr.beapp.lesson.bicloo.logic.StationEntity

class StationDTO {
    var number: Int = 0
    var name: String = ""
    var position: Position = Position(0.0, 0.0)
    var state: State = State.OPEN
    var address: String = ""
    var contractName: String = ""

    fun toEntity(): StationEntity = StationEntity(number, name, position.longitude, position.latitude, state, address, contractName)

    data class Position(val latitude: Double, val longitude: Double)
    enum class State(val raw: String) {
        OPEN("OPEN"),
        CLOSED("CLOSED")
    }
}


