package tr.com.abdulsamet.dictionary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tr.com.abdulsamet.dictionary.data.TableName.SEARCH_HISTORY
import tr.com.abdulsamet.dictionary.data.model.SearchHistory

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchHistory: SearchHistory)

    @Query("SELECT * FROM $SEARCH_HISTORY ORDER BY uuid DESC LIMIT 5")
    fun observe(): LiveData<List<SearchHistory>>

    @Query("SELECT * FROM $SEARCH_HISTORY")
    suspend fun read(): List<SearchHistory>
}