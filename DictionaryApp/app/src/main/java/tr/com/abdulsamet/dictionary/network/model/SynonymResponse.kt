package tr.com.abdulsamet.dictionary.network.model

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
data class SynonymResponse(
    val word: String,
    val score: Int
)