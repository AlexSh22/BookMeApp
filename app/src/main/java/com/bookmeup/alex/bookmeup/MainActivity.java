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
        Intent worker_login = new Intent(this, LoginActivity.class);
        worker_login.putExtra("worker", true);
        startActivity(worker_login);
    }

    public void onClientClick()
    {
        Intent client_login = new Intent(this, LoginActivity.class);
        client_login.putExtra("worker", false);
        startActivity(client_login);
    }


}
