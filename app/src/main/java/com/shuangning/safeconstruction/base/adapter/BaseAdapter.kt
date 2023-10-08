package com.shuangning.safeconstruction.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter


/**
 * Created by Chenwei on 2023/9/13.
 * TODO:
 */
class BaseAdapter
//<T>(callback: ItemCallback<T>): ListAdapter<T, BaseViewHolder>(callback) {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
//        return BaseViewHolder(parent)
//    }
//
//    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//
//    }
//
//    override fun onBindViewHolder(
//        holder: BaseViewHolder,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
//        super.onBindViewHolder(holder, position, payloads)
//        if(payloads.isNullOrEmpty()) {
//            onBindViewHolder(holder, position)
//        }else{
////            val payload = payloads[0] as Bundle
////            val bean: Student = mDatas.get(position)
////            for (key in payloadl.keySet()) {
////                when (key) {
////                    "KEY_NAME" ->                    //这里可以用payload里的数据，不过data也是新的 也可以用
////                        holder.tv_name.setText(bean.getName())
////
////                    "KEY_PIC" -> holder.iv_pic.setImageResource(payload.getInt(key))
////                    else -> {}
////                }
////            }
//        }
//    }
//}