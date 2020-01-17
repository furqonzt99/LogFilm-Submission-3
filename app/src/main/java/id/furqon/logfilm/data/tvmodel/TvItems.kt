package id.furqon.logfilm.data.tvmodel

data class TvItems(
    var id: Int = 0,
    var overview: String? = null,
    var poster_path: String? = null,
    var first_air_date: String? = null,
    var name: String? = null,
    var vote_average: Double? = null
)