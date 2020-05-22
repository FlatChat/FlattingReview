package domain

/**
 * Flat object.
 *
 * Created by Ryan.
 */
class Flat(
    var flatID: String?,
    var flatName: String?,
    var streetAddress: String,
    var suburb: String,
    var city: String,
    var postcode: Int,
    var reviews: Array<Review>
)