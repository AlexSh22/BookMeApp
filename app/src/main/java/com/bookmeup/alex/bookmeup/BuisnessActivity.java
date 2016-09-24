package com.bookmeup.alex.bookmeup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import connection.HTTPIntentService;
import connection.ServerActions;

public class BuisnessActivity extends AppCompatActivity {

    final protected String TAG = this.getClass().getName();
    public ProgressDialog pd;
    private BroadcastReceiver receiver;
    private IntentFilter filter;
    private ServerActions myactions;
    private String userName;
    private String business_name;
    private String accept_time;
    private EditText businessName;
    private EditText acceptTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_buisness);
        businessName = (EditText) (findViewById(R.id.business_name));
        acceptTime = (EditText) (findViewById(R.id.accept_time));
        myactions = new ServerActions(this, getResources().getString(R.string.SERVER_ADD_BUSINESS));
        // setup receiver - should check if there are some updates in server data base

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("===BusinessActivity "+"Broadcast Received===");
                String response = intent.getStringExtra(HTTPIntentService.PARAM_OUT_MSG);
                System.out.println("========after getting a response ====");
                if (!response.equals("")) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        System.out.println("========after creating json obj ====");
                        if (obj != null ) {
                            if (obj.getString(ServerActions.ACTION_COMMAND).equals(ServerActions.ACTION_ADD_BUSINESS)) {
                                //dismiss progress dialog
                                System.out.println(obj.toString());
                                //dismiss progress dialog
                                pd.dismiss();
                                System.out.println("========before json to str ====");
                                System.out.println(obj.toString());  //TODO debug-remove
                                System.out.println("========after json to str ====");

                                if (obj.getString(ServerActions.SERVER_RET_VAL).equals("1")) {
                                    /** Business added */
                                    System.out.println("Add business return 1 (succeeded)");
                                    Toast.makeText(context, obj.getString(ServerActions.SERVER_MSG),
                                            Toast.LENGTH_LONG).show();

                                }
                            }
                        }
                    } catch (JSONException e) {
                        Log.e("BroadcastReceiver","JSON error: "+e);
                    }
                } else {
                    //dismiss progress dialog
                    pd.dismiss();
                    Toast.makeText(context,"Unexpected error occured,Please Try again later",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        // Register filter
        filter = new IntentFilter(getResources().getString(R.string.SERVER_ADD_BUSINESS));
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
                SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.PREFS_FILE), 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("login_status");
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
    public void add_business_click(View v)
    {
        business_name = businessName.getText().toString();
        accept_time = acceptTime.getText().toString();
        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.PREFS_FILE), 0);
        String user_name = settings.getString(getResources().getString(R.string.PREFS_USER),"username");
        Log.i("BusinessActivity","add business was clicked");
        if (!user_name.equalsIgnoreCase("username")) {
            try {
                pd = ProgressDialog.show(this, "Adding...", "Please wait", true, true);
                myactions.add_business(business_name, user_name, accept_time);
            } catch (RuntimeException e) {
                Log.e("BusinessActivity", "Failed Call Menu: " + e);
            }
        } else {
            Log.i("BusinessActivity","username: " + user_name.toString());
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister filter
        unregisterReceiver(receiver);
        // call finish on self

    }
}
