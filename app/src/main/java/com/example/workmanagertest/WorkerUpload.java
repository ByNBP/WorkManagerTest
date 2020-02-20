package com.example.workmanagertest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class WorkerUpload extends Worker {
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    public WorkerUpload(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @Override
    public Result doWork() {
        // Do the work here--in this case, upload the images.

        showNotif("deneme", "deneme123!! deneme ");
        // Indicate whether the task finished successfully with the Result
        return Result.success();
    }

    public static void oneOffRequest() {
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkerUpload.class)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setConstraints(setCons())
                .build();

        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    }

    public static void periodRequest() {
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(WorkerUpload.class, 10, TimeUnit.SECONDS)
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setConstraints(setCons())
                .addTag("periodic")
                .build();

        WorkManager.getInstance().enqueueUniquePeriodicWork("periodic", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);

        Log.d("YEK", "deneme123!!");

    }

    public void showNotif(String title, String message) {

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());

    }

    public static Constraints setCons() {
        Constraints constraints = new Constraints.Builder().build();
        return constraints;
    }


}
