package domain

import com.google.firebase.database.IgnoreExtraProperties

/**
 * This class acts as a constructor
 * for flats being added
 */
@IgnoreExtraProperties
data class NewFlat(
    var flatID: String? = null,
    var address: String? = "",
    var bedrooms: String? = "",
    var bathrooms: String? = ""
)