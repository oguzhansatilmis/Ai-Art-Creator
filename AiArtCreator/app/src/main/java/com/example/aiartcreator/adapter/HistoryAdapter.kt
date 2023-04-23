package com.example.aiartcreator.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aiartcreator.model.ImageData
import com.example.aiartcreator.databinding.ImageRowBinding
import javax.inject.Inject

class HistoryAdapter @Inject constructor() :RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var data : MutableList<ImageData> = mutableListOf()
    class ViewHolder(val binding: ImageRowBinding ) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val byteArray = data[position].image
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)

        holder.binding.historyImage.setImageBitmap(bitmap)
        holder.binding.historyImagePrompt.text = data[position].arg1 + " " + data[position].arg2
    }
    override fun getItemCount(): Int {
       return data.size
    }
}