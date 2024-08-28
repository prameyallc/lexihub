package legal.prameya.lexihub.datastore

import com.typesafe.config.ConfigFactory
import net.snowflake.client.jdbc.internal.org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.io.pem.PemReader
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.Security
import java.security.interfaces.RSAPrivateCrtKey
import java.security.spec.PKCS8EncodedKeySpec
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

object SnowflakeClient {
    private val config =
        ConfigFactory.parseFile(Paths.get("/Users/sbkoth/work/lexihub/src/main/resources/application.conf").toFile())
            .getConfig("snowflake")
    private val url = config.getString("url")
    private val user = config.getString("user")
    private val privateKeyPath = config.getString("privateKeyPath")
    private val privateKeyPassphrase = config.getString("privateKeyPassphrase")
    private val database = config.getString("database")
    private val schema = config.getString("schema")
    private val warehouse = config.getString("warehouse")
    private val authenticator = config.getString("authenticator")
    private val role = config.getString("role")

    init {
        Class.forName("net.snowflake.client.jdbc.SnowflakeDriver")
        Security.addProvider(BouncyCastleProvider())
    }

    fun getPrivateKey(): RSAPrivateCrtKey {
        val keyBytes = Files.readAllBytes(Paths.get(privateKeyPath))
        PemReader(keyBytes.inputStream().reader()).use { pemReader ->
            val pemObject =
                pemReader.readPemObject() ?: throw IllegalArgumentException("Invalid PEM file: no content found")
            val keySpec = PKCS8EncodedKeySpec(pemObject.content)
            val keyFactory = KeyFactory.getInstance("RSA")
            return keyFactory.generatePrivate(keySpec) as RSAPrivateCrtKey
        }
    }

     fun getConnection(privateKey: RSAPrivateCrtKey): Connection {
        val connectionProps = Properties().apply {
            put("user", user)
            put("privateKey", privateKey)
            put("privateKeyPassphrase", privateKeyPassphrase)
            put("db", database)
            put("schema", schema)
            put("warehouse", warehouse)
            put("role", role)
            put("authenticator", authenticator)
        }
        return DriverManager.getConnection(url, connectionProps)
    }
}
