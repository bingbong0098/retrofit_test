package com.example.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.databinding.RecyclerBinding
import com.example.retrofit.model.ResponseMoviesList

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder> (){
    lateinit var binding: RecyclerBinding
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding= RecyclerBinding.inflate(inflater,parent,false)
        context = parent.context
        return MyViewHolder()
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(differ.currentList[position])

    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder() : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun setData(item: ResponseMoviesList.Data){

            binding.apply {
                Glide.with(itemView).load(item.poster).into(imageView)
                textView.text = item.title
                textView2.text = item.country
            }
        }
    }

    private val differCallBAck = object :DiffUtil.ItemCallback<ResponseMoviesList.Data>(){
        override fun areItemsTheSame(oldItem: ResponseMoviesList.Data, newItem: ResponseMoviesList.Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseMoviesList.Data, newItem: ResponseMoviesList.Data): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallBAck)
}
