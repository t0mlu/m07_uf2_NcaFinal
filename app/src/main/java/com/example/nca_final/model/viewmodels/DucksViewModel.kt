package com.example.nca_final.model.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.nca_final.model.dao.DuckDAO
import com.example.nca_final.model.entities.Duck
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DucksViewModel(private val duckDAO: DuckDAO) : ViewModel() {
    private val searchChanel = ConflatedBroadcastChannel<String>();

    val duckListLiveData = searchChanel.asFlow().flatMapLatest { search ->
        getAllDucksFiltered(search)
    }.asLiveData()

    fun setSearchQuery(search: String) {
        searchChanel.trySend(search)
    }

    fun getAllDucks(): Flow<List<Duck>> = duckDAO.getAllDucks()

    fun getAllDucksFiltered(search: String): Flow<List<Duck>> = duckDAO.getAllDucksFiltered(search)

    fun insert(vararg ducks: Duck) {
        GlobalScope.launch {
            duckDAO.insert(*ducks)
        }
    }

    fun updateDucks(vararg ducks: Duck) {
        GlobalScope.launch {
            duckDAO.updateDucks(*ducks)
        }
    }

    fun deleteDucks(vararg ducks: Duck) {
        GlobalScope.launch {
            duckDAO.deleteDucks(*ducks)
        }
    }
}

class DucksViewModelFactory(private val duckDAO: DuckDAO): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DucksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DucksViewModel(duckDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}