package domain

import com.google.firebase.database.IgnoreExtraProperties
/**
 * A user object class.
 * @author Nikki Meadows
 */
@IgnoreExtraProperties
data class Users (
    var userID: String? = "",
    var firstNameUsers: String? = "",
    var lastNameUsers: String? = "",
    var emailUsers: String? = ""
)