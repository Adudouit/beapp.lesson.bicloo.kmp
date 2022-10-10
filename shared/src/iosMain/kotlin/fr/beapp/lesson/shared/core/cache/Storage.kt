package fr.beapp.lesson.shared.core.cache

import platform.Foundation.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object IosCache {
	var cacheDirectory: NSString

	init {
		val paths = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, true)
		val path = "fr.beapp.lesson.shared.cacheWS"
		val fileManager: NSFileManager = NSFileManager.defaultManager

		@Suppress("CAST_NEVER_SUCCEEDS")
		cacheDirectory = (paths[0] as NSString).stringByAppendingPathComponent(path) as NSString

		if (!fileManager.fileExistsAtPath(path)) {
			fileManager.createDirectoryAtPath(cacheDirectory.toString(), true, null, null)
		}

	}
}


actual class Storage {

	actual fun write(key: String, value: String) {
		val cacheDirPath = IosCache.cacheDirectory.stringByAppendingPathComponent(key)

		@Suppress("CAST_NEVER_SUCCEEDS")
		val value_ = value as NSString

		value_.writeToFile(
			cacheDirPath,
			atomically = true,
			encoding = NSUTF8StringEncoding,
			error = null
		)

	}

	actual fun read(key: String): String? {
		val cacheDirPath = IosCache.cacheDirectory.stringByAppendingPathComponent(key)
		return NSString.stringWithContentsOfFile(
			path = cacheDirPath,
			encoding = NSUTF8StringEncoding,
			error = null
		)
	}

}