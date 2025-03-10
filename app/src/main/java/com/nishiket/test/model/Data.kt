package com.nishiket.test.model

import android.os.Parcel
import android.os.Parcelable

/**
{
"id": 1,
"name": "Hardik Lakhani",
"username": "imhardiklakhani",
"email": "hardik@gmail.com",
"contact_number": "+19099968325",
"dob": "10-29-1994",
"bio": "Life is too short to be Happy #BeHappy",
"gender": "male",
"latitude": 23.055974,
"longitude": 72.506192,
"address": "Thaltej, Ahmedabad, Gujarat, India",
"fitness_level": "advanced",
"interests": [
"strength training",
"mind/body",
"cardio",
"outdoor activities",
"group classes"
],
"profile_photo": "https://strengthen-numbers-stag.s3.amazonaws.com/users/profile_photo/rk4IOeAAJ9KSNqN2ZO2quc5Vmlk9Zyr9d3xSOg5R.jpg",
"registered_at": "24/09/2024 07:26 AM",
"total_followers": 7,
"total_followings": 5,
"is_following": 0,
"is_blocked": 0,
"share_location": 1
}
 */
data class Data(
    val address: String,
    val bio: String,
    val contact_number: String,
    val dob: String,
    val email: String,
    val fitness_level: String,
    val gender: String,
    val id: Int,
    val interests: List<String>?,
    val is_blocked: Int,
    val is_following: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val profile_photo: String,
    val registered_at: String,
    val share_location: Int,
    val total_followers: Int,
    val total_followings: Int,
    val username: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.createStringArrayList()?.toList(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString()
    ) {
    }


    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeString(bio)
        parcel.writeString(contact_number)
        parcel.writeString(dob)
        parcel.writeString(email)
        parcel.writeString(fitness_level)
        parcel.writeString(gender)
        parcel.writeInt(id)
        parcel.writeStringList(interests)
        parcel.writeInt(is_blocked)
        parcel.writeInt(is_following)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(name)
        parcel.writeString(profile_photo)
        parcel.writeString(registered_at)
        parcel.writeInt(share_location)
        parcel.writeInt(total_followers)
        parcel.writeInt(total_followings)
        parcel.writeString(username)
    }

}