package com.yueqiu.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.yueqiu.R;
import com.yueqiu.constant.DatabaseConstant;
import com.yueqiu.constant.HttpConstants;
import com.yueqiu.constant.PublicConstant;
import com.yueqiu.dao.DaoFactory;
import com.yueqiu.dao.UserDao;
import com.yueqiu.db.DBUtils;
import com.yueqiu.util.AsyncTaskUtil;
import com.yueqiu.util.Utils;
import com.yueqiu.view.progress.FoldingCirclesDrawable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import java.util.Map;




/**
 * 登录Activity
 * Created by yinfeng on 14/12/17.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private Button mBtnLogin;
    private TextView mTvRegister;
    private TextView mTvForgetPwd,mPreText;
    private ProgressBar mPreProgress;
    private Drawable mProgressDrawable;
    private EditText mEtUserId;
    private EditText mEtPwd;
    private InputMethodManager mImm;
    private String mUserName;
    private String mPwd;
    private UserDao mUserDao;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PublicConstant.REQUEST_ERROR:
                    if(msg.obj == null){
                        Toast.makeText(LoginActivity.this, getString(R.string.http_request_error),
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, msg.obj.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PublicConstant.GET_SUCCESS:
                    Toast.makeText(LoginActivity.this, getString(R.string.login_success),Toast.LENGTH_SHORT).show();
                    Map<String,String> map = (Map<String, String>) msg.obj;
                    Utils.getOrUpdateUserBaseInfo(LoginActivity.this,map);
                    if(!mUserDao.queryUserId(map)){
                        mUserDao.insertUserInfo(map);
                    }
                    finish();
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    break;
                case PublicConstant.TIME_OUT:
                    Toast.makeText(LoginActivity.this, getString(R.string.http_request_time_out),
                            Toast.LENGTH_SHORT).show();
                    break;
                case PublicConstant.NO_RESULT:
                    Toast.makeText(LoginActivity.this, msg.obj.toString(),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initActionBar();
        mUserDao = DaoFactory.getUser(this);



    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.login));

    }

    private void initView() {
        mBtnLogin = (Button) findViewById(R.id.activity_login_btn_login);
        mTvRegister = (TextView) findViewById(R.id.activity_login_tv_register);
        mEtUserId = (EditText) findViewById(R.id.activity_login_et_username);
        mEtPwd = (EditText) findViewById(R.id.activity_login_et_password);


        mPreProgress = (ProgressBar) findViewById(R.id.pre_progress);
        mProgressDrawable = new FoldingCirclesDrawable.Builder(this).build();
        Rect bounds = mPreProgress.getIndeterminateDrawable().getBounds();
        mPreProgress.setIndeterminateDrawable(mProgressDrawable);
        mPreProgress.getIndeterminateDrawable().setBounds(bounds);
        mPreText = (TextView) findViewById(R.id.pre_text);
        mPreText.setText(getString(R.string.pre_login_text));
////
        mBtnLogin.setOnClickListener(this);
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mTvRegister.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_login_btn_login:
                 mUserName = mEtUserId.getText().toString().trim();
                if (TextUtils.isEmpty(mUserName)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_without_phone_or_account),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mPwd = mEtPwd.getText().toString().trim();
                if (TextUtils.isEmpty(mPwd)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_without_pasword),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> requestMap = new HashMap<String, String>();
                requestMap.put(HttpConstants.LoginConstant.USERNAME, mUserName);
                requestMap.put(HttpConstants.LoginConstant.PASSWORD, mPwd);

                Map<String,String> paramMap = new HashMap<String, String>();
                paramMap.put(PublicConstant.URL,HttpConstants.LoginConstant.URL);
                paramMap.put(PublicConstant.METHOD,HttpConstants.RequestMethod.POST);
                if(Utils.networkAvaiable(LoginActivity.this)) {
                    new LoginAsyncTask(requestMap).execute(paramMap);
                }else{
                    Toast.makeText(LoginActivity.this, getString(R.string.network_not_available), Toast.LENGTH_SHORT).show();
                }

                mImm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.activity_login_tv_register:
                startActivity(new Intent(LoginActivity.this, CheckNumActivity.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }

    }


    private class LoginAsyncTask extends AsyncTaskUtil<String>{

        public LoginAsyncTask(Map<String, String> map) {
            super(map);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mPreProgress.setVisibility(View.VISIBLE);
            mPreText.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(JSONObject object) {
            super.onPostExecute(object);
            mPreProgress.setVisibility(View.GONE);
            mPreText.setVisibility(View.GONE);
            try {
                if (!object.isNull("code")){
                    if (object.getInt("code") == HttpConstants.ResponseCode.NORMAL) {
                        Map<String, String> successObj = new HashMap<String, String>();
                        successObj.put(DatabaseConstant.UserTable.USERNAME, object.getJSONObject("result").
                                getString(DatabaseConstant.UserTable.USERNAME));
                        successObj.put(DatabaseConstant.UserTable.PASSWORD, mPwd);
                        successObj.put(DatabaseConstant.UserTable.USER_ID, object.getJSONObject("result").
                                getString(DatabaseConstant.UserTable.USER_ID));
                        successObj.put(DatabaseConstant.UserTable.LOGIN_TIME, object.getJSONObject("result").
                                getString(DatabaseConstant.UserTable.LOGIN_TIME));
                        successObj.put(DatabaseConstant.UserTable.PHONE, object.getJSONObject("result").
                                getString(DatabaseConstant.UserTable.PHONE));
                        successObj.put(DatabaseConstant.UserTable.IMG_URL, object.getJSONObject("result").
                                getString(DatabaseConstant.UserTable.IMG_URL));

                        mHandler.obtainMessage(PublicConstant.GET_SUCCESS,successObj).sendToTarget();
                    }else if(object.getInt("code") == HttpConstants.ResponseCode.TIME_OUT) {
                        mHandler.obtainMessage(PublicConstant.TIME_OUT).sendToTarget();
                    }else if(object.getInt("code") == HttpConstants.ResponseCode.REQUEST_ERROR){
                        mHandler.obtainMessage(PublicConstant.REQUEST_ERROR).sendToTarget();
                    }
                    else {
                        mHandler.obtainMessage(PublicConstant.REQUEST_ERROR,object.getString("msg")).sendToTarget();
                    }
                }else{
                    mHandler.obtainMessage(PublicConstant.REQUEST_ERROR).sendToTarget();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }





}
