package co.becotix.becotix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import co.becotix.becotix.Adapters.StopAdapter;
import co.becotix.becotix.DB.StopInfo;


public class StartStopsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_stops);
        ListView listView = (ListView) findViewById(R.id.list_view);
        final StopAdapter stopAdapter = new StopAdapter(this, 0, StopInfo.all());
        listView.setAdapter(stopAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StopInfo stopInfo = stopAdapter.getItem(position);
                goToStartStopActivity(stopInfo);
            }
        });
    }

    private void goToStartStopActivity(StopInfo stopInfo) {
        Intent intent = new Intent(this, StartStopActivity.class);
        intent.putExtra(StartStopActivity.STOP_INFO_ID, stopInfo.getId());
        startActivity(intent);
    }

}
