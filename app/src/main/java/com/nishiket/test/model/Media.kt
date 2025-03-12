package com.nishiket.test.model

import com.google.gson.annotations.SerializedName

data class Media(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("path") var path: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null

)