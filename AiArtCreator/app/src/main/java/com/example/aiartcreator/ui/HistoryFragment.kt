package com.example.aiartcreator.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aiartcreator.adapter.HistoryAdapter
import com.example.aiartcreator.model.ImageData
import com.example.aiartcreator.viewmodel.ImageDataViewModel
import com.example.aiartcreator.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    @Inject
    lateinit var historyAdapter : HistoryAdapter

    private lateinit var binding:FragmentHistoryBinding
    val imageModel : ImageDataViewModel by viewModels()
    private    var imageList: MutableList<ImageData> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyAdapter = HistoryAdapter()
        binding.historyRecyclerView.layoutManager = GridLayoutManager(context,2)
        binding.historyRecyclerView.adapter = historyAdapter

        fetchImageList()


        binding.imageView11.setOnClickListener {
            findNavController().navigate(HistoryFragmentDirections.actionHistoryFragmentToHomeFragment())
        }


    }
    @SuppressLint("NotifyDataSetChanged")
    private fun fetchImageList(){

        imageModel.viewModelScope.launch {

            val localImageDatabase = imageModel.getAllImage()

            localImageDatabase.collectLatest {
               imageList.addAll(it)
                historyAdapter.data = imageList
                historyAdapter.notifyDataSetChanged()

            }
        }
    }





}