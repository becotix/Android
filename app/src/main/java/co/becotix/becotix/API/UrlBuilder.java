package co.becotix.becotix.API;

import android.content.Context;
import android.net.Uri;

import co.becotix.becotix.R;

public class UrlBuilder {

    private Context mContext;

    public UrlBuilder(Context context) {
        super();
        this.mContext = context;
    }

    private Uri.Builder baseUriBuilder() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("becotix.herokuapp.com");
        return builder;
    }

    public String registration(Registration pRegistration) {
        Uri.Builder builder = baseUriBuilder()
                .appendPath("api")
                .appendPath("register")
                .appendQueryParameter("name", pRegistration.name)
                .appendQueryParameter("email", pRegistration.email)
                .appendQueryParameter("address_1", pRegistration.address_1)
                .appendQueryParameter("address_2", pRegistration.address_2)
                .appendQueryParameter("city", pRegistration.city)
                .appendQueryParameter("region", pRegistration.region)
                .appendQueryParameter("postcode", pRegistration.postcode)
                .appendQueryParameter("cc_hash", pRegistration.cc_number)
                .appendQueryParameter("password_hash", pRegistration.hashed_password);
        return builder.toString();
    }

    public String stopInfo() {
        Uri.Builder builder = baseUriBuilder()
                .appendPath("api")
                .appendPath("stop_info");
        return builder.toString();
    }

    public String tickets() {
        Uri.Builder builder = baseUriBuilder()
                .appendPath("api")
                .appendPath("tickets");
        return builder.toString();
    }

}
