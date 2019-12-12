package tokopedia.app.data.repository.moviedetail

import retrofit2.Response
import tokopedia.app.data.entity.Movie

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: String): Response<Movie>
}