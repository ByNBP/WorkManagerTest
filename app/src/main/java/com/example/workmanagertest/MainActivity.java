package com.example.workmanagertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button deneme;
    NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deneme = findViewById(R.id.deneme);
        deneme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationHelper = new NotificationHelper(getApplicationContext());
                notificationHelper.createNotification("Deneme", "Bu bir Denemedir !! ");
            }
        });

        WorkerUpload.periodRequest();


    }


}
