package com.example.teacherspet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherspet.adapter.PostsRecyclerAdapter
import com.example.teacherspet.databinding.FragmentDiscoverPageBinding
import com.example.teacherspet.model.Model

class DiscoverPageFragment : Fragment() {
    private var binding: FragmentDiscoverPageBinding? = null
    private var adapter: PostsRecyclerAdapter? = null

    private val viewModel: PostsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoverPageBinding.inflate(inflater, container, false)

        binding?.recyclerView?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager

        // Initialize adapter with empty list (avoid passing null)
        adapter = PostsRecyclerAdapter(listOf())
        binding?.recyclerView?.adapter = adapter // SET THE ADAPTER!

        // Observe the posts LiveData
        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            Log.d("ALL POSTS - LiveData", posts.toString()) // For debugging
            adapter?.update(posts)
            adapter?.notifyDataSetChanged()
        }

        // Swipe to refresh logic
        binding?.swipeToRefresh?.setOnRefreshListener {
            viewModel.refreshAllPosts()
        }

        // Loading state observation
        Model.shared.loadingState.observe(viewLifecycleOwner) { state ->
            binding?.swipeToRefresh?.isRefreshing = state == Model.LoadingState.LOADING
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onResume() {
        super.onResume()
        Log.d("ALL POSTS - 01", "on resome")
        getAllPosts()
    }

    private fun getAllPosts() {
        viewModel.refreshAllPosts()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DiscoverPageFragment().apply {
            }
    }
}