package com.yueqiu.activity.searchmenu.search;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueqiu.R;
import com.yueqiu.adapter.SearchDatingDetailedGridAdapter;
import com.yueqiu.adapter.SearchMateSubFragmentListAdapter;
import com.yueqiu.bean.SearchDatingDetailedAlreadyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author scguo
 *
 * 用于实现约球详细的Activity,即点击约球Fragment当中的ListView当中的任何一个item的时候跳转到的
 * 一个页面，就是约球详情Activity
 *
 */
public class SearchBilliardsDatingActivity extends Activity
{
    private GridView mGridAlreadyFlow;
    private ImageView mUserPhoto;
    private TextView mUserName, mUserGender, mTvFollowNum, mTvTime1, mTvTime2;

    private Button mBtnFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiards_dating);

        mGridAlreadyFlow = (GridView) findViewById(R.id.gridview_search_dating_detailed_already_flow);
        mUserPhoto = (ImageView) findViewById(R.id.img_search_dating_detail_photo);
        mUserName = (TextView) findViewById(R.id.tv_search_dating_detail_nickname);
        mUserGender = (TextView) findViewById(R.id.tv_search_dating_detail_gender);
        mTvFollowNum = (TextView) findViewById(R.id.tv_search_dating_detail_follow_num);
        mTvTime1 = (TextView) findViewById(R.id.tv_search_dating_detailed_time);
        mTvTime2 = (TextView) findViewById(R.id.tv_search_dating_detailed_time_1);

        mBtnFollow = (Button) findViewById(R.id.btn_search_dating_detailed_iwantin);

        // the parameter we need to set are all retrieved from the previous activity

        initGridList();
        mGridAlreadyFlow.setAdapter(new SearchDatingDetailedGridAdapter(this, (ArrayList<SearchDatingDetailedAlreadyBean>) mFollowList));
    }

    private List<SearchDatingDetailedAlreadyBean> mFollowList = new ArrayList<SearchDatingDetailedAlreadyBean>();

    // TODO: the following are just the static test data, and we should
    // TODO: remove them to add the dynamic data
    private void initGridList()
    {
        int i;
        for (i = 0; i < 5; ++i)
        {
            mFollowList.add(new SearchDatingDetailedAlreadyBean("", "温柔的语"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_billiards_dating, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
