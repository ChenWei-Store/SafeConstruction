package com.shuangning.safeconstruction.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding

/**
 * Created by Chenwei on 2023/10/1.
 */
abstract class BaseView<VB: ViewBinding>  constructor(
   ctx: Context,
   attrs: AttributeSet? = null,
   defStyleAttr: Int = 0): FrameLayout(ctx, attrs, defStyleAttr) {
   protected var binding: VB ?= null
   init {
      binding = inflateBinding(LayoutInflater.from(ctx))
      initData()
      initView()
      initListener()
   }
   

   protected abstract fun inflateBinding(inflater: LayoutInflater): VB
   protected abstract fun initData()
   protected abstract fun initView()
   protected abstract fun initListener()
//   fun getViewBinding(): VB?{
//      return binding
//   }
}