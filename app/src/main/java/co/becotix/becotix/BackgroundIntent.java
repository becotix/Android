package co.becotix.becotix;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class BackgroundIntent extends IntentService {

    private static final String LOG_TAG = IntentService.class.toString();

    public BackgroundIntent() {
        super("BackgroundIntent");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AlarmManager service = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, StartBackgroundIntentReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        service.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                cal.getTimeInMillis(), 1000*5, pending);
    }
}
