package com.digicelplaypngezine;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by suna on 12/1/15.
 */
public class MyViewPager extends ViewPager {

    private boolean scrollable = true;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent me) {
        // Never allow swiping to switch between pages

        if(scrollable){
            return super.onInterceptTouchEvent(me);
        } else{
            return false;
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages

        return  super.onTouchEvent(event);


    }


    public void setScrollable(boolean scrollable) {

        this.scrollable = scrollable;
    }

    public boolean getScrollable(){
        return  scrollable;
    }
}
