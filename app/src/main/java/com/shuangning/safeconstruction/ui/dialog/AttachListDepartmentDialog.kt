package com.shuangning.safeconstruction.ui.dialog

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lxj.xpopup.core.AttachPopupView
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.DepartmentBean
import com.shuangning.safeconstruction.extension.setText
import com.shuangning.safeconstruction.ui.adapter.AttachDepartmentAdapter

/**
 * Created by Chenwei on 2023/10/17.
 */
class AttachListDepartmentDialog(val ctx: Context, val data: MutableList<DepartmentBean>,
                                 val month: Int, val department: String,
                                 val listener: OnItemClickListener<DepartmentBean>):
    AttachPopupView(ctx)  {
    private var attachDepartmentAdapter: AttachDepartmentAdapter?= null
    override fun onCreate() {
        super.onCreate()
        R.id.tv_date.setText(this, "$month 月")
        R.id.tv_department.setText(this, department)
        attachDepartmentAdapter = AttachDepartmentAdapter(data)
        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(ctx)
        rv.adapter = attachDepartmentAdapter
        attachDepartmentAdapter?.setOnItemClickListener(object: OnItemClickListener<DepartmentBean>{
            override fun onItemClick(bean: DepartmentBean, position: Int) {
                data.forEach{
                    it.isSelect = false
                }
                bean.isSelect = true
                listener.onItemClick(bean, position)
                dismiss()
            }
        })
    }
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_attach_list
    }
}