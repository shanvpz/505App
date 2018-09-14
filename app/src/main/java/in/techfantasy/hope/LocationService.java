package in.techfantasy.hope;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class LocationService extends Service {

    private Timer mTimer;
    private Handler mHandler = new Handler();

    private static final int TIMER_INTERVAL = 30000; // 2 Minute
    private static final int TIMER_DELAY = 0;
    NotificationManager notificationManager;
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences=this.getSharedPreferences(Globals.sharedPrefName,MODE_PRIVATE);
         notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (mTimer != null)
            mTimer = null;

        // Create new Timer
        mTimer = new Timer();

        // Required to Schedule DisplayToastTimerTask for repeated execution with an interval of `2 min`
        mTimer.scheduleAtFixedRate(new DisplayToastTimerTask(), TIMER_DELAY, TIMER_INTERVAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotification();
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Cancel timer
        mTimer.cancel();
    }

    // Required to do some task
    // Here I just display a toast message "Hello world"
    private class DisplayToastTimerTask extends TimerTask {

        @Override
        public void run() {

            // Do something....

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(), "Hello world", Toast.LENGTH_SHORT).show();
                    final ReqModel rm=new ReqModel();
                    rm.setGoogleID(sharedPreferences.getString("googleID",""));
                    SmartLocation.with(getApplicationContext()).location().oneFix().start(new OnLocationUpdatedListener() {
                        @Override
                        public void onLocationUpdated(Location location) {
                            new LocationHelper().decodeLocation(location,getApplicationContext());
                            rm.setCoordinates(""+location.getLatitude()+","+location.getLongitude());
                            new DBOps().UpdateLocation(getApplicationContext(),Globals.url,rm);
                            Toast.makeText(getApplicationContext(),"Location Updated",Toast.LENGTH_SHORT).show();
                        }

                    });

                }
            });
        }
    }
    private void showNotification() {
// In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = "Hope Team Locating...";


// Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.pdlg_icon_success, text,
                System.currentTimeMillis());
        notification.flags = Notification.FLAG_NO_CLEAR;
// The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        notification.contentIntent=contentIntent;
        notification.tickerText=text;

// Set the info for the views that show in the notification panel.
//notification.contentIntent.send(this,0,contentIntent);


        notificationManager.notify(0, notification);
    }
}