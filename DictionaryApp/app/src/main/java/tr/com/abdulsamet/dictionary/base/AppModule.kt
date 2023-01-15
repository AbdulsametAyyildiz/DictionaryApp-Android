package tr.com.abdulsamet.dictionary.base

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tr.com.abdulsamet.dictionary.data.DictionaryDatabase
import tr.com.abdulsamet.dictionary.data.dao.SearchHistoryDao
import tr.com.abdulsamet.dictionary.network.RestApi
import tr.com.abdulsamet.dictionary.repository.SearchHistoryRepository
import tr.com.abdulsamet.dictionary.repository.WordRequestRepository
import tr.com.abdulsamet.dictionary.repository.irepository.ISearchHistoryRepository
import tr.com.abdulsamet.dictionary.repository.irepository.IWordRequestRepository
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoom(@ApplicationContext context: Context) = DictionaryDatabase.invoke(context)

    @Singleton
    @Provides
    fun injectOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun injectRetrofit(
        okHttpClient: OkHttpClient
    ): RestApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.dictionaryapi.dev")
            .build()
            .create(RestApi::class.java)
    }

    @Singleton
    @Provides
    fun injectSearchHistoryDao(database: DictionaryDatabase) = database.searchHistoryDao()

    @Singleton
    @Provides
    fun injectSearchHistoryRepository(
        dao: SearchHistoryDao
    ) = SearchHistoryRepository(dao) as ISearchHistoryRepository

    @Singleton
    @Provides
    fun injectWordRequestRepository(
        restApi: RestApi
    ) = WordRequestRepository(restApi) as IWordRequestRepository
}