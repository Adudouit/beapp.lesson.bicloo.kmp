package fr.beapp.lesson.shared.core.cache


expect class Storage() {
	fun write(key: String, value: String)
	fun read(key: String): String?
}