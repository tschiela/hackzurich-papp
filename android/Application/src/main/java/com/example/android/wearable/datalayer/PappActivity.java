package com.example.android.wearable.datalayer;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.example.android.wearable.datalayer.services.PappService;

import java.util.List;


public class PappActivity extends Activity {
    public static final String TAG = "PappActivity";

    public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
    public static final String EXTRAS_BEACON = "extrasBeacon";

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);


    BeaconManager beaconManager;
    PappService pappService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_parking);

        pappService = new PappService();

        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> rangedBeacons) {
                // Note that results are not delivered on UI thread.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for (Beacon rangedBeacon : rangedBeacons) {
//                            if (rangedBeacon.getMacAddress().equals(beacon.getMacAddress())) {
//                                foundBeacon = rangedBeacon;
//                            }
                            if(rangedBeacon.getRssi() >-80){ //TODO: Make dbNeeded Parameter
                                parkingLotRecognized(rangedBeacon);
                            }
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                } catch (RemoteException e) {
                    Toast.makeText(PappActivity.this, "Cannot start ranging, something terrible happened",
                            Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        beaconManager.disconnect();

        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.auto_parking, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void startService(){

    }

    long lastBeconApearanceTime = 0;

    public void parkingLotRecognized(Beacon beaconNextToYou){
        Log.d("PappActivity","beacon recognized");
//

        if(System.currentTimeMillis() - lastBeconApearanceTime > 10000){
            if(parkingStartTime > 0){
                callCheckoutService();
            }else{
                callCheckInService();
            }

        }

    }

    protected void callCheckoutService(){

        if(pappService.checkOut(sessionID)){
            long millis = System.currentTimeMillis()-parkingStartTime;
            int minutes = (int) (((millis / 1000) / 60) % 60);
            int hours = (int) ((((millis / 1000) / 60) / 60) % 24);
            String minutStr = minutes < 10 ? "0"+minutes:""+minutes;
            setCheckOutNotification(""+hours+":"+minutStr  //Todo: use the json data sent from the backend
            ,String.valueOf((hours+Math.min(1,minutes))*2)+"CHF");

            sessionID = null;
            lastBeconApearanceTime = System.currentTimeMillis();
            parkingStartTime = 0;

        }
    }
    private String sessionID;
    public void callCheckInService(){
        String result = pappService.checkIn();

        if(result != null) {
            sessionID = result;
            setParkingStartTime();

            lastBeconApearanceTime = System.currentTimeMillis();
            setParkingStartTime();
            setParkingStartTime(); //Todo:get the time from BE
            setCheckInNotification();
        }
    }
    long parkingStartTime;

    public void setParkingStartTime(){
        parkingStartTime = System.currentTimeMillis();

    }
    int notificationId = 001;

    public void pushNotification(String bigText,String title, String text){
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText(bigText);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.drawable.ic_launcher))
                        .setContentTitle(title)
                        .setContentText(text)
//                        .setContentIntent(viewPendingIntent)
//                        .addAction(R.drawable.ic_map,
//                                getString(R.string.map), mapPendingIntent)
                        .setStyle(bigStyle)
                ;

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

// Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    public void setCheckOutNotification(String parkingTime,String cost){
        pushNotification(
                "you where checked in for "+parkingTime+" at Uber Store The Fee is about: "+cost
                ,"Papp check in"
                ,"Uber Store");
    }

    public void setCheckInNotification(){
       pushNotification(
               "checked in Parking Lot of Uber Store"
                ,"Papp check in"
                ,"Uber Store");
    }

    public void sendUpdateDataToWear(){

    }

}
