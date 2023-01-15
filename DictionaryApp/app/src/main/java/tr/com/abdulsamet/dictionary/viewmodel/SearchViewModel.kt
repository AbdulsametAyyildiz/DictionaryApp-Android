package tr.com.abdulsamet.dictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tr.com.abdulsamet.dictionary.data.model.SearchHistory
import tr.com.abdulsamet.dictionary.repository.irepository.ISearchHistoryRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHistoryRepository: ISearchHistoryRepository
) : ViewModel() {
    val observedSearchHistory: LiveData<List<SearchHistory>> = searchHistoryRepository.observe()

    fun insertSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        searchHistoryRepository.insert(searchHistory)
    }
}