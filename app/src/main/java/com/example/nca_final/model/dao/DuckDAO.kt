package com.example.nca_final.model.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.example.nca_final.model.entities.Duck
import kotlinx.coroutines.flow.Flow


@Dao
interface DuckDAO {

    // CREATE
    @Insert(onConflict = REPLACE)
    fun insert(vararg duck: Duck)

    @Insert(onConflict = REPLACE)
    fun insertDuckList(order: List<Duck>)

    // READ
    @Query("SELECT * FROM duck WHERE id = :id")
    fun getDuckById(id: Int): Duck

    @Query("SELECT * FROM duck")
    fun getAllDucks(): Flow<List<Duck>>

    @Query("SELECT * FROM duck WHERE name LIKE '%' || :search || '%'")
    fun getAllDucksFiltered(search: String): Flow<List<Duck>>

    @Query("SELECT EXISTS (SELECT * FROM duck)")
    fun duckHasRecords(): Boolean

    // UPDATE
    @Update
    fun updateDucks(vararg ducks: Duck)


    // DELETE
    @Delete
    fun deleteDucks(vararg ducks: Duck)
}