package tr.com.abdulsamet.dictionary.custom.model

data class ProcessedMeaning(
    val speechList : List<String>,
    val meaningList: List<Meaning>
){
    data class Meaning(
        var parent: String,
        var order: Int,
        var definition: String,
        var synonyms: List<String>,
        var antonyms: List<String>,
        var example: String?
    )
}
