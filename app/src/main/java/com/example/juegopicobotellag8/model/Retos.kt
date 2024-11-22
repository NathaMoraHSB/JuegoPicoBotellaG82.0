package com.example.juegopicobotellag8.model

import android.os.Parcel
import android.os.Parcelable

data class Retos(
    val id: String? = null,
    val description: String = ""): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString() ?: ""
        )

        override fun writeToParcel(parcel: Parcel, flags: Int){
            parcel.writeString(id)
            parcel.writeString(description)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Retos> {
            override fun createFromParcel(parcel: Parcel): Retos {
                return Retos(parcel)
            }

            override fun newArray(size: Int): Array<Retos?> {
                return arrayOfNulls(size)
            }
        }
    }