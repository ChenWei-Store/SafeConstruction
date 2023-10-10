package com.shuangning.safeconstruction.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.shuangning.safeconstruction.base.BaseFragment
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.HomeBean
import com.shuangning.safeconstruction.databinding.FragmentHomeBinding
import com.shuangning.safeconstruction.manager.HomeItemManager
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.HomeAdapter

/**
 * Created by Chenwei on 2023/10/7.
 */
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    private var data: MutableList<HomeBean> = mutableListOf()
    private var adapter: HomeAdapter?= null
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding? {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        adapter = HomeAdapter(data)
        val layoutManager = GridLayoutManager(activity, 4)
        layoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                if(position == 0){
                    return 4
                }
                return 1
            }

        }
        binding?.rvHome?.layoutManager = layoutManager
        binding?.rvHome?.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    override fun initData() {
        val items = HomeItemManager.getData()
        data.add(HomeBean(type = HEADER))
        data.addAll(items)
    }

    override fun initListener() {
        adapter?.setOnItemClickListener(object: OnItemClickListener<HomeBean>{
            override fun onItemClick(data: HomeBean, position: Int) {
                activity?.apply {
                    StartActivityManager.startToTakePhotosOfDangers(this)
                }
            }
        })

    }

    override fun observeViewModel() {
    }
}