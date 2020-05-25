package com.example.flattingreview

import com.google.firebase.database.IgnoreExtraProperties

/**
 * This class acts as a constructor
 * for flats being added
 */
@IgnoreExtraProperties
data class NewFlat(
    var address: String? = "",
    var bedrooms: String? = "",
    var bathrooms: String? = ""
)