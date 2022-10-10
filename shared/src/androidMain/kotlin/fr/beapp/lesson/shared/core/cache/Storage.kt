package fr.beapp.lesson.shared.core.cache

import java.io.File
import java.io.IOException
import java.nio.charset.Charset

actual class Storage {

	companion object {
		private lateinit var storageDir: File
		fun init(cacheDir: File) {
			if (!cacheDir.exists() && !cacheDir.mkdirs()) {
				throw RuntimeException("Couldn't create askinfo folder for cache " + cacheDir.absolutePath)
			}
			storageDir = cacheDir
		}
	}

	actual fun write(key: String, value: String) {
		try {
			File(storageDir, key)
				.outputStream()
				.use { it.write(value.toByteArray(Charset.defaultCharset())) }
		} catch (e: IOException) {
			println("Fail to write in storage $e")
		}
	}

	actual fun read(key: String): String? {
		return try {
			File(storageDir, key)
				.inputStream()
				.use { it.readBytes().decodeToString() }
		} catch (e: IOException) {
			println("Fail to read in storage $e")
			null
		}	}


}