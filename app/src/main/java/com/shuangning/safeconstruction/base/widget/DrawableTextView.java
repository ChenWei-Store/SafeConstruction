package com.shuangning.safeconstruction.base.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Chenwei on 2023/11/5.
 */
public class DrawableTextView extends AppCompatTextView {
    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

    Drawable mDrawableLeft, mDrawableTop, mDrawableRight, mDrawableBottom;

    public DrawableTextView(@NonNull Context context) {
        super(context);
    }

    public DrawableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (onDrawableClickListener != null) {
                mDrawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                mDrawableTop = getCompoundDrawables()[DRAWABLE_TOP];
                mDrawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                mDrawableBottom = getCompoundDrawables()[DRAWABLE_BOTTOM];
                if (touchDrawableLeft(event)) {
                    onDrawableClickListener.onDrawableLeftClick();
                }
                if (touchDrawableTop(event)) {
                    onDrawableClickListener.onDrawableTopClick();
                }
                if (touchDrawableRight(event)) {
                    onDrawableClickListener.onDrawableRightClick();
                }
                if (touchDrawableBottom(event)) {
                    onDrawableClickListener.onDrawableBottomClick();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private boolean touchDrawableLeft(MotionEvent event) {
        if (mDrawableLeft == null) {
            return false;
        }
        int drawableHeight = mDrawableLeft.getIntrinsicHeight();
        int drawableWidth = mDrawableLeft.getIntrinsicWidth();
        int topOfDrawable = getCompoundPaddingTop();
        int bottomOfDrawable = getHeight() - getCompoundPaddingBottom();
        double drawableCenterY = 0.5 * (bottomOfDrawable - topOfDrawable) + topOfDrawable;
        Rect bounds = new Rect(getPaddingLeft(),
                (int) (drawableCenterY - 0.5 * drawableHeight),
                getPaddingLeft() + drawableWidth,
                (int) (drawableCenterY + 0.5 * drawableHeight)
        );
        return bounds.contains((int) event.getX(), (int) event.getY());
    }

    private boolean touchDrawableTop(MotionEvent event) {
        if (mDrawableTop == null) {
            return false;
        }
        int drawableHeight = mDrawableTop.getIntrinsicHeight();
        int drawableWidth = mDrawableTop.getIntrinsicWidth();
        int leftOfDrawable = getCompoundPaddingLeft();
        int rightOfDrawable = getWidth() - getCompoundPaddingRight();
        double drawableCenterX = 0.5 * (rightOfDrawable - leftOfDrawable) + leftOfDrawable;
        Rect bounds = new Rect((int) (drawableCenterX - 0.5 * drawableWidth),
                getPaddingTop(),
                (int) (drawableCenterX + 0.5 * drawableWidth),
                getPaddingTop() + drawableHeight);
        return bounds.contains((int) event.getX(), (int) event.getY());
    }

    private boolean touchDrawableRight(MotionEvent event) {
        if (mDrawableRight == null) {
            return false;
        }
        int drawableHeight = mDrawableRight.getIntrinsicHeight();
        int drawableWidth = mDrawableRight.getIntrinsicWidth();
        int topOfDrawable = getCompoundPaddingTop();
        int bottomOfDrawable = getHeight() - getCompoundPaddingBottom();
        double drawableCenterY = 0.5 * (bottomOfDrawable - topOfDrawable) + topOfDrawable;
        Rect bounds = new Rect(getWidth() - getPaddingRight() - drawableWidth,
                (int) (drawableCenterY - 0.5 * drawableHeight),
                getWidth() - getPaddingRight(),
                (int) (drawableCenterY + 0.5 * drawableHeight));
        return bounds.contains((int) event.getX(), (int) event.getY());
    }

    private boolean touchDrawableBottom(MotionEvent event) {
        if (mDrawableBottom == null) {
            return false;
        }
        int drawHeight = mDrawableBottom.getIntrinsicHeight();
        int drawWidth = mDrawableBottom.getIntrinsicWidth();
        int leftOfDrawable = getCompoundPaddingLeft();
        int rightOfDrawable = getWidth() - getCompoundPaddingRight();
        double drawableCenterX = 0.5 * (rightOfDrawable - leftOfDrawable) + leftOfDrawable;
        Rect bounds = new Rect((int) (drawableCenterX - 0.5 * drawWidth),
                getHeight() - getPaddingBottom() - drawHeight,
                (int) (drawableCenterX + 0.5 * drawWidth),
                getHeight() - getPaddingBottom());
        return bounds.contains((int) event.getX(), (int) event.getY());
    }

    public interface OnDrawableClickListener {
        void onDrawableLeftClick();
        void onDrawableTopClick();
        void onDrawableRightClick();
        void onDrawableBottomClick();
    }

    public static class BaseOnDrawableClickListener implements OnDrawableClickListener{

        @Override
        public void onDrawableLeftClick() {

        }

        @Override
        public void onDrawableTopClick() {

        }

        @Override
        public void onDrawableRightClick() {

        }

        @Override
        public void onDrawableBottomClick() {

        }
    }
    private OnDrawableClickListener onDrawableClickListener;

    public void setOnDrawableClickListener(OnDrawableClickListener listener) {
        this.onDrawableClickListener = listener;
    }
}
