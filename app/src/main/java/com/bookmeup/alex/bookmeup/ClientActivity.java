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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import connection.HTTPIntentService;
import connection.ServerActions;

public class ClientActivity extends AppCompatActivity {

    public ProgressDialog pd;
    private BroadcastReceiver receiver;
    private IntentFilter filter;
    private ServerActions myactions;
    private String business_name;
    private String accept_time;
    private EditText businessName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        businessName = (EditText) (findViewById(R.id.buisness_search));
        myactions = new ServerActions(this, getResources().getString(R.string.SERVER_SEARCH_BUSINESS));

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("===ClientActivity "+"Broadcast Received===");
                String response = intent.getStringExtra(HTTPIntentService.PARAM_OUT_MSG);
                System.out.println("========after getting a response ====");
                if (!response.equals("")) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        System.out.println("========after creating json obj ====");
                        if (obj != null ) {
                            if (obj.getString(ServerActions.ACTION_COMMAND).equals(ServerActions.ACTION_SEARCH_BUSINESS)) {
                                //dismiss progress dialog
                                System.out.println(obj.toString());
                                //dismiss progress dialog
                                pd.dismiss();
                                System.out.println("========before json to str ====");
                                System.out.println(obj.toString());  //TODO debug-remove
                                System.out.println("========after json to str ====");

                                if (obj.getString(ServerActions.SERVER_RET_VAL).equals("1")) {
                                    /** Business added */
                                    System.out.println("Search business return 1 (succeeded)");
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
        filter = new IntentFilter(getResources().getString(R.string.SERVER_SEARCH_BUSINESS));
    }

    public void search_business_click(View v)
    {
        business_name = businessName.getText().toString();
        Log.i("ClientActivity","search business was clicked");
        if (!business_name.isEmpty()) {
            try {
                pd = ProgressDialog.show(this, "Searching...", "Please wait", true, true);
                myactions.search_business(business_name);
            } catch (RuntimeException e) {
                Log.e("ClientActivity", "Failed Call Menu: " + e);
            }
        } else {
            Log.i("ClientActivity","business name: " + business_name);
        }
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
