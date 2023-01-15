package tr.com.abdulsamet.dictionary.repository

import tr.com.abdulsamet.dictionary.base.ResponseResource
import tr.com.abdulsamet.dictionary.network.RestApi
import tr.com.abdulsamet.dictionary.network.model.MeaningResponse
import tr.com.abdulsamet.dictionary.network.model.SynonymResponse
import tr.com.abdulsamet.dictionary.repository.irepository.IWordRequestRepository
import javax.inject.Inject

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
class WordRequestRepository @Inject constructor(
    private val restApi: RestApi
): IWordRequestRepository {

    override suspend fun getMeaning(word: String): ResponseResource<List<MeaningResponse>> {
        try {
            val response = restApi.getMeaning(
                "https://api.dictionaryapi.dev/api/v2/entries/en/$word"
            )

            if (!response.isSuccessful) {
                return ResponseResource.error(if(response.message() == "")
                    "Word not found" else response.message(), null)
            }
            if (response.body() == null) {
                return ResponseResource.error("Response body is null", null)
            }

            return ResponseResource.success(response.body()!!)
        } catch (t: Throwable) {
            return ResponseResource.error(t.message.toString(), null)
        }
    }

    override suspend fun getSynonyms(word: String): ResponseResource<List<SynonymResponse>> {
        try {
            val response = restApi.getSynonyms(
                "https://api.datamuse.com/words",
                word
            )

            if (!response.isSuccessful) {
                return ResponseResource.error(response.message(), null)
            }
            if (response.body() == null) {
                return ResponseResource.error("Response body is null", null)
            }

            return ResponseResource.success(response.body()!!)
        } catch (t: Throwable) {
            return ResponseResource.error(t.message.toString(), null)
        }
    }

}