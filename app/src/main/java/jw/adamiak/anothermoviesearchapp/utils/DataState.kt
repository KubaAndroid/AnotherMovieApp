package jw.adamiak.anothermoviesearchapp.utils

sealed class DataState {
	object Success : DataState()
	data class Error(val error: String) : DataState()
}