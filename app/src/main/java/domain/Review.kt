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
    var name: String? = null,
    var cleanliness: Double = 0.0,
    var landlord: Double = 0.0,
    var location: Double = 0.0,
    var value: Double = 0.0,
    var anonymous: Boolean = false,
    var date: String? = null,
    var comment: String? = null
)
