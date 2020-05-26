package domain

/**
 * Review object.
 *
 * Created by Ryan.
 */
class Review(
    var reviewID: String? = null,
    var userID: String? = null,
    var flatID: String? = null,
    var cleanliness: Float = 0f,
    var landlord: Float = 0f,
    var location: Float = 0f,
    var value: Float = 0f,
    var anonymous: Boolean = false,
    var date: String? = null,
    var comment: String? = null
)
