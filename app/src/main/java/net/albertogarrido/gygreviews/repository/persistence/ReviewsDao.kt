package net.albertogarrido.gygreviews.repository.persistence

import android.arch.persistence.room.*
import net.albertogarrido.gygreviews.repository.Review

@Dao
interface ReviewsDao {
    @Query("SELECT * FROM review")
    fun getAll(): List<Review>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<Review>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): Review

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Review)

//    @Delete
//    fun delete(user: Review)
}