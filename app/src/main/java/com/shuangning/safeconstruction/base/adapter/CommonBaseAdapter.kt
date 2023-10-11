package com.shuangning.safeconstruction.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by Chenwei on 2023/10/10.
 */
abstract class CommonBaseAdapter<T: ItemViewType, V: ViewBinding>(val data: MutableList<T>): RecyclerView.Adapter<BaseViewHolder<V>>() {
    private var onItemClick: OnItemClickListener<T>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        val binding = getViewBinding(LayoutInflater.from(parent.context), parent, viewType)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        val item = data[position]
        holder.binding.root.setOnClickListener {
            onItemClick?.onItemClick(item, position)
        }
        onBindViewHolder(holder.binding, item, position, holder.binding.root.context)
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }
    abstract fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): V
    abstract fun onBindViewHolder(binding: V, item: T, position: Int, ctx: Context)

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>){
        this.onItemClick = onItemClickListener
    }
}