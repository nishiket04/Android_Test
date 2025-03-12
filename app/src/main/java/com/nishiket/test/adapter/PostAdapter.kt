package com.nishiket.test.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.models.SlideModel
import com.nishiket.test.R
import com.nishiket.test.databinding.IntrestLayoutBinding
import com.nishiket.test.databinding.PostLayoutBinding
import com.nishiket.test.model.FeedData
import com.nishiket.test.model.FeedModel

class PostAdapter(val list: MutableList<FeedData> ) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    private lateinit var context: Context
//    private var list: MutableList<FeedData> = mutableListOf()
//
//    fun setPost(list1: List<FeedData>) {
//        list.addAll(list1)
//        notifyDataSetChanged()
//    }

    inner class ViewHolder(val binding: PostLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val postBinding =
            PostLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(postBinding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = list[holder.adapterPosition]

        holder.binding.txtUserName.text = post.name
        holder.binding.txtTime.text = post.createdAt
        holder.binding.txtDescription.text = post.post?.description
        Glide.with(context).load(post.profilePhoto).into(holder.binding.userPostImage)
    }
}