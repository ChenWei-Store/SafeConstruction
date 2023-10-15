package com.shuangning.safeconstruction.base.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Chenwei on 2023/10/14.
 */
public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration  {
    private int mSpanCount;//横条目数量
    private int mRowSpacing;//行间距
    private int mColumnSpacing;// 列间距
    private boolean isSkipFirst = false;

    /**
     * @param spanCount     列数
     * @param rowSpacing    行间距
     * @param columnSpacing 列间距
     */
    public GridSpaceItemDecoration(int spanCount, int rowSpacing, int columnSpacing, boolean isSkipFirst) {
        this.mSpanCount = spanCount;
        this.mRowSpacing = rowSpacing;
        this.mColumnSpacing = columnSpacing;
        this.isSkipFirst = isSkipFirst;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // 获取view 在adapter中的位置。
        int realPosition = isSkipFirst ? position - 1 : position;

        int column = realPosition % mSpanCount; // view 所在的列

        outRect.left = column * mColumnSpacing / mSpanCount; // column * (列间距 * (1f / 列数))
        outRect.right = mColumnSpacing - (column + 1) * mColumnSpacing / mSpanCount; // 列间距 - (column + 1) * (列间距 * (1f /列数))

        // 如果position > 行数，说明不是在第一行，则不指定行高，其他行的上间距为 top=mRowSpacing
        if (realPosition >= mSpanCount) {
            outRect.top = mRowSpacing; // item top
        }

    }

}
