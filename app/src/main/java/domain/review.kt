package domain

/**
 * Review object.
 *
 * Created by Ryan.
 */
class review {
    var reviewID: Int
    var userID: Int
    var flatID: Int
    var cleanliness: Float
    var landlord: Float
    var location: Float
    var value: Float
    var overRating = 0
    var numberOfReviews = 0
    var comment: String? = null
    var anonymous: Boolean

    constructor(
        reviewID: Int,
        userID: Int,
        flatID: Int,
        cleanliness: Float,
        landlord: Float,
        location: Float,
        value: Float,
        comment: String?,
        anonymous: Boolean
    ) {
        this.reviewID = reviewID
        this.userID = userID
        this.flatID = flatID
        this.cleanliness = cleanliness
        this.landlord = landlord
        this.location = location
        this.value = value
        this.comment = comment
        this.anonymous = anonymous
    }

    constructor(
        reviewID: Int,
        userID: Int,
        flatID: Int,
        cleanliness: Float,
        landlord: Float,
        location: Float,
        value: Float,
        anonymous: Boolean
    ) {
        this.reviewID = reviewID
        this.userID = userID
        this.flatID = flatID
        this.cleanliness = cleanliness
        this.landlord = landlord
        this.location = location
        this.value = value
        this.anonymous = anonymous
    }



}