package net.albertogarrido.gygreviews.repository.persistence

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import net.albertogarrido.gygreviews.repository.Review


@Database(entities = [(Review::class)], version = 1)
abstract class ReviewsDatabase : RoomDatabase() {
    abstract fun reviewDao(): ReviewsDao
}