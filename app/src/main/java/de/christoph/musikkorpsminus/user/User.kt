package de.christoph.musikkorpsminus.user

import android.os.Parcel
import android.os.Parcelable

data class User (
    var id:String = "",
    var username:String = "",
    var email:String = "",
    var fcmToken:String = "",
    var instrument:String = "",
    var admin:Int = 0,
    var defaultPromise:Boolean = false,
    var chairID:Int = 0
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readInt()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(fcmToken)
        parcel.writeString(instrument)
        parcel.writeInt(admin)
        parcel.writeByte(if (defaultPromise) 1 else 0)
        parcel.writeInt(chairID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}