package com.github.wirye.musicBrainzkt

import com.github.wirye.musicBrainzkt.api.ArtApi
import com.github.wirye.musicBrainzkt.api.BrowseApi
import com.github.wirye.musicBrainzkt.api.LookupApi
import com.github.wirye.musicBrainzkt.api.SearchApi
import io.ktor.client.HttpClient
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeSource

internal object MusicBrainzRateLimiter {
    private val minInterval = 5000.milliseconds
    private val mutex = Mutex()
    private var nextSlot = TimeSource.Monotonic.markNow()

    suspend fun acquire() = mutex.withLock {
        val wait = -nextSlot.elapsedNow()
        if (wait.isPositive()) delay(wait)
        nextSlot = TimeSource.Monotonic.markNow() + minInterval
    }
}

class MusicBrainzClient(
    customHttpClient: HttpClient? = null
) {
    private val httpClient: HttpClient = customHttpClient ?: HttpClient {
        install(UserAgent) {
            agent =
                "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36"
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    val search: SearchApi = SearchApi(httpClient)
    val lookup: LookupApi = LookupApi(httpClient)
    val browse: BrowseApi = BrowseApi(httpClient)
    val art: ArtApi = ArtApi(httpClient)
}