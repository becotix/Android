package co.becotix.becotix;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import co.becotix.becotix.DB.Journey;
import co.becotix.becotix.DB.StopInfo;

public class JourneyManager {

    private static final String LOG_TAG = JourneyManager.class.getName();
    private static final String CURRENT_JOURNEY_ID = "CURRENT_JOURNEY_ID";

    Context mContext;

    public JourneyManager(Context context) {
        mContext = context;
    }

    public void addStart(StopInfo stopInfo) {
        Log.v(LOG_TAG, "Start journey at " + stopInfo.name);
        Journey journey = new Journey();
        journey.start_id = stopInfo.remoteId;
        journey.start_time = currentTime();
        journey.save();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(CURRENT_JOURNEY_ID, journey.getId());
        editor.commit();
    }

    public void addEnd(StopInfo stopInfo) {
        Log.v(LOG_TAG, "End journey at " + stopInfo.name);
        Journey journey = current();
        journey.end_id = stopInfo.remoteId;
        journey.end_time = currentTime();
        journey.save();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CURRENT_JOURNEY_ID);
        editor.commit();
    }

    public Journey current() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        long current_id = sharedPreferences.getLong(CURRENT_JOURNEY_ID, 0);
        if (current_id != 0) {
            Journey journey = Journey.find(current_id);
            if (journey != null) {
                return journey;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    private static String currentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        return dateFormat.format(new Date()).toString();
    }

}
