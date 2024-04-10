package com.wbw.note.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wbw.note.adapter.FileAdapter
import com.wbw.note.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = root.findViewById(com.wbw.note.R.id.recycler_view)
        Log.d("HomeFargment", "onCreateView: StartHomeFragment")

        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))

        homeViewModel.getFileName().observe(getViewLifecycleOwner()) { fileList ->
            val fileAdapter = FileAdapter(fileList, requireContext())
            recyclerView.setAdapter(fileAdapter)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}