package com.nishiket.test.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
{
"id": 99,
"name": "Thursday",
"username": "ThursdaylOctober",
"email": "jenny@ma6ilinator.com",
"contact_number": "+18128828825",
"dob": "09-20-2001",
"bio": null,
"gender": null,
"latitude": 0,
"longitude": 0,
"address": null,
"fitness_level": null,
"interests": null,
"profile_photo": "https://strengthen-numbers-stag.dev-imaginovation.net/assets/media/users/user-icon@4x.png",
"registered_at": "10/03/2025 09:11 AM",
"total_followers": 0,
"total_followings": 0,
"is_following": 0,
"is_blocked": 0,
"share_location": 0
}
 */
data class EditProfileResponseModel(

    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("contact_number") val contactNumber: String? = null,
    @SerializedName("dob") val dob: String? = null,
    @SerializedName("bio") val bio: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("latitude") val latitude: Int? = null,
    @SerializedName("longitude") val longitude: Int? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("fitness_level") val fitnessLevel: String? = null,
    @SerializedName("interests") val interests: List<String>? = null,
    @SerializedName("profile_photo") val profilePhoto: String? = null,
    @SerializedName("registered_at") val registeredAt: String? = null,
    @SerializedName("total_followers") val totalFollowers: Int? = null,
    @SerializedName("total_followings") val totalFollowings: Int? = null,
    @SerializedName("is_following") val isFollowing: Int? = null,
    @SerializedName("is_blocked") val isBlocked: Int? = null,
    @SerializedName("share_location") val shareLocation: Int? = null

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(contactNumber)
        parcel.writeString(dob)
        parcel.writeString(bio)
        parcel.writeString(gender)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
        parcel.writeString(address)
        parcel.writeString(fitnessLevel)
        parcel.writeStringList(interests)
        parcel.writeString(profilePhoto)
        parcel.writeString(registeredAt)
        parcel.writeValue(totalFollowers)
        parcel.writeValue(totalFollowings)
        parcel.writeValue(isFollowing)
        parcel.writeValue(isBlocked)
        parcel.writeValue(shareLocation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EditProfileResponseModel> {
        override fun createFromParcel(parcel: Parcel): EditProfileResponseModel {
            return EditProfileResponseModel(parcel)
        }

        override fun newArray(size: Int): Array<EditProfileResponseModel?> {
            return arrayOfNulls(size)
        }
    }

}