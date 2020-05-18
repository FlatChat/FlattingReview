package com.example.flattingreview

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class NewFlat(
    var address: String? = "",
    var bedrooms: String? = "",
    var bathrooms: String? = ""
)