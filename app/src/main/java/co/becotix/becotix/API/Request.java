package co.becotix.becotix.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Request {
    public interface OnResultListener {
        void onSuccess(String response);
        void onFailure();
    }

    private static final String LOG_TAG = Request.class.getName();
    private Context mContext;

    public Request(Context context) {
        mContext = context;
    }

    public void processPost(String url, final OnResultListener onResultListener) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        Log.v(LOG_TAG, "Starting POST " + url);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.v(LOG_TAG, "Request success - response: " + response);
                    onResultListener.onSuccess(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.v(LOG_TAG, volleyError.toString());
                    onResultListener.onFailure();
                }
            });
        queue.add(stringRequest);
    }

    public void processGet(String url, final OnResultListener onResultListener) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        Log.v(LOG_TAG, "Starting GET " + url);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.v(LOG_TAG, "Request success - response: " + response);
                    onResultListener.onSuccess(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.v(LOG_TAG, volleyError.toString());
                    onResultListener.onFailure();
                }
            });
        queue.add(stringRequest);
    }
}
