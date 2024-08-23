package legal.prameya.lexihub.models

import kotlinx.serialization.Serializable

@Serializable
data class GenerateRequest(
    val model: String,
    val prompt: String,
    val stream: Boolean,
)
