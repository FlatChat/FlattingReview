package models

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(flatID)
        parcel.writeString(address)
        parcel.writeString(bedrooms)
        parcel.writeString(bathrooms)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Flat> {
        override fun createFromParcel(parcel: Parcel): Flat {
            return Flat(parcel)
        }

        override fun newArray(size: Int): Array<Flat?> {
            return arrayOfNulls(size)
        }
    }
}
