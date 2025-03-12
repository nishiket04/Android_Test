package com.nishiket.test.model

import com.google.gson.annotations.SerializedName

data class FeedModel(
    @SerializedName("data") var data: ArrayList<FeedData> = arrayListOf(),
    @SerializedName("meta") var meta: Meta? = Meta("Something went wrong")
)
