package com.nishiket.test.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.nishiket.test.R
import com.nishiket.test.databinding.IntrestLayoutBinding
import com.nishiket.test.model.InterestModel
import kotlin.math.log

class InterestAdapter(val list: List<InterestModel>) :
    RecyclerView.Adapter<InterestAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(val binding: IntrestLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val intrestLayoutBinding =
            IntrestLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(intrestLayoutBinding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val interest = list[holder.adapterPosition]
        holder.binding.ivIntrest.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                interest.image
            )
        )
        holder.binding.txtInterest.setText(interest.interest)

        if (interest.isSelected) {
            holder.binding.ivIntrest.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.white
                ), PorterDuff.Mode.SRC_IN
            )
            holder.binding.clIntrest.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.intrest_selected_drawable))
            holder.binding.txtInterest.setTextColor(ContextCompat.getColor(context,R.color.white))
        } else {
            holder.binding.ivIntrest.clearColorFilter()
            holder.binding.clIntrest.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.edt_drawable))
            holder.binding.txtInterest.setTextColor(ContextCompat.getColor(context,R.color.black))
        }

        holder.binding.clIntrest.setOnClickListener {
            interest.isSelected = interest.isSelected.not()
            notifyItemChanged(holder.adapterPosition)
        }
    }

}
