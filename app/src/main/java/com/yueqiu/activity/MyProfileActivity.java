package com.yueqiu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

import com.yueqiu.R;
import com.yueqiu.YueQiuApp;
import com.yueqiu.bean.UserInfo;
import com.yueqiu.constant.HttpConstants;
import com.yueqiu.util.HttpUtil;
import com.yueqiu.util.Utils;

import android.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by doushuqi on 14/12/19.
 * 我的资料主Activity
 */
public class MyProfileActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MyProfileActivity";
    private Button mAssistant, mCoach;
    private RelativeLayout mPhoto, mAccount, mGender, mNickName, mRegion, mLevel, mBallType,
            mBilliardsCue, mCueHabits, mPlayAge, mIdol, mSign, mTheNewestPost;
    private TextView mNickNameTextView, mRegionTextView, mLevelTextView, mBallTypeTextView,
            mBilliardsCueTextView, mCueHabitsTextView, mPlayAgeTextView, mIdolTextView,
            mSignTextView, mAccountTextView, mGenderTextView;
    private ImageView mPhotoImageView, mTheNewestPostImageView;

    public static final String EXTRA_FRAGMENT_ID =
            "com.yueqiu.activity.searchmenu.myprofileactivity.fragment_id";
    public static int EXTRA_REQUEST_ID = 0;
    public static String EXTRA_RESULT_ID = "com.yueqiu.activity.searchmenu.myprofileactivity.result_id";

    //data
    private int mUserId = YueQiuApp.sUserInfo.getUser_id();
    private UserInfo mUserInfo;
    private static final int DATA_ERROR = 1;
    private static final int DATA_SUCCESS = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.search_my_profile_str));
        initView();
        setClickListener();
        initData();

    }

    private void initView() {
        mAssistant = (Button) findViewById(R.id.update_assistant_btn);
        mCoach = (Button) findViewById(R.id.update_coach_btn);
        mPhoto = (RelativeLayout) findViewById(R.id.my_profile_photo);
        mAccount = (RelativeLayout) findViewById(R.id.my_profile_account);
        mGender = (RelativeLayout) findViewById(R.id.my_profile_gender);
        mNickName = (RelativeLayout) findViewById(R.id.my_profile_nick_name);
        mRegion = (RelativeLayout) findViewById(R.id.my_profile_region);
        mLevel = (RelativeLayout) findViewById(R.id.my_profile_level);
        mBallType = (RelativeLayout) findViewById(R.id.my_profile_ball_type);
        mBilliardsCue = (RelativeLayout) findViewById(R.id.my_profile_billiards_cue);
        mCueHabits = (RelativeLayout) findViewById(R.id.my_profile_cue_habits);
        mPlayAge = (RelativeLayout) findViewById(R.id.my_profile_play_age);
        mIdol = (RelativeLayout) findViewById(R.id.my_profile_idol);
        mSign = (RelativeLayout) findViewById(R.id.my_profile_sign);
        mTheNewestPost = (RelativeLayout) findViewById(R.id.my_profile_the_new_post);

        mAccountTextView = (TextView) findViewById(R.id.my_profile_account_tv);
        mGenderTextView = (TextView) findViewById(R.id.my_profile_gender_tv);
        mNickNameTextView = (TextView) findViewById(R.id.my_profile_nick_name_tv);
        mRegionTextView = (TextView) findViewById(R.id.my_profile_region_tv);
        mLevelTextView = (TextView) findViewById(R.id.my_profile_level_tv);
        mBallTypeTextView = (TextView) findViewById(R.id.my_profile_ball_type_tv);
        mBilliardsCueTextView = (TextView) findViewById(R.id.my_profile_billiards_cue_tv);
        mCueHabitsTextView = (TextView) findViewById(R.id.my_profile_cue_habits_tv);
        mPlayAgeTextView = (TextView) findViewById(R.id.my_profile_play_age_tv);
        mIdolTextView = (TextView) findViewById(R.id.my_profile_idol_tv);
        mSignTextView = (TextView) findViewById(R.id.my_profile_sign_tv);

        mPhotoImageView = (ImageView) findViewById(R.id.my_profile_photo_iv);
        mTheNewestPostImageView = (ImageView) findViewById(R.id.my_profile_the_new_post_im);
    }

    //初始化我的资料数据
    private void initData() {
        mUserInfo = Utils.getMyProfileFromLocal(this);
        if (mUserInfo != null) {
            //从本地获取我的资料
            Log.e(TAG, "从本地获取我的资料");
            updateUI(mUserInfo);
        } else {
            if (mUserId == 0) {
                return; //游客状态！！是否要显示我的资料界面
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String, Integer> map = new HashMap<String, Integer>();
                    map.put(HttpConstants.GetMyInfo.USER_ID, mUserId);

                    String result = HttpUtil.urlClient(HttpConstants.GetMyInfo.URL,
                            map, HttpConstants.RequestMethod.GET);
                    JSONObject object = Utils.parseJson(result);
                    Message message = new Message();
                    try {
                        if (object.getInt("code") != HttpConstants.ResponseCode.NORMAL) {

                            message.what = DATA_ERROR;
                            message.obj = object.getString("msg");
                        } else {
                            mUserInfo = new UserInfo(object.getJSONObject("result"));
//                            userInfo.setImg_url(String.valueOf(object.getJSONObject("result").getInt("img_url")));
//                            userInfo.setAccount(String.valueOf(object.getJSONObject("result").getInt("account")));
//
//                            userInfo.setUsername(String.valueOf(object.getJSONObject("result").getInt("username")));
//                            userInfo.setDistrict(object.getJSONObject("result").getString("district"));
//                            userInfo.setLevel(String.valueOf(object.getJSONObject("result").getInt("level")));
//                            userInfo.setBall_type(String.valueOf(object.getJSONObject("result").getInt("ball_type")));
//                            userInfo.setAppoint_date(object.getJSONObject("result").getString("appoint_date"));//datatime数据格式
//                            userInfo.setBallArm(object.getJSONObject("result").getString("ballArm"));
//                            userInfo.setUsedType(object.getJSONObject("result").getString("usedType"));
//                            userInfo.setBallAge(object.getJSONObject("result").getInt("ballAge"));
//                            userInfo.setIdol(String.valueOf(object.getJSONObject("result").getInt("idol")));
//                            userInfo.setIdol_name(object.getJSONObject("result").getString("idol_name"));
//                            userInfo.setNew_img(object.getJSONObject("result").getString("new_img"));
//
//                            JSONArray list_data = object.getJSONObject("result").getJSONArray("sex");
//
//                            Log.e("dou", "---" + userInfo.getImg_url() + "   " + userInfo.getAccount() + "   "
//                                    + object.getJSONObject("result").getString("sex"));
                            message.what = DATA_SUCCESS;
                            message.obj = mUserInfo;
                        }
                        mHandler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATA_ERROR:
                    Log.i(TAG, "error to get profile from service");
                    break;
                case DATA_SUCCESS:
                    updateUI((UserInfo) msg.obj);
                    break;
            }
        }
    };

    private void updateUI(UserInfo userInfo) {
        String unset = getString(R.string.unset);
//        mPhotoImageView.setImageDrawable();
        mAccountTextView.setText(userInfo.getAccount());
        mGenderTextView.setText(userInfo.getSex() == 1
                ? getString(R.string.man) : getString(R.string.woman));
        mNickNameTextView.setText("".equals(userInfo.getUsername())
                ? unset : userInfo.getUsername());
        mRegionTextView.setText("".equals(userInfo.getDistrict())
                ? unset : userInfo.getDistrict());
        mLevelTextView.setText("".equals(userInfo.getLevel())
                ? unset : userInfo.getLevel());
        mBallTypeTextView.setText("".equals(userInfo.getBall_type())
                ? unset : userInfo.getBall_type());
        mBilliardsCueTextView.setText("".equals(userInfo.getBallArm())
                ? unset : userInfo.getBallArm());
        mCueHabitsTextView.setText("".equals(userInfo.getUsedType())
                ? unset : userInfo.getUsedType());
        mPlayAgeTextView.setText(0 == userInfo.getBallAge()
                ? unset : String.valueOf(userInfo.getBallAge()));
        mIdolTextView.setText("".equals(userInfo.getIdol())
                ? unset : userInfo.getIdol());
        mSignTextView.setText("".equals(userInfo.getIdol_name())
                ? unset : userInfo.getIdol_name());
//        mTheNewestPostImageView.setImageDrawable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //保存数据到本地
        if (mUserInfo != null) {
            try {
                Utils.updateMyProfile(this, mUserInfo);
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.toString());
            } catch (JSONException e) {
                Log.e(TAG, "JSONException: " + e.toString());
            }
        }

    }

    private void setClickListener() {
        mAssistant.setOnClickListener(this);
        mCoach.setOnClickListener(this);
        mPhoto.setOnClickListener(this);
        mAccount.setOnClickListener(this);
        mGender.setOnClickListener(this);
        mNickName.setOnClickListener(this);
        mRegion.setOnClickListener(this);
        mLevel.setOnClickListener(this);
        mBallType.setOnClickListener(this);
        mBilliardsCue.setOnClickListener(this);
        mCueHabits.setOnClickListener(this);
        mPlayAge.setOnClickListener(this);
        mIdol.setOnClickListener(this);
        mSign.setOnClickListener(this);
        mTheNewestPost.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.update_assistant_btn:
                //TODO:升级助教界面
//                Intent intent = new Intent(this,);
                break;
            case R.id.update_coach_btn:
                //TODO:升级教练界面
                break;
            case R.id.my_profile_photo:
                startMyActivity(0);
                break;
            case R.id.my_profile_account:
//                startMyActivity(1);
                break;
            case R.id.my_profile_gender:
//                startMyActivity(2);
                break;
            case R.id.my_profile_nick_name:
                startMyActivity(3);
                break;
            case R.id.my_profile_region:
                startMyActivity(4);
                break;
            case R.id.my_profile_level:
                startMyActivity(5);
                break;
            case R.id.my_profile_ball_type:
                startMyActivity(6);
                break;
            case R.id.my_profile_billiards_cue:
                startMyActivity(7);
                break;
            case R.id.my_profile_cue_habits:
                startMyActivity(8);
                break;
            case R.id.my_profile_play_age:
                startMyActivity(9);
                break;
            case R.id.my_profile_idol:
                startMyActivity(10);
                break;
            case R.id.my_profile_sign:
                startMyActivity(11);
                break;
            case R.id.my_profile_the_new_post:
                startMyActivity(12);
                break;
        }
    }

    private void startMyActivity(int id) {
        Intent i = new Intent(this, ProfileSetupActivity.class);
        i.putExtra(EXTRA_FRAGMENT_ID, id);
        startActivityForResult(i, id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        String str = data.getStringExtra(EXTRA_RESULT_ID);
        if ("".equals(str) || null == str)
            return;
        if (mUserInfo == null)
            mUserInfo = new UserInfo();
        switch (requestCode) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:
                mNickNameTextView.setText(str);
                mUserInfo.setUsername(str);
                break;
            case 4:
                mRegionTextView.setText(str);
                mUserInfo.setDistrict(str);
                break;
            case 5:
                mLevelTextView.setText(str);
                mUserInfo.setLevel(str);
                break;
            case 6:
                mBallTypeTextView.setText(str);
                mUserInfo.setBall_type(str);
                break;
            case 7:
                mBilliardsCueTextView.setText(str);
                mUserInfo.setBallArm(str);
                break;
            case 8:
                mCueHabitsTextView.setText(str);
                mUserInfo.setUsedType(str);
                break;
            case 9:
                mPlayAgeTextView.setText(str);
                mUserInfo.setBallAge(Integer.parseInt(str));
                break;
            case 10:
                mIdolTextView.setText(str);
                mUserInfo.setIdol(str);
                break;
            case 11:
                mSignTextView.setText(str);
                mUserInfo.setIdol_name(str);
                break;
            case 12:

                break;

        }
    }


}
