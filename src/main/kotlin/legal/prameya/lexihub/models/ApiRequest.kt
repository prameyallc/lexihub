package legal.prameya.lexihub.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiRequest(
    val model: String,
    val prompt: String,
    val stream: Boolean,
)
