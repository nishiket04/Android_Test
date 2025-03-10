package com.nishiket.test.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

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
data class EditProfileModel(
    val address: String = "",
    val bio: String = "",
    val contact_number: String = "",
    val dob: String = "",
    val email: String = "",
    val fitness_level: String = "",
    val gender: String = "",
    val interests: List<String>? = emptyList(),
    val is_blocked: Int = 0,
    val is_following: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val name: String = "",
    val profile_photo: String = "",
    val registered_at: String = "",
    val share_location: Int = 0,
    val total_followers: Int = 0,
    val total_followings: Int = 0,
    val username: String = ""
)

