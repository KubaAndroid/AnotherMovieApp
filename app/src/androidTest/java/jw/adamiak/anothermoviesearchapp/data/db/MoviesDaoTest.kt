package jw.adamiak.anothermoviesearchapp.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MoviesDaoTest {

	@get:Rule
	var instantTaskExecutorRule = InstantTaskExecutorRule()

	private lateinit var database: MoviesDatabase
	private lateinit var dao: MoviesDao

	@Before
	fun setup(){
		database = Room.inMemoryDatabaseBuilder(
			ApplicationProvider.getApplicationContext(),
			MoviesDatabase::class.java
		).allowMainThreadQueries().build()
		dao = database.dao
	}

	@After
	fun teardown() {
		database.close()
	}

	@Test
	fun insertMovie() = runBlockingTest {
		val movie = Movie(1, "org title", "title", "a new movie",
			"path://", "20-12-2212", 2.3f)
		dao.insert(movie)
		val allMovies = dao.getAllMovies().getOrAwaitValue()
		assertThat(allMovies).contains(movie)
	}

	@Test
	fun deleteMovie() = runBlockingTest {
		val movie = Movie(1, "org title", "title", "a new movie",
			"path://", "20-12-2212", 2.3f)
		dao.insert(movie)
		dao.delete(movie)
		val allMovies = dao.getAllMovies().getOrAwaitValue()
		assertThat(allMovies).doesNotContain(movie)
	}



}