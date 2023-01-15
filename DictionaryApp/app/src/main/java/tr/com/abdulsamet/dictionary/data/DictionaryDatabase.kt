package tr.com.abdulsamet.dictionary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tr.com.abdulsamet.dictionary.data.dao.SearchHistoryDao
import tr.com.abdulsamet.dictionary.data.model.SearchHistory

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
@Database(
    entities = [SearchHistory::class], version = 1, exportSchema = false
)

abstract class DictionaryDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        @Volatile
        private var instance: DictionaryDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, DictionaryDatabase::class.java, "DictionaryApp"
        ).fallbackToDestructiveMigration().build()
    }

}
