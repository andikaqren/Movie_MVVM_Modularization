package com.andika.architecturecomponent.core.business.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andika.architecturecomponent.core.business.data.local.model.LocalGenre
import com.andika.architecturecomponent.core.business.data.local.model.LocalMovie
import com.andika.architecturecomponent.core.business.data.local.model.LocalTV

@Database(
    entities = [LocalMovie::class, LocalGenre::class, LocalTV::class],
    version = 2,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localDB(): LocalDao

    companion object {
        const val DATABASE_NAME: String = "local_db"
    }
}