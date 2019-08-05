package cn.moecity.virtualpet;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Tony on 2018/1/27.
 */
public class CustomView extends LinearLayout {

    private ViewDragHelper dragHelper;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        dragHelper = ViewDragHelper.create(this, (float) 1.0,
                new ViewDragHelper.Callback() {

                    // 尝试捕获要拖动的子View 这里一定要返回true
                    @Override
                    public boolean tryCaptureView(View view, int pointerId) {


                        return true;
                    }


                    @Override
                    public int clampViewPositionHorizontal(View child,
                                                           int left, int dx) {

                        if (getPaddingLeft() > left) {
                            return getPaddingLeft();
                        }
                        if (left > (getWidth() - getPaddingLeft()
                                - getPaddingRight() - child.getWidth())
                                + getPaddingLeft()) {
                            return (getWidth() - getPaddingLeft()
                                    - getPaddingRight() - child.getWidth())
                                    + getPaddingLeft();
                        }

                        if (getWidth() - child.getWidth() < left) {
                            return getWidth() - child.getWidth();
                        }
                        return left;
                    }

                    @Override
                    public int clampViewPositionVertical(View child, int top,
                                                         int dy) {

                        if (getPaddingTop() > top) {
                            return getPaddingTop();
                        }

                        if (getHeight() - child.getHeight() < top) {
                            return getHeight() - child.getHeight();
                        }

                        return top;
                    }

                    /**
                     * 当拖拽到状态改变时回调
                     *
                     * @params 新的状态
                     */
                    @Override
                    public void onViewDragStateChanged(int state) {
                        switch (state) {
                            // 正在被拖拽
                            case ViewDragHelper.STATE_DRAGGING:
                                break;
                            // view没有被拖拽或者正在进行fling
                            case ViewDragHelper.STATE_IDLE:
                                break;
                            // fling完毕后被放置到一个位置
                            case ViewDragHelper.STATE_SETTLING:
                                break;
                        }

                        super.onViewDragStateChanged(state);
                    }

                });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_DOWN:
                dragHelper.cancel();
                break;
        }
        /**
         * 检查是否可以拦截touch事件 如果onInterceptTouchEvent可以return true 则这里return true
         */
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 处理拦截到的事件 这个方法会在返回前分发事件
         */
        dragHelper.processTouchEvent(event);

        return true;
    }

}