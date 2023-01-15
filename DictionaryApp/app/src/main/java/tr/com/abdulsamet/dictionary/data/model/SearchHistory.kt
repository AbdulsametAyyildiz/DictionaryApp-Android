package tr.com.abdulsamet.dictionary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import tr.com.abdulsamet.dictionary.data.TableName

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
@Entity(tableName = TableName.SEARCH_HISTORY)
data class SearchHistory(
    val searchString: String
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
