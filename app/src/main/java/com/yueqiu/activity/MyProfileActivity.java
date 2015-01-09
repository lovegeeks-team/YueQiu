package com.yueqiu.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

import com.yueqiu.R;
import com.yueqiu.YueQiuApp;
import com.yueqiu.bean.UserInfo;
import com.yueqiu.constant.DatabaseConstant;
import com.yueqiu.constant.HttpConstants;
import com.yueqiu.db.DBUtils;
import com.yueqiu.util.HttpUtil;
import com.yueqiu.util.Utils;

import android.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
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
    private JSONArray mJSONArray;
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
//        mJSONArray = Utils.getJSONFromLocal(this);
//        try {
//            mUserInfo = new UserInfo(mJSONArray.getJSONObject(0));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        };
//        if (mUserInfo != null) {
//            //从本地获取我的资料
//            Log.e(TAG, "从本地获取我的资料");
//            updateUI(mUserInfo);
//        } else {
//            if (mUserId == 0) {
//                return; //游客状态！！是否要显示我的资料界面
//            }
        if (Utils.networkAvaiable(this)) {

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
                            message.what = DATA_SUCCESS;
                            message.obj = mUserInfo;
                        }
                        mHandler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else{
            mUserInfo = getUserByUserId(String.valueOf(YueQiuApp.sUserInfo.getUser_id()));
        }
    }
    //}



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
        mNickNameTextView.setText("".equals(userInfo.getNickName())
                ? unset : userInfo.getNickName());
        mRegionTextView.setText("".equals(userInfo.getDistrict())
                ? unset : userInfo.getDistrict());
        mLevelTextView.setText(1 == userInfo.getLevel()
                ? getString(R.string.level_base) : ((2 == userInfo.getLevel()) ?
                getString(R.string.level_middle) : getString(R.string.level_master)));
        mBallTypeTextView.setText(1 == userInfo.getBall_type()
                ? getString(R.string.ball_type_1) : (2 == userInfo.getBall_type() ?
                getString(R.string.ball_type_2) : getString(R.string.ball_type_3)));
        mBilliardsCueTextView.setText(1 == userInfo.getBallArm()
                ? getString(R.string.cue_1) : getString(R.string.cue_2));
        mCueHabitsTextView.setText(1 == userInfo.getUsedType()
                ? getString(R.string.habit_1) : (2 == userInfo.getUsedType() ?
                getString(R.string.habit_2) : getString(R.string.habit_3)));
        mPlayAgeTextView.setText(userInfo.getBallAge());
        mIdolTextView.setText("".equals(userInfo.getIdol())
                ? unset : userInfo.getIdol());
        mSignTextView.setText("".equals(userInfo.getIdol_name())
                ? unset : userInfo.getIdol_name());
//        mTheNewestPostImageView.setImageDrawable();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        //保存数据到本地
//        if (mUserInfo != null) {
//            try {
//                Utils.updateJSONData(this, mUserInfo,Utils.USER_INFO_FILE_NAME);
//            } catch (IOException e) {
//                Log.e(TAG, "IOException: " + e.toString());
//            } catch (JSONException e) {
//                Log.e(TAG, "JSONException: " + e.toString());
//            }
//        }

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
                startActivity(new Intent(this, UpdateAssistantActivity.class));
                break;
            case R.id.update_coach_btn:
                //TODO:升级教练界面
                startActivity(new Intent(this, UpdateAssistantActivity.class));
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
//                startMyActivity(5);
                break;
            case R.id.my_profile_ball_type:
//                startMyActivity(6);
                break;
            case R.id.my_profile_billiards_cue:
//                startMyActivity(7);
                break;
            case R.id.my_profile_cue_habits:
//                startMyActivity(8);
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
                mUserInfo.setNickName(str);
                break;
            case 4:
                mRegionTextView.setText(str);
                mUserInfo.setDistrict(str);
                break;
            case 5:
                mLevelTextView.setText(str);
                break;
            case 6:
                mBallTypeTextView.setText(str);
                break;
            case 7:
                mBilliardsCueTextView.setText(str);
                break;
            case 8:
                mCueHabitsTextView.setText(str);
                break;
            case 9:
                mPlayAgeTextView.setText(str);
                mUserInfo.setBallAge(str);
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

    private UserInfo getUserByUserId(String userId){
        DBUtils dbUtil = new DBUtils(this, DatabaseConstant.UserTable.CREATE_SQL);
        SQLiteDatabase db = dbUtil.getReadableDatabase();
        UserInfo info = new UserInfo();
        String sql = "select * from " + DatabaseConstant.UserTable.TABLE + " where " + DatabaseConstant.UserTable.USER_ID + "=?";
        Cursor cursor = db.rawQuery(sql,new String[]{userId});
        if(cursor != null || cursor.getCount() != 0){
            cursor.moveToFirst();
            info.setUser_id(Integer.valueOf(userId));
            info.setAccount(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.ACCOUNT)));
            info.setPhone(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.PHONE)));
            info.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.PASSWORD)));
            info.setSex(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.UserTable.SEX)));
            info.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.TITLE)));
            info.setImg_url(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.IMG_URL)));
            info.setNickName(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.USERNAME)));
            info.setDistrict(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.DISTRICT)));
            info.setLevel(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.UserTable.LEVEL)));
            info.setBall_type(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.UserTable.BALL_TYPE)));
            info.setAppoint_date(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.APPOINT_DATE)));
            info.setBallArm(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.UserTable.BALLARM)));
            info.setUsedType(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.UserTable.USERDTYPE)));
            info.setBallAge(String.valueOf(cursor.getInt(cursor.getColumnIndex(DatabaseConstant.UserTable.BALLAGE))));
            info.setIdol(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.IDOL)));
            info.setIdol_name(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.IDOL_NAME)));
            info.setNew_img(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.NEW_IMG)));
            info.setLogin_time(cursor.getString(cursor.getColumnIndex(DatabaseConstant.UserTable.LOGIN_TIME)));
        }
        cursor.close();
        return info;
    }

}
