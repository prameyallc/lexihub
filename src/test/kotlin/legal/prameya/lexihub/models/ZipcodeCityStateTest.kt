package legal.prameya.lexihub.models

import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ZipcodeCityStateTest {

    @Test
    fun testSerialization() {
        val json = Json { encodeDefaults = true }
        val zipcodeCityState = ZipcodeCityState(
            physicalZip = "02062",
            areaName = "Area Name",
            areaCode = "123",
            districtName = "District Name",
            districtNo = "456",
            deliveryZipcode = "02062-1234",
            localeName = "Locale Name",
            physicalDelvAddr = "123 Main St",
            city = "Norwood",
            state = "MA",
            physicalZip4 = "1234",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-02T00:00:00Z")
        )
        val jsonString = json.encodeToString(ZipcodeCityState.serializer(), zipcodeCityState)
        val decoded = json.decodeFromString(ZipcodeCityState.serializer(), jsonString)
        assertEquals(zipcodeCityState, decoded)
    }
}