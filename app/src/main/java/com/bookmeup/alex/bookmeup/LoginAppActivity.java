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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import connection.HTTPIntentService;
import connection.ServerActions;

public class LoginAppActivity extends AppCompatActivity
{

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ImageButton email_sign_in_button;
    private static boolean is_worker;
    private String mEmail;
    private String mPassword;
    public ProgressDialog pd;
    private ServerActions myactions;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        is_worker = intent.getBooleanExtra("worker", false);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        email_sign_in_button = (ImageButton) findViewById(R.id.email_sign_in_button);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        myactions = new ServerActions(this, getResources().getString(R.string.SERVER_LOGIN));


        // setup receiver - should check if there are some updates in server data base
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("===LoginActivity "+"Broadcast Received===");
                String response = intent.getStringExtra(HTTPIntentService.PARAM_OUT_MSG);
                System.out.println("========after getting a response ====");
                if (!response.equals("")) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        System.out.println("========after creating json obj ====");
                        if (obj != null ) {
                            //dismiss progress dialog
                            pd.dismiss();
                            System.out.println("========before json to str ====");
                            System.out.println(obj.toString());  //TODO debug-remove
                            System.out.println("========after json to str ====");

                            if (obj.getString(ServerActions.SERVER_RET_VAL).equals("1")) {
                                /** Register Verified */

                                // edit SharedPref and change status to true
                                Log.d("MainActivity","Saving SharedPrefs");
                                SharedPreferences settings = getSharedPreferences( getResources().getString(R.string.PREFS_FILE), 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("login_status", true);
                                System.out.println("NEW group id is:"+obj.getString(ServerActions.SERVER_DATA));
                                editor.putString(getResources().getString(R.string.PREFS_GRP), obj.getString(ServerActions.SERVER_DATA));
                                editor.putString(getResources().getString(R.string.PREFS_GRP_NAME), obj.getString(ServerActions.SERVER_DATA2));
                                editor.commit();


                                // call menu
                                try {
                                    Log.d("MainActivity","Call Menu");
                                    // unregister receiver
                                    Intent launcher;
                                    if(!(obj.getString(ServerActions.SERVER_DATA).equals("0"))){
                                        launcher = new Intent(context, MainActivity.class);
                                    }else{
                                        if(is_worker)
                                            launcher = new Intent(context, BusinessActivity.class);
                                        else
                                            launcher = new Intent(context, ClientActivity.class);
                                    }
                                    launcher.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//remove login screen from stack
                                    launcher.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);// Stay single instance
                                    startActivity(launcher);
                                    finish();
                                } catch (RuntimeException e) {
                                    Log.e("MainActivity","Failed Call Menu: "+e);
                                }

                            }

                            /** Anyway Print Message from Server */
                            //TODO change error messages from server to local to support other languages
                            Toast.makeText(context, obj.getString(ServerActions.SERVER_MSG),
                                    Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        Log.e("BroadcastReceiver","JSON error: "+e);
                    }
                }else{
                    //dismiss progress dialog
                    pd.dismiss();
                    Toast.makeText(context,"Unexpected error occured,Please Try again later",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        // Register filter
        filter = new IntentFilter(getResources().getString(R.string.SERVER_LOGIN));


    }

    public void attemptLogin()
    {
        pd = ProgressDialog.show(this, "Loading..", "Please wait",true,true);
        // save sharedPrefs, status is false until confirmation
        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.PREFS_FILE), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("login_status", false);
        mEmail = (((EditText)findViewById(R.id.email)).getText().toString());
        mPassword = (((EditText)findViewById(R.id.password)).getText().toString());

        editor.putString("prefs_user", mEmail);
        editor.putString("prefs_pwd", mPassword);
        editor.commit();
        myactions.new_user_registery(mEmail, mPassword);
    }

    public void registrationClick(View v)
    {
        attemptLogin();
    }

    public void onStart(){
        super.onStart();

        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.PREFS_FILE), 0);
        boolean login_stats = settings.getBoolean(getResources().getString(R.string.PREFS_STATS), false);
        if (login_stats) {
            try {
                pd = ProgressDialog.show(this, "Loading...", "Please wait",true,true);

                userName = settings.getString(getResources().getString(R.string.PREFS_USER),"username");
                passWord = settings.getString(getResources().getString(R.string.PREFS_PWD),"password");
                myactions.new_user_registery(userName, passWord);
            } catch (RuntimeException e) {
                Log.e("LoginActivity","Failed Call Menu: "+e);
            }
        }
    }


}
