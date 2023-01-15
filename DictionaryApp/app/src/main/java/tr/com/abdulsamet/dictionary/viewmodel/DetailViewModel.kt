package tr.com.abdulsamet.dictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tr.com.abdulsamet.dictionary.base.ResponseResource
import tr.com.abdulsamet.dictionary.custom.model.ProcessedMeaning
import tr.com.abdulsamet.dictionary.network.model.MeaningResponse
import tr.com.abdulsamet.dictionary.network.model.SynonymResponse
import tr.com.abdulsamet.dictionary.repository.irepository.IWordRequestRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val wordRequestRepository: IWordRequestRepository
) : ViewModel() {

    private var mMeaningLiveData = MutableLiveData<ResponseResource<List<MeaningResponse>>>()
    val meaningLiveData: LiveData<ResponseResource<List<MeaningResponse>>>
        get() = mMeaningLiveData

    private var mSynonymLiveData = MutableLiveData<ResponseResource<List<SynonymResponse>>>()
    val synonymLiveData: LiveData<ResponseResource<List<SynonymResponse>>>
        get() = mSynonymLiveData

    fun getMeaning(word: String) {
        mMeaningLiveData.value = ResponseResource.loading(null)

        viewModelScope.launch {
            mMeaningLiveData.value = wordRequestRepository.getMeaning(word)
        }
    }

    fun getSynonym(word: String) {
        mSynonymLiveData.value = ResponseResource.loading(null)

        viewModelScope.launch {
            mSynonymLiveData.value = wordRequestRepository.getSynonyms(word)
        }
    }

    fun convertFormattedMeaningList(meaningResponse: MeaningResponse): ProcessedMeaning {
        val meaningMutableList = mutableListOf<ProcessedMeaning.Meaning>()
        val speechMutableList = mutableListOf<String>()
        meaningResponse.meanings.forEach { meaning ->
            val parent = meaning.partOfSpeech
            speechMutableList.add(parent)
            var order = 1
            meaning.definitions.forEach { definition ->
                meaningMutableList.add(
                    ProcessedMeaning.Meaning(
                        parent,
                        order,
                        definition.definition,
                        definition.synonyms,
                        definition.antonyms,
                        definition.example
                    )
                )
                order++
            }
        }

        return ProcessedMeaning(
            speechMutableList.toSet().toList(),
            meaningMutableList.toList()
        )
    }

    fun convertFormattedSynonymList(synonymResponse: List<SynonymResponse>): List<String> {
        val strList = mutableListOf<String>()
        synonymResponse.forEachIndexed { index, item ->
            if(index < 5){
                strList.add(item.word)
            }
        }

        return strList
    }

}