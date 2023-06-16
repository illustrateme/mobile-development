package com.dicoding.illustrateme.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.illustrateme.R
import com.dicoding.illustrateme.databinding.ItemPostBinding
import com.dicoding.illustrateme.model.PostsItem

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){
    private val list = ArrayList<PostsItem>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(posts: List<PostsItem>) {
        list.clear()
        list.addAll(posts)
        notifyDataSetChanged()
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostsItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(post)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(post.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .dontAnimate()
                    .into(ivPost)
                tvTitle.text = post.title
                tvName.text = post.author.username
                tvDesignStyle.text = post.designStyle.name
                tvDesignType.text = post.designType.name
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val view = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder((view))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: PostsItem)
    }
}