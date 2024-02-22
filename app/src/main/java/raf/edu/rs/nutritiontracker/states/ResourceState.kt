package raf.edu.rs.nutritiontracker.states

sealed class ResourceState{
    object Loading: ResourceState()
    object DataFetched: ResourceState()
    data class Success(val movies: List<*>): ResourceState()
    data class Error(val message: String): ResourceState()

}
