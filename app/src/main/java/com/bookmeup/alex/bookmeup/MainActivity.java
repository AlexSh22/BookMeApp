package com.bookmeup.alex.bookmeup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mClientButton = null;
    Button mWorkerButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClientButton = (Button)findViewById(R.id.ClientButton);
        mWorkerButton = (Button)findViewById(R.id.WorkerButton);
    }
    public void onWorkerClick()
    {
        Intent worker_activity_intent = new Intent(this, BusinessActivity.class);
        startActivity(worker_activity_intent);
    }

    public void onClientClick()
    {
        Intent client_activity_intent = new Intent(this, ClientActivity.class);
        startActivity(client_activity_intent);
    }


}
