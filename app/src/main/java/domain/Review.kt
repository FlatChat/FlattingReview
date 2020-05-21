package domain

import java.time.LocalDateTime

/**
 * Review object.
 *
 * Created by Ryan.
 */
class Review(
    var reviewID: Int,
    var userID: String?,
    var flatID: Int,
    var cleanliness: Float,
    var landlord: Float,
    var location: Float,
    var value: Float,
    var anonymous: Boolean,
    var date: LocalDateTime
    ) {

    var comment: String? = null // Default value
}

