package tokopedia.app.data.repository.moviedetail

import retrofit2.Response
import tokopedia.app.data.entity.Movie
import tokopedia.app.data.routes.NetworkServices

class MovieDetailRepositoryImpl constructor(private val services: NetworkServices): MovieDetailRepository {

    override suspend fun getMovieDetail(movieId: String): Response<Movie> {
        return services.getMovieDetail(movieId)
    }
}