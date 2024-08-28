package legal.prameya.lexihub.datastore.dao

import legal.prameya.lexihub.models.ZipcodeCityState
import java.sql.Connection
import kotlinx.datetime.toKotlinInstant

class SnowflakeDAO(private val connection: Connection) {

    fun fetchData(query: String): List<ZipcodeCityState> {
        val result = mutableListOf<ZipcodeCityState>()
        connection.createStatement().use { statement ->
            val resultSet = statement.executeQuery(query)
            while (resultSet.next()) {
                val zipcodeCityState = ZipcodeCityState(
                    physicalZip = resultSet.getString("PHYSICAL_ZIP"),
                    areaName = resultSet.getString("AREA_NAME"),
                    areaCode = resultSet.getString("AREA_CODE"),
                    districtName = resultSet.getString("DISTRICT_NAME"),
                    districtNo = resultSet.getString("DISTRICT_NO"),
                    deliveryZipcode = resultSet.getString("DELIVERY_ZIPCODE"),
                    localeName = resultSet.getString("LOCALE_NAME"),
                    physicalDelvAddr = resultSet.getString("PHYSICAL_DELV_ADDR"),
                    city = resultSet.getString("CITY"),
                    state = resultSet.getString("STATE"),
                    physicalZip4 = resultSet.getString("PHYSICAL_ZIP_4"),
                    createdAt = resultSet.getTimestamp("created_at")?.toInstant()?.toKotlinInstant(),
                    updatedAt = resultSet.getTimestamp("updated_at")?.toInstant()?.toKotlinInstant()
                )
                result.add(zipcodeCityState)
            }
        }
        return result
    }
}