package com.yueqiu.activity.searchmenu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.yueqiu.R;


public class FeedbackActivity extends Activity
{
    private static final String TAG = "FeedbackActivity";

    private EditText mEtFeedbackTitle, mEtFeedbackContent, mEtFeedbackContact;
    private static final int SUBMIT_ACTION = 1 << 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initActionBar();

        mEtFeedbackTitle = (EditText) findViewById(R.id.et_feedback_title);
        mEtFeedbackContent = (EditText) findViewById(R.id.et_feedback_content);
        mEtFeedbackContact = (EditText) findViewById(R.id.et_feedback_contact);

        (findViewById(R.id.btn_feedback_submit)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sSubmitHandler.sendEmptyMessage(SUBMIT_ACTION);
            }
        });
    }

    private void initActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(getString(R.string.search_feed_back_str));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    private static Handler sSubmitHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == SUBMIT_ACTION)
            {
                // perform the submit action

            }
        }
    };


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.feedback, menu);
//        return true;
//    }
//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
