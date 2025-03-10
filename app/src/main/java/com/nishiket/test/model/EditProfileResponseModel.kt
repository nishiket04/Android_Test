package com.nishiket.test.model

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
    val address: Any,
    val bio: Any,
    val contact_number: String,
    val dob: String,
    val email: String,
    val fitness_level: Any,
    val gender: Any,
    val id: Int,
    val interests: Any,
    val is_blocked: Int,
    val is_following: Int,
    val latitude: Int,
    val longitude: Int,
    val name: String,
    val profile_photo: String,
    val registered_at: String,
    val share_location: Int,
    val total_followers: Int,
    val total_followings: Int,
    val username: String
)