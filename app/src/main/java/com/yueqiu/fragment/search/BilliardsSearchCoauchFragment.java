package com.yueqiu.fragment.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.yueqiu.R;
import com.yueqiu.adapter.SearchCoauchSubFragmentListAdapter;
import com.yueqiu.adapter.SearchPopupBaseAdapter;
import com.yueqiu.bean.SearchCoauchSubFragmentCoauchBean;
import com.yueqiu.constant.HttpConstants;
import com.yueqiu.fragment.search.common.SubFragmentsCommonUtils;
import com.yueqiu.util.HttpUtil;
import com.yueqiu.util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by scguo on 14/12/30.
 * <p/>
 * 用于SearchActivity当中的教练子Fragment的实现
 */
@SuppressLint("ValidFragment")
public class BilliardsSearchCoauchFragment extends Fragment
{
    private static final String TAG = "BilliardsSearchAssistCoauchFragment";

    private static final String FRAGMENT_TAG = "BilliardsSearchAssistCoauchFragment";

    // 这是用于整个BilliardsSearchCoauchFragment当中的layout view
    private View mView;
    private static Button sBtnAbility, sBtnKinds;

    private ListView mCoauchListView;

    private static Context sContext;

    @SuppressLint("ValidFragment")
    public BilliardsSearchCoauchFragment()
    {
    }

    private static final String PARAMS_KEY = "BilliardsSearchCoauchFragment";

    public static BilliardsSearchCoauchFragment newInstance(Context context, String params)
    {
        sContext = context;
        BilliardsSearchCoauchFragment instance = new BilliardsSearchCoauchFragment();

        Bundle args = new Bundle();
        args.putString(PARAMS_KEY, params);
        instance.setArguments(args);

        return instance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    private String mArgs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.search_coauch_fragment_layout, container, false);

        SubFragmentsCommonUtils.initViewPager(sContext, mView, R.id.coauch_fragment_gallery_pager, R.id.coauch_fragment_gallery_pager_indicator_group);

        (sBtnAbility = (Button) mView.findViewById(R.id.btn_coauch_ability)).setOnClickListener(new OnFilterBtnClickListener());
        (sBtnKinds = (Button) mView.findViewById(R.id.btn_coauch_kinds)).setOnClickListener(new OnFilterBtnClickListener());

        mCoauchListView = (ListView) mView.findViewById(R.id.search_coauch_subfragment_list);

        Bundle args = getArguments();
        mArgs = args.getString(PARAMS_KEY);

        initListViewTestData();
        Log.d(TAG, " the source list content are : " + mCoauchList.size());
        mCoauchListView.setAdapter(new SearchCoauchSubFragmentListAdapter(sContext, (ArrayList<SearchCoauchSubFragmentCoauchBean>) mCoauchList));

        return mView;
    }

    private static class OnFilterBtnClickListener implements View.OnClickListener
    {
        private LayoutInflater inflater = (LayoutInflater) sContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        private PopupWindow popupWindow;

        @Override
        public void onClick(View v)
        {
            switch (v.getId()) {
                case R.id.btn_coauch_ability:
                    View levelPopupView = inflater.inflate(R.layout.search_coauch_subfragment_level_popupwindow, null);

                    String[] levelStrList = {
                            sContext.getResources().getString(R.string.search_coauch_filter_level_guojiadui),
                            sContext.getResources().getString(R.string.search_coauch_filter_level_in_guojiadui),
                            sContext.getResources().getString(R.string.search_coauch_filter_level_pre_guojiadui),
                    };

                    Button btnLevelNoFilter = (Button) levelPopupView.findViewById(R.id.btn_search_coauch_level_popup_no_filter);
                    btnLevelNoFilter.setOnClickListener(new CoauchFilterPopupInternalHandler());
                    ListView levelList = (ListView) levelPopupView.findViewById(R.id.list_search_coauch_level_filter_list);
                    levelList.setAdapter(new SearchPopupBaseAdapter(sContext, Arrays.asList(levelStrList)));
                    SubFragmentsCommonUtils.getFilterPopupWindow(sContext, sBtnAbility, levelPopupView);
                    break;
                case R.id.btn_coauch_kinds:
                    String[] kindsStrList = {
                            sContext.getResources().getString(R.string.search_coauch_filter_kinds_desk),
                            sContext.getResources().getString(R.string.search_coauch_filter_kinds_jiuqiu),
                            sContext.getResources().getString(R.string.search_coauch_filter_kinds_sinuoke)
                    };

                    View kindsPopupView = inflater.inflate(R.layout.search_coauch_subfragment_kinds_popupwindow, null);

                    Button btnKindsNoFilter = (Button) kindsPopupView.findViewById(R.id.btn_search_coauch_kinds_popup_no_filter);
                    btnKindsNoFilter.setOnClickListener(new CoauchFilterPopupInternalHandler());
                    ListView kindsList = (ListView) kindsPopupView.findViewById(R.id.list_search_coauch_kinds_filter_list);
                    kindsList.setAdapter(new SearchPopupBaseAdapter(sContext, Arrays.asList(kindsStrList)));
                    SubFragmentsCommonUtils.getFilterPopupWindow(sContext, sBtnKinds, kindsPopupView);

                    break;
                default:
                    break;
            }
        }
    }

    private final static class CoauchFilterPopupInternalHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_search_coauch_level_popup_no_filter:
                    Toast.makeText(sContext, "do not filter the level", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_search_coauch_kinds_popup_no_filter:
                    Toast.makeText(sContext, "do not filter the kinds", Toast.LENGTH_SHORT).show();

            }
        }
    }

    // TODO: 用于获取教练的列表信息的网络请求处理过程
    private static void retrieveCoauchInfo(final String userId, final String range, final int level, final int startNum, final int endNum)
    {
        // 创建请求参数
        HashMap<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("user_id", userId);
        requestParams.put("range", range);
        requestParams.put("level", level + "");
        requestParams.put("start_no", startNum + "");
        requestParams.put("end_no", endNum + "");

        String rawResult = HttpUtil.urlClient(HttpConstants.SearchCoauch.URL, requestParams, HttpConstants.RequestMethod.GET);
        Log.d(TAG, " the raw result we get are : " + rawResult);
        JSONObject object = Utils.parseJson(rawResult);



    }

    private static final int RETRIEVE_ALL_COAUCH_INFO = 1 << 1;
    private static final int RETRIEVE_COAUCH_WITH_LEVEL_FILTERED = 1 << 2;
    private static final int RETRIEVE_COAUCH_WITH_CLASS_FILTERED = 1 << 3;

    private static Handler sHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case RETRIEVE_ALL_COAUCH_INFO:
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
//                            retrieveCoauchInfo("1233aa", "200", 1 + "", 1 + "", 1 + "");
                        }
                    }).start();
                    break;
                case RETRIEVE_COAUCH_WITH_CLASS_FILTERED:
                    break;
                case RETRIEVE_COAUCH_WITH_LEVEL_FILTERED:

                    break;

            }
        }
    };


    // TODO: 以下是测试数据,在测试接口的时候，将以下的初始化过程删除
    private List<SearchCoauchSubFragmentCoauchBean> mCoauchList = new ArrayList<SearchCoauchSubFragmentCoauchBean>();

    private void initListViewTestData()
    {
        int i;
        for (i = 0; i < 200; ++i) {
            mCoauchList.add(new SearchCoauchSubFragmentCoauchBean("", "大力水手", "男", "2000米以内", "前国家队队员", "九球"));
        }
    }
}






































































































































