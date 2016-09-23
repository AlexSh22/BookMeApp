package com.bookmeup.alex.bookmeup;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import connection.HTTPIntentService;
import connection.ServerActions;

public class BusinessActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_business);
    }
}
