package com.example.aiartcreator.ui

import android.app.AlertDialog
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.aiartcreator.model.ImageData
import com.example.aiartcreator.viewmodel.ImageDataViewModel
import com.example.aiartcreator.databinding.FragmentImageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class ImageFragment : Fragment() {
    private lateinit var arg1 :String
    private lateinit var arg2 :String
    private lateinit var byteArray: ByteArray
    private lateinit var bitmap :Bitmap
    private lateinit var binding: FragmentImageBinding
    private val imageViewModel: ImageDataViewModel by  viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        arg1 = arguments?.getString("arg1").toString()
        arg2 = arguments?.getString("arg2").toString()
        println(arg1)
        println(arg2)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        viewLifecycleOwner.lifecycleScope.  launch {
            binding.aiImage.visibility = View.GONE
            imageViewModel.getTextToImage("${arg1}")
                .collect {
                    if (it.isSuccessful) {
                        byteArray = it.body()?.bytes()!!
                        if (byteArray != null) {
                             bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                            println("adasdasda")
                            if(it.isSuccessful){
                                binding.infoTV.text = arg1 + "," + arg2
                                binding.aiImage.setImageBitmap(bitmap)
                                binding.aiImage.visibility = View.VISIBLE
                            }
                        }
                    }
                    else{
                        println("basarısız")
                    }

                }
        }
        binding.downloadButton.setOnClickListener {
            val saveImage = ImageData(arg1 = arg1, arg2 = arg2,image=byteArray)
            imageViewModel.viewModelScope.launch {
                imageViewModel.insert(saveImage)
            }

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Artwork saved succesfully!")
            builder.setMessage("The Al-generated artwork you created has been succesfully saved to photos.")
            builder.setPositiveButton("Okay") { dialog, which ->
                // Butona tıklandığında yapılacak işlemler
                dialog.dismiss() // Alert dialog penceresini kapat
            }
            builder.setCancelable(true)
            val dialog = builder.create()
            dialog.show()

            val bitmap = bitmap
            val fileName = "myImageDescription.jpg"
// Save bitmap to device gallery
            val resolver = requireActivity().contentResolver
            val imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val newImage = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }
            val imageUri = resolver.insert(imageCollection, newImage)
            if (imageUri != null) {
                resolver.openOutputStream(imageUri).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
            }
        }
        binding.backButton.setOnClickListener {
            findNavController().navigate(ImageFragmentDirections.actionImageFragmentToHomeFragment())
        }
    }


}