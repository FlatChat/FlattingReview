package domain

/**
 * A review object. Used to model the reviews. This model is used when presenting, and reading
 * writing reviews from the database.
 * @author Ryan Cole
 * @property reviewID The reviews id
 * @property userID The users id who created the review
 * @property flatID The flats id that the review is based on
 * @property name The users first name who wrote the review, this is to display their name
 * when displaying the reviews
 * @property cleanliness An elements that users can rate flats on
 * @property landlord An elements that users can rate flats on
 * @property location An elements that users can rate flats on
 * @property value An elements that users can rate flats on
 * @property anonymous If the user doesn't want their first name displayed on the review
 * @property date The date the review was created
 * @property comment Any comments a user choose to write about the flat
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
