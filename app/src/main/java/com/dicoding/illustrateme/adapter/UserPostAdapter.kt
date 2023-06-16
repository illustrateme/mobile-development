package com.dicoding.illustrateme.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.illustrateme.R
import com.dicoding.illustrateme.databinding.ItemPortfolioBinding
import com.dicoding.illustrateme.model.PostsItemUser

class UserPostAdapter : RecyclerView.Adapter<UserPostAdapter.UserPostViewHolder>(){
    private val list = ArrayList<PostsItemUser>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(posts: List<PostsItemUser>) {
        list.clear()
        list.addAll(posts)
        notifyDataSetChanged()
    }

    inner class UserPostViewHolder(private val binding: ItemPortfolioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostsItemUser) {
            binding.apply {
                Glide.with(itemView)
                    .load(post.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .dontAnimate()
                    .into(ivPost)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserPostViewHolder {
        val view = ItemPortfolioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserPostViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserPostViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
    
}