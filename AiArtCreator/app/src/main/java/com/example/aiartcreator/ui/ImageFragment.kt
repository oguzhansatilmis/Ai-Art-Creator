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
import com.example.aiartcreator.model.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.Exception

@AndroidEntryPoint
class ImageFragment : Fragment() {
    private lateinit var arg1: String
    private lateinit var arg2: String
    private var byteArray: ByteArray? = null
    private lateinit var bitmap: Bitmap
    private lateinit var binding: FragmentImageBinding
    private val imageViewModel: ImageDataViewModel by viewModels()

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



        println("------")









        fetchImage()

        println(arg1)
        println(arg2)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.downloadButton.setOnClickListener {


            if (byteArray != null) {
                imageViewModel.viewModelScope.launch {




                        val saveImage = ImageData(arg1 = arg1, arg2 = arg2, image = byteArray!!)
                        saveHistoryImage(saveImage)


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

            if (byteArray == null){

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("NULLLLLLL")
                builder.setMessage("Şuan else bloğunun içindeyim ")
                builder.setPositiveButton("Okay") { dialog, which ->
                    // Butona tıklandığında yapılacak işlemler
                    dialog.dismiss() // Alert dialog penceresini kapat
                }
                builder.setCancelable(true)
                val dialog = builder.create()
                dialog.show()

            }




        }
        binding.backButton.setOnClickListener {
            findNavController().navigate(ImageFragmentDirections.actionImageFragmentToHomeFragment())
        }

    }

    private fun saveHistoryImage(imageData: ImageData) {
        imageViewModel.viewModelScope.launch {
            imageViewModel.saveImage(imageData = imageData)
        }
    }



    private fun fetchImage() {
        imageViewModel.viewModelScope.launch {
            imageViewModel.fetchData(arg1)

            val imageResponse = imageViewModel.imageResponseState
            imageResponse.collectLatest {
                when (it) {
                    is Result.Success -> {
                        val data = it.data
                        println("basarılı")

                        val responseBody = data.body()
                        responseBody?.let {
                            bitmap =
                                responseBody.convertResponseBodyToBitmap(responseBody = responseBody)!!
                                byteArray = bitmap.convertBitmapToByteArray(bitmap)!!
                            binding.aiImage.setImageBitmap(bitmap)
                        }
                    }

                    is Result.Loading -> {
                        println("Loading")
                    }

                    is Result.Error -> {
                        val message = it.message
                        println("error $message")
                    }
                }
            }

        }
    }

    fun ResponseBody.convertResponseBodyToBitmap(responseBody: ResponseBody): Bitmap? {

        return try {
            val byteArray = responseBody.bytes()
            val inputStream = ByteArrayInputStream(byteArray)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }


    }

    fun Bitmap.convertBitmapToByteArray(bitmap: Bitmap): ByteArray? {

        return try {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            return stream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}