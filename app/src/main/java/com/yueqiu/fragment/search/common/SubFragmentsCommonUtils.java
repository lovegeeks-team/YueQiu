package com.yueqiu.fragment.search.common;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yueqiu.R;
import com.yueqiu.adapter.SearchMateFragmentViewPagerImgAdapter;

/**
 * Created by scguo on 15/1/4.
 * <p/>
 * 用于实现所有的位于SearchActivity当中的子Fragment的实现的一些公有的方法的实现，
 * 在这里我们主要是实现加载ViewPager(因为这个ViewPager在每一个子Fragment当中都有)，
 * 实现PopupWindow的加载
 */
public class SubFragmentsCommonUtils
{

    // 定义用于处理从Fragment的ListView点击之后切换到具体的Activity时的切换过程
    // 以下是用于球厅Fragment当中需要传输的数据的详细的key值
    public static final String KEY_BUNDLE_SEARCH_ROOM_FRAGMENT = "searchRoomFragment";
    public static final String KEY_ROOM_FRAGMENT_PRICE = "roomPrice";
    public static final String KEY_ROOM_FRAGMENT_TAG = "roomTag";
    public static final String KEY_ROOM_FRAGMENT_ADDRESS = "roomAddress";
    public static final String KEY_ROOM_FRAGMENT_PHONE = "roomPhone";
    public static final String KEY_ROOM_FRAGMENT_PHOTO = "roomPhoto";
    public static final String KEY_ROOM_FRAGMENT_NAME = "roomName";
    public static final String KEY_ROOM_FRAGMENT_LEVEL = "roomLevel";

    // 以下是用于球友Fragment当中需要传输的数据的详细的key值
    public static final String KEY_BUNDLE_SEARCH_MATE_FRAGMENT = "searchMateFragment";

    // 以下是用于约球Fragment当中需要传输的数据的详细的key值
    public static final String KEY_BUNDLE_SEARCH_DATING_FRAGMENT = "searchDatingFragment";
    public static final String KEY_DATING_FRAGMENT_PHOTO = "datingPhoto";
    public static final String KEY_DATING_FRAGMENT_NAME = "datingName";
    public static final String KEY_DATING_FRAGMENT_GENDER = "datingGender";
    public static final String KEY_DATING_FRAGMENT_FOLLOWNUM = "datingFollowNum";
    public static final String KEY_DATING_PUBLISH_TIME = "datingTime";

    // 以下是用于教练Fragment当中需要传输的数据的详细的key值
    public static final String KEY_BUNDLE_SEARCH_COAUCH_FRAGMENT = "searchCoauchFragment";

    // 以下是用于助教Fragment当中需要传输的数据的详细的key值
    public static final String KEY_BUNDLE_SEARCH_ASSISTCOAUCH_FRAGMENT = "searchAssistCoauchFragment";


    private SubFragmentsCommonUtils(){}

    /**
     * @param context
     * @param anchorView  当前的popupWindow是依附于具体的哪一个View组件
     * @param layoutResId 用于显示当前的PopupWindow的具体的布局文件
     */
    public static void initPopupWindow(Context context, View anchorView, int layoutResId)
    {
        final int popupWidth = LinearLayout.LayoutParams.MATCH_PARENT;
        final int popupHeight = LinearLayout.LayoutParams.WRAP_CONTENT;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupWindowLayout = layoutInflater.inflate(layoutResId, null);

        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(popupWindowLayout);
        popupWindow.setWidth(popupWidth);
        popupWindow.setHeight(popupHeight);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.popup_window_bg));

        popupWindow.showAsDropDown(anchorView);


    }



    private static void setImgBackground(int selectedItem)
    {
        int i;
        final int size = sPagerIndicatorImgList.length;
        for (i = 0; i < size; ++i)
        {
            if (i == selectedItem)
            {
                sPagerIndicatorImgList[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else
            {
                sPagerIndicatorImgList[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }

    }

    private static ImageView[] sPagerIndicatorImgList;
    private static ImageView[] sPagerImgArr;

    private static LinearLayout sGalleryIndicatorGroup;
    private static SearchMateFragmentViewPagerImgAdapter sGalleryImgAdapter;

    private static int[] sPagerImgResArr;
    private static ViewPager sImgGalleryViewPager;

    /**
     *
     * @param context
     * @param parentView 用于加载ViewPager的父View
     * @param viewPagerId ViewPager在parentView当中的id值
     * @param galleryIndiGroupId ViewPager底部的indicator所在的布局中的id值
     */
    public static void initViewPager(Context context, View parentView, final int viewPagerId, final int galleryIndiGroupId)
    {
        // TODO: 以下仅仅是测试数据，在测试接口的时候就删除掉
        sPagerImgResArr = new int[]{R.drawable.test_pager_1, R.drawable.test_pager_2, R.drawable.test_pager_3, R.drawable.test_pager_4};

        sImgGalleryViewPager = (ViewPager) parentView.findViewById(viewPagerId);
        sGalleryIndicatorGroup = (LinearLayout) parentView.findViewById(galleryIndiGroupId);

        sPagerIndicatorImgList = new ImageView[sPagerImgResArr.length];

        final int size = sPagerIndicatorImgList.length;

        ImageView indicatorView;
        int i;
        for (i = 0; i < size; ++i)
        {
            indicatorView = new ImageView(context);
            indicatorView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));

            sPagerIndicatorImgList[i] = indicatorView;
            if (i == 0)
            {
                sPagerIndicatorImgList[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else
            {
                sPagerIndicatorImgList[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            params.leftMargin = 5;
            params.rightMargin = 5;

            sGalleryIndicatorGroup.addView(indicatorView, params);
        }

        sPagerImgArr = new ImageView[size];
        int j;
        for (j = 0; j < size; ++j)
        {
            ImageView imgView = new ImageView(context);
            sPagerImgArr[j] = imgView;
            imgView.setBackgroundResource(sPagerImgResArr[j]);
        }

        sGalleryImgAdapter = new SearchMateFragmentViewPagerImgAdapter(sPagerImgArr);
        sImgGalleryViewPager.setAdapter(sGalleryImgAdapter);
        sImgGalleryViewPager.setCurrentItem(sPagerImgArr.length * 100);
        sImgGalleryViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageScrolled(int i, float v, int i2)
            {

            }

            @Override
            public void onPageSelected(int i)
            {
                setImgBackground(i % sPagerImgArr.length);

            }

            @Override
            public void onPageScrollStateChanged(int i)
            {

            }
        });
    }
}
