package legal.prameya.lexihub.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class ZipcodeCityState(
    val physicalZip: String,
    val areaName: String?,
    val areaCode: String?,
    val districtName: String?,
    val districtNo: String?,
    val deliveryZipcode: String,
    val localeName: String?,
    val physicalDelvAddr: String?,
    val city: String?,
    val state: String?,
    val physicalZip4: String?,
    val createdAt: Instant?,
    val updatedAt: Instant?
)