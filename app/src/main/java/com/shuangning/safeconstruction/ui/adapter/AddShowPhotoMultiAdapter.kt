package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.util.SmartGlideImageLoader
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.base.ShowPhoto
import com.shuangning.safeconstruction.databinding.ItemAddPhotoBinding
import com.shuangning.safeconstruction.databinding.ItemShowPhotoBinding
import com.shuangning.safeconstruction.utils2.ImageLoader


/**
 * Created by Chenwei on 2023/10/17.
 */
const val ADD_PHOTO = 0
const val SHOW_PHOTO = 1
class AddShowPhotoMultiAdapter(data: MutableList<ItemViewType>): CommonBaseMultiAdapter<ItemViewType, ViewBinding>(data) {
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
                val content = item as? ShowPhoto
                content?.let {
                    binding.ivDelete.visibility = if (it.isShowDelete){
                        View.VISIBLE
                    }else{
                        View.GONE
                    }
                    ImageLoader.loadUrl(ctx, content.url, binding.iv)
                    binding.ivDelete.setOnClickListener {
                        listener?.onDelete(position)
                    }
                    binding.iv.setOnClickListener {
                        listener?.onImageClick()
                        XPopup.Builder(ctx)
                            .asImageViewer(binding.iv, content.url, SmartGlideImageLoader())
                            .show()
                    }
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
        }else {
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

        fun onImageClick()
    }

    fun setListener(listener: OnPhotoActionClickListener){
        this.listener = listener
    }
}