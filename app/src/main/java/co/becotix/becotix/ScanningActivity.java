package co.becotix.becotix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;

import co.becotix.becotix.DB.StopInfo;

public class ScanningActivity extends Activity {

    protected static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    protected static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId", ESTIMOTE_PROXIMITY_UUID, null, null);
    protected static final String LAST_MAJOR = "LAST_MAJOR";

    protected BeaconManager beaconManager;
    protected final static String LOG_TAG = ScanningActivity.class.getName();

    @Override
    protected void onStart() {
        super.onStart();
        onCreateBeaconActions(this);
        onStartBeaconActions();
    }

    @Override
    protected void onStop() {
        super.onStop();
        onDestroyBeaconActions();
    }


    protected void onCreateBeaconActions(final Context context) {
        Log.v(LOG_TAG, "OnCreateBeaconActivity");
        beaconManager = new BeaconManager(context);
        final Context fContext = this;
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
                Log.v(LOG_TAG, "Ranged beacons: " + beacons);
                if (beacons.size() > 0) {
                    Beacon beacon = beacons.get(0);
                    StopInfo stopInfo = StopInfo.findByMajor(beacon.getMajor());
                    SharedPreferences sharedPreferences = fContext.getSharedPreferences(MainActivity.PREFS_NAME, 0);
                    int lastMajor = sharedPreferences.getInt(LAST_MAJOR, 0);
                    if (lastMajor != beacon.getMajor() && stopInfo != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(LAST_MAJOR, beacon.getMajor());
                        editor.commit();
                        JourneyManager journeyManager = new JourneyManager(fContext);
                        if (journeyManager.current() == null) {
                            Log.v(LOG_TAG, "Going to start journey for " + stopInfo.name);
                            goToStartStopActivity(stopInfo);
                        }
                        else {
                            Log.v(LOG_TAG, "Going to stop journey for " + stopInfo.name);
                            goToEndStopActivity(stopInfo);
                        }
                    }
                }
            }
        });
    }

    protected void onStartBeaconActions() {
        Log.v(LOG_TAG, "OnStartBeaconActivity");
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.v(LOG_TAG, "Cannot start ranging", e);
                }
            }
        });
    }

    protected void onDestroyBeaconActions() {
        Log.v(LOG_TAG, "OnDestroyBeaconActivity");
        beaconManager.disconnect();
    }

    protected void goToStartStopActivity(StopInfo stopInfo) {
        Intent intent = new Intent(this, StartStopActivity.class);
        intent.putExtra(StartStopActivity.STOP_INFO_ID, stopInfo.getId());
        startActivity(intent);
    }

    protected void goToEndStopActivity(StopInfo stopInfo) {
        Intent intent = new Intent(this, EndStopActivity.class);
        intent.putExtra(EndStopActivity.STOP_INFO_ID, stopInfo.getId());
        startActivity(intent);
    }
}
