package tr.com.abdulsamet.dictionary.base

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
data class ResponseResource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): ResponseResource<T> {
            return ResponseResource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ResponseResource<T> {
            return ResponseResource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): ResponseResource<T> {
            return ResponseResource(Status.LOADING, data, null)
        }

    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

}