package com.devcru.staylocated;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by S01L02 on 7/10/2015.
 */
public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final String access_token = "";

        final String TAG = "RegisterActivity";

        final String initOauthReq = "";

        final String registerPostUrl = "https://staylocated.herokuapp.com/users/?access_token=" + access_token;

        final EditText editUser = (EditText) findViewById(R.id.editUser);
        final EditText editPass = (EditText) findViewById(R.id.editPass);

        final Button login = (Button) findViewById(R.id.login);
        final Button completeRegistration = (Button) findViewById(R.id.signup);

        completeRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "onClick reached!");
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost post = new HttpPost(registerPostUrl);
                post.setHeader("content-type", "application/json; charset=UTF-8");

                // Construct JSON object
                JSONObject data = new JSONObject();

                Log.i(TAG, "editUser.getText().toString(): " + editUser.getText().toString());
                Log.i(TAG, "editPass.getText().toString(): " + editPass.getText().toString());

                try {
                    data.put("username", editUser.getText().toString());
                    data.put("password", editPass.getText().toString());

                    Log.i(TAG, "data: " + data.toString());

                    StringEntity entity = new StringEntity(data.toString());
                    post.setEntity(entity);

                    Log.i(TAG, "");

                    HttpResponse resp = httpClient.execute(post);
                    String respStr = EntityUtils.toString(resp.getEntity());

                    Log.i(TAG, "OKAY: " + respStr);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                URL url = null;
                try {
                    url = new URL("https://google.com");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    connection.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                // Read response
                try {
                    Log.i(TAG, "ResponseCode: " + connection.getResponseCode());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InputStream in = null;
                try {
                    in = new BufferedInputStream(connection.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
               /* String response = "";
                try {
                    response = IOUtils.toString(in, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "Response: " + response);*/

            }
        });
    }

    // Intredasting stuff -- review later
    private static JSONArray loadFromDatabaseNew(String phpFileAddress){
        JSONArray jsonArray = null;

        try {
            URL url = new URL(phpFileAddress);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            jsonArray = new JSONArray(responseStrBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}