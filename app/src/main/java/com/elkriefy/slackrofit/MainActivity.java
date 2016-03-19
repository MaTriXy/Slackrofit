package com.elkriefy.slackrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private SlackApi slackApi;
    private Call<SlackApi.UploadFileResponse> call;
    private RequestBody file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        slackApi = new Retrofit.Builder().baseUrl("https://slack.com/").client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(SlackApi.class);


        String str = "Google Places API for Android Samples\n" +
                "===================================\n" +
                "\n" +
                "Samples that use the [Google Places API for Android](https://developers.google.com/places/android/).\n" +
                "\n" +
                "This repo contains the following samples:";
        file = RequestBody.create(MediaType.parse("multipart/form-data"), str.getBytes());

        Map<String, RequestBody> map = new HashMap<>();
        map.put("file\"; filename=\"heapDump.md\"", file);
        call = slackApi.uploadFile(SlackApi.TOKEN, map, "text", "heapDump.md", "Test Dump", "Check this out", SlackApi.MEMORY_LEAK_CHANNEL);
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickMe(View view) {

        call.clone().enqueue(new Callback<SlackApi.UploadFileResponse>() {
            @Override
            public void onResponse(Call<SlackApi.UploadFileResponse> call, Response<SlackApi.UploadFileResponse> response) {
                if (response != null) {
                    Log.e("GAG", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<SlackApi.UploadFileResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
