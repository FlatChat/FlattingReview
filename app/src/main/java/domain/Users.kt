package domain

import com.google.firebase.database.IgnoreExtraProperties
/**
 * A user object class containing variables to store the users unique ID, First Name, Last Name, and Email Address.
 * @author Nikki Meadows
 */
@IgnoreExtraProperties
data class Users (
    var userID: String? = "",
    var firstNameUsers: String? = "",
    var lastNameUsers: String? = "",
    var emailUsers: String? = ""
)