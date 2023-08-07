package de.christoph.musikkorpsminus.votings

import android.os.Parcel
import android.os.Parcelable

data class Voting (
    var id:String = "",
    var name:String = "",
    var liked:ArrayList<String> = ArrayList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeStringList(liked)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Voting> {
        override fun createFromParcel(parcel: Parcel): Voting {
            return Voting(parcel)
        }

        override fun newArray(size: Int): Array<Voting?> {
            return arrayOfNulls(size)
        }
    }
}