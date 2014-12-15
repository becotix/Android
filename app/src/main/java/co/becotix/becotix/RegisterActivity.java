package co.becotix.becotix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.becotix.becotix.API.Registration;
import co.becotix.becotix.API.Request;
import co.becotix.becotix.API.UrlBuilder;
import co.becotix.becotix.DB.BusBeacon;
import co.becotix.becotix.DB.StopInfo;
import co.becotix.becotix.DB.Ticket;


public class RegisterActivity extends Activity {

    public final static String PREFS_NAME = "myPrefs";

    private final static String LOG_TAG = RegisterActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Registration registration = new Registration(this);
        if (registration.isRegistered()) {
            goToDashboardActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button submit = (Button) findViewById(R.id.submit);
        final Activity fActivity = this;
        final Context fContext = this;
        Registration registration = new Registration(this);
        registration.load();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registration registration = new Registration(fActivity);
                registration.save();
                if (registration.isValid()) {
                    sendRegistrationRequest(fContext, registration);
                }
                else {
                    Toast.makeText(fActivity, R.string.activity_registration_error_message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendRegistrationRequest(final Context context, Registration registration) {
        Request request = new Request(context);
        UrlBuilder urlBuilder = new UrlBuilder(context);
        request.processPost(urlBuilder.registration(registration), new Request.OnResultListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int remoteId = Integer.parseInt(jsonObject.getString("uid"));
                    SharedPreferences sharedPreferences = getSharedPreferences(RegisterActivity.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(Registration.REMOTE_ID, remoteId);
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "JSON Parse Error", Toast.LENGTH_SHORT).show();
                }
                sendStopInfoRequest(context);
            }

            @Override
            public void onFailure() {
                Toast.makeText(context, "Registration Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendStopInfoRequest(final Context context) {
        Request request = new Request(context);
        UrlBuilder urlBuilder = new UrlBuilder(context);
        request.processGet(urlBuilder.stopInfo(), new Request.OnResultListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Process bus beacons
                    BusBeacon.destroyAll();
                    JSONArray busBeacons = jsonObject.getJSONArray("beacons");
                    for (int i = 0 ; i < busBeacons.length() ; i++) {
                        JSONObject busBeacon = busBeacons.getJSONObject(i);
                        BusBeacon dbBusBeacon = new BusBeacon();
                        dbBusBeacon.remote_id = Long.parseLong(busBeacon.getString("id"));
                        dbBusBeacon.major = Integer.parseInt(busBeacon.getString("major"));
                        dbBusBeacon.minor = Integer.parseInt(busBeacon.getString("minor"));
                        dbBusBeacon.save();
                    }
                    Log.v(LOG_TAG, "Created " + BusBeacon.all().size() + " bus beacons");

                    // Process stops
                    StopInfo.destroyAll();
                    JSONArray stopInfos = jsonObject.getJSONArray("stops");
                    for (int i = 0 ; i < stopInfos.length() ; i++) {
                        JSONObject stopInfo = stopInfos.getJSONObject(i);
                        StopInfo dbStopInfo = new StopInfo();
                        dbStopInfo.name = stopInfo.getString("name");
                        dbStopInfo.remoteId = stopInfo.getLong("id");
                        BusBeacon busBeacon = BusBeacon.find(Long.parseLong(stopInfo.getString("beacon_id")));
                        dbStopInfo.major = busBeacon.major;
                        dbStopInfo.minor = busBeacon.minor;
                        dbStopInfo.save();
                    }
                    Log.v(LOG_TAG, "Created " + StopInfo.all().size() + " stops");

                    sendTicketsRequest(context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(context, "Stop Info Request Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendTicketsRequest(final Context context) {
        Request request = new Request(context);
        UrlBuilder urlBuilder = new UrlBuilder(context);
        request.processGet(urlBuilder.tickets(), new Request.OnResultListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Ticket.destroyAll();
                    JSONArray tickets = jsonObject.getJSONArray("tickets");
                    for (int i = 0 ; i < tickets.length() ; i++) {
                        JSONObject ticket = tickets.getJSONObject(i);
                        Ticket dbTicket = new Ticket();
                        dbTicket.word = ticket.getString("word");
                        dbTicket.color = ticket.getString("colour");
                        dbTicket.save();
                    }
                    Log.v(LOG_TAG, "Created " + Ticket.all().size() + " tickets");

                    goToDashboardActivity();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Ticket request failure", Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onFailure() {

            }
        });
    }

    private void goToDashboardActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
