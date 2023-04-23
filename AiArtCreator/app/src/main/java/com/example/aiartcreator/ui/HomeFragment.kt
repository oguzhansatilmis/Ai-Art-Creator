package com.example.aiartcreator.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.aiartcreator.R
import com.example.aiartcreator.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private  var arg1 = ""
    private  var arg2  = ""
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val buttons = listOf(surrealist, steampunk, rickandmorty, renaissancepainting, portraitphoto)
            val categoryTextViews = listOf(surrealistTV, steampunkTV, rickmortyTV, renaissanceTV, portraitTV)

            editTextTextPersonName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val arg1 = s.toString()
                    textView17.setOnClickListener {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToImageFragment(arg1, arg2))
                    }
                }
            })

            buttons.forEachIndexed { index, button ->
                button.setOnClickListener {
                    reset()
                    val categoryTextView = categoryTextViews[index]
                    arg2 = categoryTextView.text.toString()
                    button.text = "Using"
                    button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    button.setBackgroundResource(R.drawable.homeusing)
                }
            }
        }
        binding.imageView6.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHistoryFragment())
        }

        binding.imageView7.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
        }
    }
    private fun reset() {
        binding.apply {
            val buttons = listOf(surrealist, steampunk, rickandmorty, renaissancepainting, portraitphoto)
            buttons.forEach { button ->
                button.text = "Use"
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                button.setBackgroundResource(R.drawable.homeuse)
            }
        }
    }
}