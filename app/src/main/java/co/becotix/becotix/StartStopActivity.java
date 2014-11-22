package co.becotix.becotix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.becotix.becotix.DB.StopInfo;


public class StartStopActivity extends Activity {

    public static final String STOP_INFO_ID = "STOP_INFO_ID";

    StopInfo mStopInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            mStopInfo = StopInfo.find(intent.getLongExtra(STOP_INFO_ID, 0));
        }
        if (savedInstanceState != null) {
            mStopInfo = StopInfo.find(savedInstanceState.getInt(STOP_INFO_ID));
        }

        setContentView(R.layout.activity_start_stop);

        TextView stopName = (TextView) findViewById(R.id.stop_name);
        stopName.setText(mStopInfo.name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(STOP_INFO_ID, mStopInfo.getId());
        super.onSaveInstanceState(outState);
    }
}
