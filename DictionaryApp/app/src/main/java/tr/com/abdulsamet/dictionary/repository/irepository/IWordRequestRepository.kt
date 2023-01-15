package tr.com.abdulsamet.dictionary.repository.irepository

import tr.com.abdulsamet.dictionary.base.ResponseResource
import tr.com.abdulsamet.dictionary.network.model.MeaningResponse
import tr.com.abdulsamet.dictionary.network.model.SynonymResponse

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 * The repository layer has been returned over an interface in order to be suitable for the unit test structure.
 */
interface IWordRequestRepository {

    suspend fun getMeaning(
        word: String
    ): ResponseResource<List<MeaningResponse>>

    suspend fun getSynonyms(
        word: String
    ): ResponseResource<List<SynonymResponse>>
}