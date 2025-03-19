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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoverPageBinding.inflate(inflater, container, false)

        binding?.recyclerView?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager

        adapter = PostsRecyclerAdapter(viewModel.posts.value)

        viewModel.posts.observe(viewLifecycleOwner) {
            adapter?.update(it)
            adapter?.notifyDataSetChanged()

//            binding?.progressBar?.visibility = View.GONE
        }

        binding?.swipeToRefresh?.setOnRefreshListener {
            viewModel.refreshAllPosts()
        }

        Model.shared.loadingState.observe(viewLifecycleOwner) { state ->
            binding?.swipeToRefresh?.isRefreshing = state == Model.LoadingState.LOADING
        }

        return binding?.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DiscoverPageFragment().apply {
            }
    }
}