package com.shuangning.safeconstruction.ui.dialog

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.lxj.xpopup.core.AttachPopupView
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.bean.other.ContentSelectTypeBean
import com.shuangning.safeconstruction.ui.adapter.SelectTypeMultiAdapter
import com.shuangning.safeconstruction.utils.ScreenUtil

/**
 * Created by Chenwei on 2023/10/14.
 */
class SelectTypeDialog(ctx: Context, val data: MutableList<IItemViewType>, val block: (index: Int)-> Unit): AttachPopupView(ctx) {
    private var selectTypeAdapter: SelectTypeMultiAdapter?= null
    override fun onCreate() {
        super.onCreate()
        initRv()
    }

    private fun initRv(){
        selectTypeAdapter = SelectTypeMultiAdapter(data)
        val rv = findViewById<RecyclerView>(R.id.rv)
        val layoutManager = GridLayoutManager(context, 3)
        val decoration: GridSpaceItemDecoration
        if (data.size > 0){
            val type = data[0].getItemType()
            if(type == HEADER){
                layoutManager.spanSizeLookup = object: SpanSizeLookup(){
                    override fun getSpanSize(position: Int): Int =
                        if (position == 0){
                            3
                        }else{
                            1
                        }
                }
                decoration = GridSpaceItemDecoration(3, ScreenUtil.dp2px(4f),ScreenUtil.dp2px(8f), true)
            }else{
                decoration = GridSpaceItemDecoration(3, ScreenUtil.dp2px(4f),ScreenUtil.dp2px(8f), false)
            }
            rv.layoutManager = layoutManager
            rv.addItemDecoration(decoration)
            rv.adapter = selectTypeAdapter
        }
        selectTypeAdapter?.setOnItemClickListener(object: OnItemClickListener<IItemViewType>{
            override fun onItemClick(data: IItemViewType, position: Int) {
                if (data.getItemType() == HEADER){
                    return
                }
                this@SelectTypeDialog.data.forEachIndexed { index, item ->
                    if (item is ContentSelectTypeBean){
                        item.isSelected = index == position
                    }
                }
                block(position)
                dismiss()
            }

        })
    }
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_select_type
    }
}