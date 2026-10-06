package com.github.wirye.musicBrainzkt

import com.github.wirye.musicBrainzkt.model.AreaInclude
import com.github.wirye.musicBrainzkt.model.ArtistInclude
import com.github.wirye.musicBrainzkt.model.EventInclude
import com.github.wirye.musicBrainzkt.model.InstrumentInclude
import com.github.wirye.musicBrainzkt.model.LabelInclude
import com.github.wirye.musicBrainzkt.model.PlaceInclude
import com.github.wirye.musicBrainzkt.model.RecordingInclude
import com.github.wirye.musicBrainzkt.model.RelationInclude
import com.github.wirye.musicBrainzkt.model.ReleaseGroupInclude
import com.github.wirye.musicBrainzkt.model.ReleaseInclude
import com.github.wirye.musicBrainzkt.model.SeriesInclude
import com.github.wirye.musicBrainzkt.model.WorkInclude
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class RealApiIntegrationTest {
    private val client = MusicBrainzClient()

    @Test
    fun `area search test`(): Unit = runTest {
        val result = client.search.searchAreas("Chi", limit = 5)

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `artist search test`(): Unit = runTest {
        val result = client.search.searchArtists("natori", limit = 5)

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `release-group search test`(): Unit = runTest {
        val result = client.search.searchReleaseGroups("Test", limit = 5)

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `work search test`(): Unit = runTest {
        val result = client.search.searchWorks("Test", limit = 5)

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `label search test`(): Unit = runTest {
        val result = client.search.searchLabels("Sony", limit = 5)

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `place search test`(): Unit = runTest {
        val result = client.search.searchPlaces("Club", limit = 5)

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `event search test`(): Unit = runTest {
        val result = client.search.searchEvents("Festival", limit = 5)

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `series search test`(): Unit = runTest {
        val result = client.search.searchSeries("Live", limit = 5)

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `instrument search test`(): Unit = runTest {
        val result = client.search.searchInstruments("Guitar", limit = 5)

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `recording search test`(): Unit = runTest {
        val result = client.search.searchRecordings("serenade", limit = 5)

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `release search test`(): Unit = runTest {
        val result = client.search.searchReleases("Serenade", artist = "natori", limit = 5)

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `recording lookup test`(): Unit = runTest {
        val result = client.lookup.getRecording(
            "d9504e13-d5af-465d-aae8-02715c098d6c",
            include = RecordingInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `release lookup test`(): Unit = runTest {
        val result = client.lookup.getRelease(
            "93d67f7b-0cc9-4c96-9bf5-5e1567870d3f",
            include = ReleaseInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `area lookup test`(): Unit = runTest {
        val result = client.lookup.getArea(
            "51d34c28-61bf-3d21-849f-7492672a9d44",
            include = AreaInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `artist lookup test`(): Unit = runTest {
        val result = client.lookup.getArtist(
            "cad9e169-893d-4432-a23f-3aa16beb9f0e",
            include = ArtistInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `release-group lookup test`(): Unit = runTest {
        val result = client.lookup.getReleaseGroup(
            "cbd45f37-9560-3d16-851b-a0c293aecb7c",
            include = ReleaseGroupInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `work lookup test`(): Unit = runTest {
        val result = client.lookup.getWork(
            "ca37f64d-7fd7-4aee-9257-7ba739353498",
            include = WorkInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `label lookup test`(): Unit = runTest {
        val result = client.lookup.getLabel(
            "9e6b4d7f-4958-4db7-8504-d89e315836af",
            include = LabelInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `place lookup test`(): Unit = runTest {
        val result = client.lookup.getPlace(
            "37d1207a-1735-46a8-ad23-f8ea1b1c63c4",
            include = PlaceInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `event lookup test`(): Unit = runTest {
        val result = client.lookup.getEvent(
            "f6ede675-887d-41a8-9468-2af872f67ecf",
            include = EventInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `series lookup test`(): Unit = runTest {
        val result = client.lookup.getSeries(
            "c4fe2a59-043f-43e2-95e4-e9ae259c0ca4",
            include = SeriesInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `instrument lookup test`(): Unit = runTest {
        val result = client.lookup.getInstrument(
            "63021302-86cd-4aee-80df-2270d54f4978",
            include = InstrumentInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    /* Наверное оно работает, но я честно пытался найти трек с жанрами */
    @Test
    fun `genre lookup test`(): Unit = runTest {
        val result = client.lookup.getGenre("d9504e13-d5af-465d-aae8-02715c098d6c")

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get url by mbid test`(): Unit = runTest {
        val result = client.lookup.getUrl(
            "dd9a5505-e2fa-4c97-ba7b-325b05130afb",
            RelationInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get resource by url test`(): Unit = runTest {
        val result = client.lookup.getResourceByUrl(
            "https://open.spotify.com/track/4EftPHa368qiFkGW9yicby",
            RelationInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get releases by release-group test`(): Unit = runTest {
        val result = client.browse.getReleasesByReleaseGroup(
            "a973e418-4aea-4aef-9d1e-ca9f406d2ef7",
            offset = 0,
            limit = 5,
            include = ReleaseInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get releases by artist test`(): Unit = runTest {
        val result = client.browse.getReleasesByArtist(
            "cad9e169-893d-4432-a23f-3aa16beb9f0e",
            offset = 0,
            limit = 5,
            include = ReleaseInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: ${result.getOrNull()?.items?.size},  $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get release groups by artist test`(): Unit = runTest {
        val result = client.browse.getReleaseGroupsByArtist(
            "cad9e169-893d-4432-a23f-3aa16beb9f0e",
            offset = 0,
            limit = 5,
            include = ReleaseGroupInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get recordings by artist test`(): Unit = runTest {
        val result = client.browse.getRecordingsByArtist(
            "cad9e169-893d-4432-a23f-3aa16beb9f0e",
            offset = 0,
            limit = 5,
            include = RecordingInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get works by artist test`(): Unit = runTest {
        val result = client.browse.getWorksByArtist(
            "cad9e169-893d-4432-a23f-3aa16beb9f0e",
            offset = 0,
            limit = 5,
            include = WorkInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get releases by label test`(): Unit = runTest {
        val result = client.browse.getReleasesByLabel(
            "9e6b4d7f-4958-4db7-8504-d89e315836af",
            offset = 0,
            limit = 1,
            include = ReleaseInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get releases by area test`(): Unit = runTest {
        val result = client.browse.getReleasesByArea(
            "51d34c28-61bf-3d21-849f-7492672a9d44",
            offset = 0,
            limit = 5,
            include = ReleaseInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get artists by area test`(): Unit = runTest {
        val result = client.browse.getArtistsByArea(
            "51d34c28-61bf-3d21-849f-7492672a9d44",
            offset = 0,
            limit = 5,
            include = ArtistInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get labels by area test`(): Unit = runTest {
        val result = client.browse.getLabelsByArea(
            "51d34c28-61bf-3d21-849f-7492672a9d44",
            offset = 0,
            limit = 5,
            include = LabelInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get places by area test`(): Unit = runTest {
        val result = client.browse.getPlacesByArea(
            "51d34c28-61bf-3d21-849f-7492672a9d44",
            offset = 0,
            limit = 5,
            include = PlaceInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get events by place test`(): Unit = runTest {
        val result = client.browse.getEventsByPlace(
            "37d1207a-1735-46a8-ad23-f8ea1b1c63c4",
            offset = 0,
            limit = 5,
            include = EventInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get events by artist test`(): Unit = runTest {
        val result = client.browse.getEventsByArtist(
            "cad9e169-893d-4432-a23f-3aa16beb9f0e",
            offset = 0,
            limit = 5,
            include = EventInclude.entries.toSet()
        )

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get releases by recording test`(): Unit = runTest {
        val result = client.browse.getReleasesByRecording("d9504e13-d5af-465d-aae8-02715c098d6c", offset = 0, limit = 5, include = ReleaseInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get releases by track test`(): Unit = runTest {
        val result = client.browse.getReleasesByTrack("d9504e13-d5af-465d-aae8-02715c098d6c", offset = 0, limit = 5, include = ReleaseInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get releases by track artist test`(): Unit = runTest {
        val result = client.browse.getReleasesByTrackArtist("cad9e169-893d-4432-a23f-3aa16beb9f0e", offset = 0, limit = 5, include = ReleaseInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get release groups by release test`(): Unit = runTest {
        val result = client.browse.getReleaseGroupsByRelease("93d67f7b-0cc9-4c96-9bf5-5e1567870d3f", offset = 0, limit = 5, include = ReleaseGroupInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get recordings by release test`(): Unit = runTest {
        val result = client.browse.getRecordingsByRelease("93d67f7b-0cc9-4c96-9bf5-5e1567870d3f", offset = 0, limit = 5, include = RecordingInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get recordings by work test`(): Unit = runTest {
        val result = client.browse.getRecordingsByWork("ca37f64d-7fd7-4aee-9257-7ba739353498", offset = 0, limit = 5, include = RecordingInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get artists by recording test`(): Unit = runTest {
        val result = client.browse.getArtistsByRecording("d9504e13-d5af-465d-aae8-02715c098d6c", offset = 0, limit = 5, include = ArtistInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get artists by release test`(): Unit = runTest {
        val result = client.browse.getArtistsByRelease("93d67f7b-0cc9-4c96-9bf5-5e1567870d3f", offset = 0, limit = 5, include = ArtistInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get artists by release-group test`(): Unit = runTest {
        val result = client.browse.getArtistsByReleaseGroup("cbd45f37-9560-3d16-851b-a0c293aecb7c", offset = 0, limit = 5, include = ArtistInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get artists by work test`(): Unit = runTest {
        val result = client.browse.getArtistsByWork("ca37f64d-7fd7-4aee-9257-7ba739353498", offset = 0, limit = 5, include = ArtistInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get labels by release test`(): Unit = runTest {
        val result = client.browse.getLabelsByRelease("93d67f7b-0cc9-4c96-9bf5-5e1567870d3f", offset = 0, limit = 5, include = LabelInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get events by area test`(): Unit = runTest {
        val result = client.browse.getEventsByArea("51d34c28-61bf-3d21-849f-7492672a9d44", offset = 0, limit = 5, include = EventInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `get events by event test`(): Unit = runTest {
        val result = client.browse.getEventsByEvent("f6ede675-887d-41a8-9468-2af872f67ecf", offset = 0, limit = 5, include = EventInclude.entries.toSet())

        println(">>> Result isSuccess: ${result.isSuccess}")
        println(">>> Result: $result")
        println(">>> Exception type: ${result.exceptionOrNull()?.javaClass?.simpleName}")
        println(">>> Exception message: ${result.exceptionOrNull()?.message}")

        assertTrue(result.isSuccess)
    }
}
