package com.yueqiu.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yueqiu.R;

import java.lang.reflect.Method;

/**
 * Created by wangyun on 14/12/19.
 */
public class ActionBarDrawToggle extends android.support.v4.app.ActionBarDrawerToggle {

    private static final String TAG = ActionBarDrawToggle.class.getName();

    protected Activity mActivity;
    protected DrawerLayout mDrawLayout;

    protected int mOpenDrawerContentDescRes;
    protected int mCloseDrawerContentDescRes;
    protected DrawerArrowDrawble mDrawerImage;
    protected boolean mAnimateEnabled;

    public ActionBarDrawToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    public ActionBarDrawToggle(Activity activity,DrawerLayout drawerLayout,DrawerArrowDrawble drawerImage,
                               int openDrawerContentDescRes,int closeDrawerContentDescRes){
        super(activity,drawerLayout, R.drawable.ic_drawer,openDrawerContentDescRes,closeDrawerContentDescRes);
        mActivity = activity;
        mDrawLayout = drawerLayout;
        mOpenDrawerContentDescRes = openDrawerContentDescRes;
        mCloseDrawerContentDescRes = closeDrawerContentDescRes;
        mDrawerImage = drawerImage;
        mAnimateEnabled = true;
    }

    public void syncState(){
        if(mDrawerImage == null){
            super.syncState();;
            return;
        }
        if(mAnimateEnabled){
            if(mDrawLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerImage.setProgress(1.f);
            }else{
                mDrawerImage.setProgress(0.f);
            }
        }
        setActionBarUpIndicator();
        setActionBarDescription();
    }


    public void setDrawerIndicatorEnabled(boolean enable){
        if(mDrawerImage == null){
            super.setDrawerIndicatorEnabled(enable);
            return;
        }
        setActionBarUpIndicator();
        setActionBarDescription();
    }
    public boolean isDrawerIndicatorEnabled(){
        if(mDrawerImage == null){
            return super.isDrawerIndicatorEnabled();
        }
        return true;
    }
    public void onConfigurationChanged(Configuration newConfig){
        if(mDrawerImage == null){
            super.onConfigurationChanged(newConfig);
            return;
        }
        syncState();
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        if(mDrawerImage == null) {
            super.onDrawerSlide(drawerView, slideOffset);
            return;
        }
        if(mAnimateEnabled){
            mDrawerImage.setVertivalMirror(!mDrawLayout.isDrawerOpen(GravityCompat.START));
            mDrawerImage.setProgress(slideOffset);
        }
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        if (mDrawerImage == null) {
            super.onDrawerOpened(drawerView);
            return;
        }
        if (mAnimateEnabled) {
            mDrawerImage.setProgress(1.f);
        }
        setActionBarDescription();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        if (mDrawerImage == null) {
            super.onDrawerClosed(drawerView);
            return;
        }
        if (mAnimateEnabled) {
            mDrawerImage.setProgress(0.f);
        }
        setActionBarDescription();
    }

    protected void setActionBarUpIndicator() {
        if (mActivity != null) {
            try {
                Method setHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator",
                        Drawable.class);
                setHomeAsUpIndicator.invoke(mActivity.getActionBar(), mDrawerImage);
                return;
            } catch (Exception e) {
                Log.e(TAG, "setActionBarUpIndicator error", e);
            }

            final View home = mActivity.findViewById(android.R.id.home);
            if (home == null) {
                return;
            }

            final ViewGroup parent = (ViewGroup) home.getParent();
            final int childCount = parent.getChildCount();
            if (childCount != 2) {
                return;
            }

            final View first = parent.getChildAt(0);
            final View second = parent.getChildAt(1);
            final View up = first.getId() == android.R.id.home ? second : first;

            if (up instanceof ImageView) {
                ImageView upV = (ImageView) up;
                upV.setImageDrawable(mDrawerImage);
            }
        }
    }

    protected void setActionBarDescription() {
        if (mActivity != null && mActivity.getActionBar() != null) {
            try {
                Method setHomeActionContentDescription = ActionBar.class.getDeclaredMethod(
                        "setHomeActionContentDescription", Integer.TYPE);
                setHomeActionContentDescription.invoke(mActivity.getActionBar(),
                        mDrawLayout.isDrawerOpen(GravityCompat.START) ? mOpenDrawerContentDescRes : mCloseDrawerContentDescRes);
                if (Build.VERSION.SDK_INT <= 19) {
                    mActivity.getActionBar().setSubtitle(mActivity.getActionBar().getSubtitle());
                }
            } catch (Exception e) {
                Log.e(TAG, "setActionBarUpIndicator", e);
            }
        }
    }
    public void setAnimateEnabled(boolean enabled){
        this.mAnimateEnabled = enabled;
    }
    public boolean isAnimateEnabled(){
        return this.mAnimateEnabled;
    }
}
