package de.christoph.musikkorpsminus.calendar

import android.os.Parcel
import android.os.Parcelable
import java.util.*
import kotlin.collections.ArrayList

data class Event (
    var documentID:String = "",
    var name:String = "",
    var day: Int = 0,
    var month: Int = 0,
    var year: Int = 0,
    var time:String = "",
    var promised:ArrayList<String> = ArrayList(),
    var cancelled:ArrayList<String> = ArrayList(),
    var isEventCancelled:Boolean = false,
    var isTraining:Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readInt()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.createStringArrayList()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(documentID)
        parcel.writeString(name)
        parcel.writeInt(day)
        parcel.writeInt(month)
        parcel.writeInt(year)
        parcel.writeString(time)
        parcel.writeStringList(promised)
        parcel.writeStringList(cancelled)
        parcel.writeByte(if (isEventCancelled) 1 else 0)
        parcel.writeByte(if (isTraining) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}