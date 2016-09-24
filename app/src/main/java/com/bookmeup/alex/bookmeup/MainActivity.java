package com.bookmeup.alex.bookmeup;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button mClientButton = null;
    Button mWorkerButton = null;
    TextView mLogoutButton = null;
    TextView mMainTitle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClientButton = (Button)findViewById(R.id.ClientButton);
        mWorkerButton = (Button)findViewById(R.id.WorkerButton);
        mLogoutButton = (TextView)findViewById(R.id.logoutButton);
        mMainTitle = (TextView)findViewById(R.id.mainTitle);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.PREFS_FILE), 0);
        String name = settings.getString(getResources().getString(R.string.PREFS_USER),"username");
        if(!name.equalsIgnoreCase("username") && settings.getBoolean("login_status", false))
        {
            mMainTitle.setText("שלום "+name);
            mLogoutButton.setVisibility(View.VISIBLE);
        }

    }




    public void onWorkerClick(View v)
    {
        Intent worker_login = new Intent(this, LoginAppActivity.class);
        worker_login.putExtra("worker", true);
        startActivity(worker_login);
    }

    public void onClientClick(View v)
    {
        Intent client_login = new Intent(this, LoginAppActivity.class);
        client_login.putExtra("worker", false);
        startActivity(client_login);
    }

    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder editalert = new AlertDialog.Builder(this);
        editalert.setTitle("Exit");
        editalert.setIcon(android.R.drawable.ic_dialog_alert);
        editalert.setCancelable(true);
        final TextView in = new TextView(this);
        in.setText("Do you really wish to exit? ");
        in.setTextSize(22);
        editalert.setView(in);
        editalert.setView(in);
        editalert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                System.out.println("backpressed");
                finish();
            }
        });
        editalert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        editalert.create();

        editalert.show();
    }


    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouch(View v, MotionEvent event)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return false;
    }

    public void onLogoutClick (View v)
    {
        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.PREFS_FILE), 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(getResources().getString(R.string.PREFS_USER), "username");
        editor.putString(getResources().getString(R.string.PREFS_PWD), "password");
        editor.putBoolean("login_status", false);
        editor.commit();
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

}
