package com.bookmeup.alex.bookmeup;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import connection.ServerActions;

public class BuisnessActivity extends AppCompatActivity {

    final protected String TAG = this.getClass().getName();
    public ProgressDialog pd;
    private BroadcastReceiver receiver;
    private IntentFilter filter;
    private ServerActions myactions;
    private String userName;
    private String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_buisness);
    }
}
