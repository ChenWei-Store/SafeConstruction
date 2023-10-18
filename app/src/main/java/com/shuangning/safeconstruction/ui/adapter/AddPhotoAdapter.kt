package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ItemAddPhotoBinding
import com.shuangning.safeconstruction.databinding.ItemShowPhotoBinding

/**
 * Created by Chenwei on 2023/10/17.
 */
const val ADD_PHOTO = 0
const val SHOW_PHOTO = 1
class AddPhotoAdapter(data: MutableList<ItemViewType>): CommonBaseAdapter<ItemViewType, ViewBinding>(data) {
    private var maxCount = 9
   private var listener: OnPhotoActionClickListener?= null
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: ItemViewType,
        position: Int,
        ctx: Context
    ) {
        when(binding){
            is ItemAddPhotoBinding->{
                binding.ivAdd.setOnClickListener {
                    listener?.onAdd()
                }
            }
            is ItemShowPhotoBinding->{
                binding.ivDelete.setOnClickListener {
                    listener?.onDelete(position)
                }
            }
        }

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return if (ADD_PHOTO == viewType){
            ItemAddPhotoBinding.inflate(inflater, parent, false)
        }else{
            ItemShowPhotoBinding.inflate(inflater, parent, false)
        }
    }

    override fun getItemCount(): Int {
        return if (data.size > maxCount){
            return maxCount
        }else{
            return data.size
        }
    }

    interface OnPhotoActionClickListener{
        fun onAdd()
        fun onDelete(position: Int)
    }

    fun setListener(listener: OnPhotoActionClickListener){
        this.listener = listener
    }
}