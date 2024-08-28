@file:Suppress("SqlResolve")

package legal.prameya.lexihub.datastore.dao

import kotlinx.datetime.Instant
import legal.prameya.lexihub.models.ZipcodeCityState
import java.sql.Connection
import java.sql.DriverManager
import kotlin.test.Test
import kotlin.test.assertEquals

class SnowflakeDAOTest {

    @Test
    fun testFetchData() {
        val connection: Connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;")
        connection.createStatement().use { statement ->
            statement.execute("CREATE SCHEMA IF NOT EXISTS METADATA")
            statement.execute(
                """
                CREATE TABLE METADATA.ZIPCODE_CITY_STATE (
                    PHYSICAL_ZIP VARCHAR(255),
                    AREA_NAME VARCHAR(255),
                    AREA_CODE VARCHAR(255),
                    DISTRICT_NAME VARCHAR(255),
                    DISTRICT_NO VARCHAR(255),
                    DELIVERY_ZIPCODE VARCHAR(255),
                    LOCALE_NAME VARCHAR(255),
                    PHYSICAL_DELV_ADDR VARCHAR(255),
                    CITY VARCHAR(255),
                    STATE VARCHAR(255),
                    PHYSICAL_ZIP_4 VARCHAR(255),
                    CREATED_AT TIMESTAMP,
                    UPDATED_AT TIMESTAMP
                )
                """
            )
            statement.execute(
                """
                INSERT INTO METADATA.ZIPCODE_CITY_STATE VALUES (
                    '02062', 'Area Name', '123', 'District Name', '456', '02062-1234', 'Locale Name', '123 Main St', 'Norwood', 'MA', '1234', '2023-01-01 00:00:00', '2023-01-02 00:00:00'
                )
                """
            )
        }

        val dao = SnowflakeDAO(connection)
        val result = dao.fetchData("SELECT * FROM METADATA.ZIPCODE_CITY_STATE")

        val expected = listOf(
            ZipcodeCityState(
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
                createdAt = Instant.parse("2023-01-01T06:00:00Z"),
                updatedAt = Instant.parse("2023-01-02T06:00:00Z")
            )
        )

        assertEquals(expected, result)
    }
}