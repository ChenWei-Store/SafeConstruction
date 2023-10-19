package com.shuangning.safeconstruction.ui.dialog

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.lxj.xpopup.core.AttachPopupView
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.ui.adapter.SelectTypeAdapter
import com.shuangning.safeconstruction.utils.ScreenUtil

/**
 * Created by Chenwei on 2023/10/14.
 */
class SelectTypeDialog(ctx: Context, val data: MutableList<IItemViewType>): AttachPopupView(ctx) {
    private var selectTypeAdapter: SelectTypeAdapter?= null
    override fun onCreate() {
        super.onCreate()
        setFullWidth()
        initRv()
    }

    private fun setFullWidth(){
        findViewById<LinearLayout>(R.id.ll).layoutParams.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }
    private fun initRv(){
        selectTypeAdapter = SelectTypeAdapter(data)
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
    }
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_select_type
    }
}