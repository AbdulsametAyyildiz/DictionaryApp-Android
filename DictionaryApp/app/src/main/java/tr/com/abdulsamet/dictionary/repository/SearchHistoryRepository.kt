package tr.com.abdulsamet.dictionary.repository

import androidx.lifecycle.LiveData
import tr.com.abdulsamet.dictionary.data.dao.SearchHistoryDao
import tr.com.abdulsamet.dictionary.data.model.SearchHistory
import tr.com.abdulsamet.dictionary.repository.irepository.ISearchHistoryRepository
import javax.inject.Inject

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
class SearchHistoryRepository @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
): ISearchHistoryRepository {

    override suspend fun insert(searchHistory: SearchHistory) {
        if(searchHistoryDao.read().find { it.searchString == searchHistory.searchString } == null){
            searchHistoryDao.insert(searchHistory)
        }
    }

    override fun observe(): LiveData<List<SearchHistory>> {
        return searchHistoryDao.observe()
    }

}