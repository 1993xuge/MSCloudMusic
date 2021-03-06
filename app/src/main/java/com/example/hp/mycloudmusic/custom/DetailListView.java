package com.example.hp.mycloudmusic.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.hp.mycloudmusic.ui.onLine.ArtistInfoActivity;

public class DetailListView extends ListView {
    public static final String TAG = "DetailListView";
    private ArtistDetailScrollView parentView;
    private ArtistInfoActivity activity;

    private int y;
    private int newY;
    private float mLastY;

    private float preY;
    private float nowY;

    public DetailListView(Context context) {
        super(context);
    }

    public DetailListView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public DetailListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOverScrollMode(ScrollView.OVER_SCROLL_NEVER);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                y = (int) ev.getY();
                activity.getScrollView().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                newY = (int) ev.getY();
                mLastY = newY;
                if((newY-y) > 0){
                    if(getScrollY() == 0){
                        Log.e(TAG, "dispatchTouchEvent: allow");
                        activity.getScrollView().requestDisallowInterceptTouchEvent(false);
                    }else{
                        Log.e(TAG, "dispatchTouchEvent: disallow");
                        activity.getScrollView().requestDisallowInterceptTouchEvent(true);
                    }
                }else if((newY-y)<0){
                    Log.e(TAG, "dispatchTouchEvent: isTop = "+activity.getScrollView().getIsTop());
                    if(activity.getScrollView().getIsTop()){
                        Log.e(TAG, "dispatchTouchEvent: disallow");
                        activity.getScrollView().requestDisallowInterceptTouchEvent(true);
                    }else{
                        Log.e(TAG, "dispatchTouchEvent: allow");
                        activity.getScrollView().requestDisallowInterceptTouchEvent(false);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        y = newY;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                preY = ev.getY();
                Log.e(TAG, "onTouchEvent: down");
                break;
            case MotionEvent.ACTION_MOVE:
                nowY = ev.getY();
                float delta = nowY - preY;
                preY = nowY;
                if(delta>0) {
                    scrollTo(0, (int) (getScrollY() - delta));
                }else if(delta<0){
                    if(activity.getScrollView().getIsTop()) {
                        Log.e(TAG, "onTouchEvent: is Scroll delta=" + delta);
                        scrollTo(0, (int) (getScrollY() - delta));
                    }else{
                        return false;
                    }
                }
                Log.e(TAG, "onTouchEvent: move");
                break;
            case MotionEvent.ACTION_UP:
                if(getScrollY()<0){
                    scrollTo(0,0);
                }
                Log.e(TAG, "onTouchEvent: up");
                break;
        }
        //activity.getScrollView().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(ev);
    }

    public void setParentScrollView(ArtistDetailScrollView scrollView) {
        this.parentView = scrollView;
    }

    public void setActivity(ArtistInfoActivity activity) {
        this.activity = activity;
    }
}
