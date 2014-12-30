package com.yueqiu;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yueqiu.activity.searchmenu.ActivitiesIssueActivity;
import com.yueqiu.activity.searchmenu.FeedbackActivity;
import com.yueqiu.activity.searchmenu.LoginActivity;
import com.yueqiu.activity.searchmenu.MentionedMeActivity;
import com.yueqiu.activity.searchmenu.MyProfileActivity;
import com.yueqiu.activity.searchmenu.MyfavorCollActivity;
import com.yueqiu.activity.searchmenu.PublishedInfoActivity;
import com.yueqiu.adapter.SlideViewAdapter;
import com.yueqiu.bean.ListItem;
import com.yueqiu.bean.SlideAccountItem;
import com.yueqiu.bean.SlideOtherItem;
import com.yueqiu.fragment.search.BilliardsSearchMateFragment;
import com.yueqiu.view.menudrawer.MenuDrawer;
import com.yueqiu.view.menudrawer.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页的SearchActivity
 */
public class BilliardSearchActivity extends FragmentActivity implements ActionBar.TabListener
{
    private static final String TAG = "BilliardSearchActivity";
    private static final String STATE_MENUDRAWER = "com.yueqiu.menuDrawer";
    private static final int NUM_OF_FRAGMENTS = 5;

    // make the instances of the basic fragment that directly loaded in the BilliardSearchActivity
    private BilliardsSearchMateFragment mMateFragment;

    private ViewPager mViewPager;
    private String[] mTitles;
    private SectionPagerAdapter mPagerAdapter;
    private ActionBar mActionBar;
    private Context mContext;
    private RadioGroup mGroup;
    private RadioButton mNearbyRadio;
    private Intent mIntent = new Intent();
    private MenuDrawer mMenuDrawer;
    private ListView mMenuList;
    private List<ListItem> mItemList = new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;
        initDrawer();
        mActionBar = getActionBar();
        mTitles = new String[]{getString(R.string.search_billiard_mate_str),
                getString(R.string.search_billiard_assist_coauch_str),
                getString(R.string.search_billiard_coauch_str),
                getString(R.string.search_billiard_room_str),
                getString(R.string.search_billiard_dating_str)};
        mViewPager = (ViewPager) findViewById(R.id.search_parent_fragment_view_pager);

        mGroup = (RadioGroup) findViewById(R.id.search_parent_radio_group);
        mNearbyRadio = (RadioButton) findViewById(R.id.first_title_nearby);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.first_title_nearby:
                        break;
                    case R.id.first_title_chatbar:
                        mIntent.setClass(BilliardSearchActivity.this, ChatBarActivity.class);
                        startActivity(mIntent);
                        break;
                    case R.id.first_title_activity:
                        mIntent.setClass(BilliardSearchActivity.this,ActivitiesActivity.class);
                        startActivity(mIntent);
                        break;
                    case R.id.first_title_group:
                        mIntent.setClass(BilliardSearchActivity.this,BilliardGroupActivity.class);
                        startActivity(mIntent);
                        break;
                }

            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mActionBar.removeAllTabs();
        mActionBar.setTitle(getString(R.string.billiard_search));
        setupTabs();
        mNearbyRadio.setChecked(true);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mActionBar.removeAllTabs();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mContext = null;
    }

    private void setupTabs()
    {
        mPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setIcon(R.drawable.slide_menu_icon);
        mActionBar.setTitle(getString(R.string.billiard_search));

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                mActionBar.setSelectedNavigationItem(position);
            }
        });

        ActionBar.Tab tab;

        int i;
        final int count = mPagerAdapter.getCount();
        for (i = 0; i < count; i++)
        {
            tab = mActionBar.newTab()
                    .setText(mPagerAdapter.getPageTitle(i))
                    .setTabListener(this);
            mActionBar.addTab(tab);
        }
    }



    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft)
    {
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft)
    {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft)
    {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter
    {

        public SectionPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int index)
        {
            Fragment fragment = BilliardsSearchMateFragment.newInstance(mContext, "testguoshichao");
            Bundle args = new Bundle();
            args.putString("test", mTitles[index]);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount()
        {
            return NUM_OF_FRAGMENTS;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mTitles[position];
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.billiard_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                mMenuDrawer.toggleMenu();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initDrawer(){
        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND, Position.LEFT, MenuDrawer.MENU_DRAG_WINDOW);
        mMenuDrawer.setContentView(R.layout.activity_billiard_search);
        mMenuDrawer.setMenuView(R.layout.slide_drawer_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SlideAccountItem accountItem = new SlideAccountItem(R.drawable.account,"如果我是一片海",100);
        mItemList.add(accountItem);

        String[] values = new String[]{
                getString(R.string.search_my_profile_str),
                getString(R.string.search_mentioned_me_str),
                getString(R.string.search_my_favor_collection_str),
                getString(R.string.search_my_published_info_str),
                getString(R.string.search_publishing_dating_billiards_info_str),
                getString(R.string.search_feed_back_str),
                getString(R.string.search_logout_str)
        };
        int[] resIds = new int[]{
                R.drawable.e01,
                R.drawable.e02,
                R.drawable.e03,
                R.drawable.e04,
                R.drawable.e05,
                R.drawable.e06,
                R.drawable.e07,
                R.drawable.e08
        };

        SlideOtherItem otherItem;
        for(int i=0;i<values.length;i++){
            otherItem = new SlideOtherItem(resIds[i],values[i],false);
            mItemList.add(otherItem);
        }

        SlideViewAdapter adapter = new SlideViewAdapter(this,mItemList);

        mMenuList = (ListView) findViewById(R.id.menu_drawer_list);
        mMenuList.setAdapter(adapter);

        mMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch(position){
                    case 0:
                        intent.setClass(BilliardSearchActivity.this, LoginActivity.class);
                        break;
                    case 1:
                        intent.setClass(BilliardSearchActivity.this, MyProfileActivity.class);
                        break;
                    case 2:
                        intent.setClass(BilliardSearchActivity.this, MentionedMeActivity.class);
                        break;
                    case 3:
                        intent.setClass(BilliardSearchActivity.this, MyfavorCollActivity.class);
                        break;
                    case 4:
                        intent.setClass(BilliardSearchActivity.this, PublishedInfoActivity.class);
                        break;
                    case 5:
                        intent.setClass(BilliardSearchActivity.this, ActivitiesIssueActivity.class);
                        break;
                    case 6:
                        intent.setClass(BilliardSearchActivity.this, FeedbackActivity.class);
                        break;
                    case 7:
                        break;
                }
                startActivity(intent);
                final int drawerState = mMenuDrawer.getDrawerState();
                if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
                    mMenuDrawer.closeMenu();
                    return;
                }

            }
        });
        mMenuDrawer.peekDrawer();


    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        mMenuDrawer.restoreState(state.getParcelable(STATE_MENUDRAWER));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_MENUDRAWER, mMenuDrawer.saveState());
    }
    @Override
    public void onBackPressed() {
        final int drawerState = mMenuDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mMenuDrawer.closeMenu();
            return;
        }
        super.onBackPressed();
    }

}

























































