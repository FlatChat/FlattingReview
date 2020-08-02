package models

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

/**
 * This class represents a Flat object.
 *
 * @property flatID the unique ID for the new flat.
 * @property address records the location of the flat.
 * @property bedrooms the number of bedrooms in the flat.
 * @property bathrooms the number of bathrooms in the flat.
 *
 * @author Meggie Morrison
 */
@IgnoreExtraProperties
data class Flat(
    var flatID: String? = null,
    var address: String? = "",
    var bedrooms: String? = "",
    var bathrooms: String? = ""
) : Serializable
