package tr.com.abdulsamet.dictionary.repository.irepository

import androidx.lifecycle.LiveData
import tr.com.abdulsamet.dictionary.data.model.SearchHistory

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 * The repository layer has been returned over an interface in order to be suitable for the unit test structure.
 */
interface ISearchHistoryRepository {

    suspend fun insert(searchHistory: SearchHistory)

    fun observe(): LiveData<List<SearchHistory>>

}