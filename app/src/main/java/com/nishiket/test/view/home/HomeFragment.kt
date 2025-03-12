package com.nishiket.test.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nishiket.test.R
import com.nishiket.test.adapter.InterestAdapter
import com.nishiket.test.adapter.PostAdapter
import com.nishiket.test.databinding.FragmentHomeBinding
import com.nishiket.test.model.FeedData
import com.nishiket.test.model.FeedModel
import com.nishiket.test.viewmodel.FeedViewModel

class HomeFragment : Fragment() {
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var feedViewModel: FeedViewModel
    private val posts:MutableList<FeedData> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedViewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        Log.d("TAG", "onViewCreated: vjhjfbvhjfd")
        val sp = context?.getSharedPreferences("login_state", MODE_PRIVATE)
        sp?.getString("auth", "")?.let { feedViewModel.getFeed(it) }
//        val adapter = PostAdapter()
//        feedViewModel.liveData.observe(viewLifecycleOwner,{
//            posts.addAll(it.data)
//            adapter.setPost(posts)
//        })

//        fragmentHomeBinding.rvPost.adapter = adapter
//        fragmentHomeBinding.rvPost.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
    }
}