package com.nishiket.test.model

import com.google.gson.annotations.SerializedName

data class Post(

    @SerializedName("post_id") var postId: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("media") var media: ArrayList<Media> = arrayListOf(),
    @SerializedName("is_liked") var isLiked: Int? = null,
    @SerializedName("likes_count") var likesCount: Int? = null,
    @SerializedName("is_saved") var isSaved: Int? = null,
    @SerializedName("saved_count") var savedCount: Int? = null,
    @SerializedName("comments_count") var commentsCount: Int? = null

)
