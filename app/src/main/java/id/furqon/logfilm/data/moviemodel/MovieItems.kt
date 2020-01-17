package id.furqon.logfilm.data.moviemodel

data class MovieItems(
    var id: Int = 0,
    var overview: String? = null,
    var poster_path: String? = null,
    var release_date: String? = null,
    var title: String? = null,
    var vote_average: Double? = null
)