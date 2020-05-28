package domain

import com.google.firebase.database.IgnoreExtraProperties

/**
 * This class represents a Flat object.
 *
 * @property flatID the unique ID for the new flat.
 * @property address records the location of the flat.
 * @property bedrooms the number of bedrooms in the flat.
 * @property bathrooms the number of bathrooms in the flat.
 * @property flatsReviews a list to store the review IDs for specific flats.
 *
 * @author Meggie Morrison
 */
@IgnoreExtraProperties
data class NewFlat(
    var flatID: String? = null,
    var address: String? = "",
    var bedrooms: String? = "",
    var bathrooms: String? = ""
){
    var flatsReviews = listOf<String>()
}