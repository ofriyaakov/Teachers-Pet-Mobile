package com.example.teacherspet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherspet.adapter.PostsRecyclerAdapter
import com.example.teacherspet.databinding.FragmentMyPostsPageBinding
import com.example.teacherspet.model.Model
import com.example.teacherspet.model.Post

class MyPostsPageFragment : Fragment() {
    private var binding: FragmentMyPostsPageBinding? = null
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
        binding = FragmentMyPostsPageBinding.inflate(inflater, container, false)

        binding?.recyclerView?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager

        adapter = PostsRecyclerAdapter(listOf())

        viewModel.postsByUserId.observe(viewLifecycleOwner) { posts ->
            adapter?.update(posts)
            adapter?.notifyDataSetChanged()
        }

        binding?.swipeToRefresh?.setOnRefreshListener {
            viewModel.refreshAllPosts()
        }

        Model.shared.loadingState.observe(viewLifecycleOwner) { state ->
            binding?.swipeToRefresh?.isRefreshing = state == Model.LoadingState.LOADING
        }

        adapter?.listener = object : OnItemClickListener {
            override fun onItemClick(post: Post?) {
                post?.let {
                    binding?.root?.let {
                        val action = MyPostsPageFragmentDirections
                            .actionMyPostsPageFragmentToEditPostFragment(post.id)
                        findNavController().navigate(action)
                    }
                }
            }

            override fun onPositionClick(position: Int) {
            }

        }

        binding?.recyclerView?.adapter = adapter

        binding?.editProfileBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_myPostsPageFragment_to_editProfileFragment)
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
        getPostsByUserId()
    }

    private fun getPostsByUserId() {
        viewModel.refreshPostsByUserId()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DiscoverPageFragment().apply {
            }
    }
}