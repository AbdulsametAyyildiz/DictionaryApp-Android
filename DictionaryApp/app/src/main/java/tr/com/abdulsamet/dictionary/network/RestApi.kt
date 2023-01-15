package tr.com.abdulsamet.dictionary.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import tr.com.abdulsamet.dictionary.network.model.MeaningResponse
import tr.com.abdulsamet.dictionary.network.model.SynonymResponse

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
interface RestApi {
    @GET
    suspend fun getMeaning(
        @Url url: String,
    ): Response<List<MeaningResponse>>

    @GET
    suspend fun getSynonyms(
        @Url url: String,
        @Query("rel_syn") word: String,
    ): Response<List<SynonymResponse>>
}