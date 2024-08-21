package legal.prameya.lexihub.models

import kotlinx.serialization.Serializable

class ApiResponses {
    @Serializable
    data class OllamaResponse(
        val id: String,
        val model: String,
        val created_at: String,
        val response: String,
        val done: Boolean
    )

    @Serializable
    data class CourtListenerResponse(
        val count: Int,
        val next: String?,
        val previous: String?,
        val results: List<CourtCase>
    ) {
        @Serializable
        data class CourtCase(
            val id: Int,
            val absolute_url: String,
            val caseName: String,
            val docketNumber: String,
            val dateArgued: String?,
            val dateReargued: String?,
            val dateDecided: String,
            val court: String
        )
    }

    @Serializable
    data class RegulationsGovResponse(
        val totalElements: Int,
        val totalPages: Int,
        val data: List<Regulation>
    ) {
        @Serializable
        data class Regulation(
            val id: String,
            val type: String,
            val attributes: RegulationAttributes
        )

        @Serializable
        data class RegulationAttributes(
            val documentType: String,
            val docketId: String,
            val agencyId: String,
            val title: String,
            val summary: String
        )
    }

    @Serializable
    data class CongressGovResponse(
        val congress: Int,
        val bills: List<Bill>
    ) {
        @Serializable
        data class Bill(
            val number: String,
            val type: String,
            val title: String,
            val introducedDate: String,
            val sponsors: List<Sponsor>,
            val latestAction: Action
        )

        @Serializable
        data class Sponsor(
            val bioguideId: String,
            val fullName: String,
            val party: String,
            val state: String
        )

        @Serializable
        data class Action(
            val actionDate: String,
            val text: String
        )
    }

    @Serializable
    data class AgencyApiResponse(
        val status: String,
        val data: List<AgencyData>
    ) {
        @Serializable
        data class AgencyData(
            val id: String,
            val name: String,
            val acronym: String,
            val website: String,
            val description: String
        )
    }

}