package de.dhbw.meetme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HelloActivity extends Activity {

    private static final String TAG = "HelloActivity";
    private static final String HOSTNAME = "<here your IP or hostname>";
    private static final int PORT = 8087;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_layout);
        Log.e(TAG, "run client");

        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            // specify the host, protocol, and port
            HttpHost target = new HttpHost(HOSTNAME, PORT, "http");

            // specify the get request
            HttpGet getRequest = new HttpGet("/meetmeserver/api/user/list");
            HttpResponse httpResponse = httpclient.execute(target, getRequest);
            HttpEntity entity = httpResponse.getEntity();
            Log.e(TAG, EntityUtils.toString(entity));

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText("Hello world!");
    }

}
