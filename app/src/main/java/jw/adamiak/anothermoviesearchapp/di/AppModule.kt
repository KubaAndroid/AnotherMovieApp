package jw.adamiak.anothermoviesearchapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jw.adamiak.anothermoviesearchapp.data.api.RetrofitInstance
import jw.adamiak.anothermoviesearchapp.data.api.TheMovieDbApi
import jw.adamiak.anothermoviesearchapp.data.db.MoviesDao
import jw.adamiak.anothermoviesearchapp.data.db.MoviesDatabase
import jw.adamiak.anothermoviesearchapp.data.repository.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Singleton
	@Provides
	fun provideMoviesDatabase(app: Application): MoviesDatabase {
		return Room.databaseBuilder(
			app,
			MoviesDatabase::class.java,
			"movies_db"
		).fallbackToDestructiveMigration()
			.build()
	}

	@Singleton
	@Provides
	fun provideDao(db: MoviesDatabase): MoviesDao {
		return db.dao
	}

	@Singleton
	@Provides
	fun provideMoviesApi(): TheMovieDbApi {
		return RetrofitInstance.api
	}

	@Singleton
	@Provides
	fun provideRepository(api: TheMovieDbApi, dao: MoviesDao): MovieRepository {
		return MovieRepository(api, dao)
	}

}