package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.LEVEL_ONE
import com.shuangning.safeconstruction.base.adapter.LEVEL_TWO
import com.shuangning.safeconstruction.bean.other.TermsOfReferenceLevelOne
import com.shuangning.safeconstruction.bean.other.TermsOfReferenceLevelThree
import com.shuangning.safeconstruction.bean.other.TermsOfReferenceLevelTwo
import com.shuangning.safeconstruction.databinding.ItemTermsOfReferenceHeaderBinding
import com.shuangning.safeconstruction.databinding.ItemTermsOfReferenceLevelOneBinding
import com.shuangning.safeconstruction.databinding.ItemTermsOfReferenceLevelThreeBinding
import com.shuangning.safeconstruction.databinding.ItemTermsOfReferenceLevelTwoBinding

/**
 * Created by Chenwei on 2023/10/18.
 */

class TermsOfReferencesMultiAdapter(data: MutableList<IItemViewType>):
    CommonBaseMultiAdapter<IItemViewType, ViewBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: IItemViewType,
        position: Int,
        ctx: Context
    ) {
        when(binding){
            is ItemTermsOfReferenceHeaderBinding->{
            }

            is ItemTermsOfReferenceLevelOneBinding->{
                binding.tv.text = (item as? TermsOfReferenceLevelOne)?.title?:""
                val resId = if((item as? TermsOfReferenceLevelOne)?.isExpand() == true){
                    R.drawable.bottom_triangles
                }else{
                    R.drawable.right_triangles
                }
                binding.iv.setImageResource(resId)
            }

            is ItemTermsOfReferenceLevelTwoBinding->{
                binding.tv.text = (item as? TermsOfReferenceLevelTwo)?.title?:""
                val resId = if((item as? TermsOfReferenceLevelTwo)?.isExpand() == true){
                    R.drawable.bottom_triangles
                }else{
                    R.drawable.right_triangles
                }
                binding.iv.setImageResource(resId)
            }

            is ItemTermsOfReferenceLevelThreeBinding->{
                binding.tv.text = (item as? TermsOfReferenceLevelThree)?.title?:""
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return when (viewType) {
            HEADER -> {
                ItemTermsOfReferenceHeaderBinding.inflate(inflater, parent, false)
            }
            LEVEL_ONE -> {
                ItemTermsOfReferenceLevelOneBinding.inflate(inflater, parent, false)
            }
            LEVEL_TWO ->{
                ItemTermsOfReferenceLevelTwoBinding.inflate(inflater, parent, false)
            }
            else -> {
                ItemTermsOfReferenceLevelThreeBinding.inflate(inflater, parent, false)
            }
        }
    }


}