package domain

/**
 * Review object.
 *
 * Created by Ryan.
 */
class Review (
    var reviewID: Int,
    var userID: Int,
    var flatID: Int,
    var cleanliness: Float,
    var landlord: Float,
    var location: Float,
    var value: Float,
    var anonymous: Boolean
    ) {

    var comment: String? = null // Default value
}

