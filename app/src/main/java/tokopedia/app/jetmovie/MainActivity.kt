package tokopedia.app.jetmovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.ui.PopularMovieFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inflatePopularMovieFragment()
    }

    private fun inflatePopularMovieFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, PopularMovieFragment())
        transaction.commit()
    }

}
