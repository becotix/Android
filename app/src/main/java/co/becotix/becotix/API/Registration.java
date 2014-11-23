package co.becotix.becotix.API;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;

import co.becotix.becotix.MainActivity;
import co.becotix.becotix.R;

public class Registration {

    private final static String LOG_TAG = Registration.class.getName();
    private final static String REGISTRATION_NAME = "REGISTRATION_NAME";
    private final static String REGISTRATION_EMAIL = "REGISTRATION_EMAIL";
    private final static String REGISTRATION_ADDRESS_1 = "REGISTRATION_ADDRESS_1";
    private final static String REGISTRATION_ADDRESS_2 = "REGISTRATION_ADDRESS_2";
    private final static String REGISTRATION_CITY = "REGISTRATION_CITY";
    private final static String REGISTRATION_REGION = "REGISTRATION_REGION";
    private final static String REGISTRATION_POSTCODE = "REGISTRATION_POSTCODE";

    public  final static String REMOTE_ID = "REMOTE_ID";

    public String name;
    public String email;
    public String hashed_password;
    public String address_1;
    public String address_2;
    public String city;
    public String region;
    public String postcode;
    public String cc_number;

    public Integer remoteId;

    private Activity mActivity;

    public Registration(Activity activity) {
        super();

        mActivity = activity;

        try {
            this.name = ((EditText) mActivity.findViewById(R.id.name)).getText().toString();
            this.email = ((EditText) mActivity.findViewById(R.id.email)).getText().toString();
            this.address_1 = ((EditText) mActivity.findViewById(R.id.address_1)).getText().toString();
            this.address_2 = ((EditText) mActivity.findViewById(R.id.address_2)).getText().toString();
            this.city = ((EditText) mActivity.findViewById(R.id.city)).getText().toString();
            this.region = ((EditText) mActivity.findViewById(R.id.region)).getText().toString();
            this.postcode = ((EditText) mActivity.findViewById(R.id.postcode)).getText().toString();
            this.cc_number = ((EditText) mActivity.findViewById(R.id.cc_number)).getText().toString();
        } catch (NullPointerException e) {

        }
        this.remoteId = 0;

        // TODO store securely
        // TODO password hash
    }

    public boolean save() {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REGISTRATION_NAME, this.name);
        editor.putString(REGISTRATION_EMAIL, this.email);
        editor.putString(REGISTRATION_ADDRESS_1, this.address_1);
        editor.putString(REGISTRATION_ADDRESS_2, this.address_2);
        editor.putString(REGISTRATION_CITY, this.city);
        editor.putString(REGISTRATION_REGION, this.region);
        editor.putString(REGISTRATION_POSTCODE, this.postcode);
        editor.putInt(REMOTE_ID, this.remoteId);
        return editor.commit();
    }

    public void load() {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        this.name = sharedPreferences.getString(REGISTRATION_NAME, "Nick Bolt");
        this.email = sharedPreferences.getString(REGISTRATION_EMAIL, "email@nickbolt.co.uk");
        this.address_1 = sharedPreferences.getString(REGISTRATION_ADDRESS_1, "123 Fake Street");
        this.address_2 = sharedPreferences.getString(REGISTRATION_ADDRESS_2, "Fakestown");
        this.city = sharedPreferences.getString(REGISTRATION_CITY, "Sheffield");
        this.region = sharedPreferences.getString(REGISTRATION_REGION, "South Yorkshire");
        this.postcode = sharedPreferences.getString(REGISTRATION_POSTCODE, "S2 1HG");
        this.remoteId = sharedPreferences.getInt(REMOTE_ID, 0);

        try {
            ((EditText) mActivity.findViewById(R.id.name)).setText(this.name);
            ((EditText) mActivity.findViewById(R.id.email)).setText(this.email);
            ((EditText) mActivity.findViewById(R.id.address_1)).setText(this.address_1);
            ((EditText) mActivity.findViewById(R.id.address_2)).setText(this.address_2);
            ((EditText) mActivity.findViewById(R.id.city)).setText(this.city);
            ((EditText) mActivity.findViewById(R.id.region)).setText(this.region);
            ((EditText) mActivity.findViewById(R.id.postcode)).setText(this.postcode);
        } catch (NullPointerException e) {

        }
    }

    public boolean isValid() {
        if (this.name.equals("")) {
            return false;
        }
        if (this.email.equals("") || this.email.indexOf("@") == -1) {
            return false;
        }
        if (this.address_1.equals("")) {
            return false;
        }
        if (this.address_2.equals("")) {
            return false;
        }
        if (this.region.equals("")) {
            return false;
        }
        if (this.city.equals("")) {
            return false;
        }
        return true;
    }

    public boolean isRegistered() {
        if (this.remoteId != 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
