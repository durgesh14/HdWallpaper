package com.andro.theflash.walls;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andro.theflash.walls.adapter.RvAdapter;
import com.andro.theflash.walls.model.Walls;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends BaseActivity {

    private String Url = "https://api.myjson.com/bins/vxodg";
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Walls> wallpaper = new ArrayList<>();
    private RecyclerView myrv;

    ArrayList<HashMap<String, String>> contactList;
    private String TAG = MainActivity.class.getSimpleName();
    GetContacts getContacts;
  //  CategoryWall categoryWall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
     //setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        myrv = findViewById(R.id.rv);

        new GetContacts().execute();
  // new CategoryWall().execute();


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              //  categoryWall = new CategoryWall();
//              //  categoryWall.execute();
//                Intent i = new Intent(MainActivity.this,Category.class);
//                startActivity(i);
//
//
//            }
//        });

        //  jsoncall();
    }
    private void jsoncall() {

        ArrayRequest = new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                        // JSONArray Wallpaper = new JSONArray(jsonObject.getString("wallpapers"));
                        Walls walls = new Walls();
//                        walls.setName(jsonObject.getString("sub_category"));
//                        walls.setThumb_url(jsonObject.getString("url_thumb"));
//                        walls.setImg_url(jsonObject.getString("url_image"));
//                        JSONObject w = jsonObject.getJSONObject("wallpapers");
//                        walls.setName(w.getString("sub_category"));
//                        walls.setThumb_url(w.getString("url_thumb"));
//                        Toast.makeText(MainActivity.this, "aa"+w, Toast.LENGTH_SHORT).show();


                        wallpaper.add(walls);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                // setRvadapter(wallpaper);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //  requestQueue = Volley.newRequestQueue(MainActivity.this);
        //  requestQueue.add(ArrayRequest);
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            final String jsonStr = sh.makeServiceCall("https://wall.alphacoders.com/api2.0/get.php?auth=321a42093f3753ca40879d07bcfbd714&method=newest&page=1&info_level=2");

            Log.e(TAG, "Response from url: " + jsonStr);
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray wallpapersArray = jsonObj.getJSONArray("wallpapers");
                for (int i = 0; i < wallpapersArray.length(); i++) {
                    JSONObject c = wallpapersArray.getJSONObject(i);
                    String thumbname = c.getString("sub_category");
                    String thumbUrl = c.getString("url_thumb");
                    String imgUrl = c.getString("url_image");
                    Walls walls = new Walls();
                    walls.setName(thumbname);
                    walls.setThumb_url(thumbUrl);
                    walls.setImg_url(imgUrl);
                    wallpaper.add(walls);
                    Log.e(TAG, "thumbname: " + wallpaper);
                }
                   // setRvadapter(wallpaper);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

          RvAdapter myAdapter = new RvAdapter(MainActivity.this, wallpaper);

          myrv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            myrv.setAdapter(myAdapter);
        }
    }
    private void setRvadapter (List < Walls > wallpaper) {

        RvAdapter myAdapter = new RvAdapter(this, wallpaper);
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(myAdapter);


    }

/*
    private class CategoryWall extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            final String jsonCat = sh.makeServiceCall("https://wall.alphacoders.com/api2.0/get.php?auth=321a42093f3753ca40879d07bcfbd714&method=category&id=10&page=10&info_level=2");

          Log.e(TAG, "Response from cat url: " + jsonCat);
            try {
                JSONObject jsonObj = new JSONObject(jsonCat);
                JSONArray wallpapersArray = jsonObj.getJSONArray("wallpapers");
                for (int i = 0; i < wallpapersArray.length(); i++) {
                    JSONObject c = wallpapersArray.getJSONObject(i);
                    String thumbname = c.getString("sub_category");
                    String thumbUrl = c.getString("url_thumb");
                    String imgUrl = c.getString("url_image");
                    Walls walls = new Walls();
                    walls.setName(thumbname);
                    walls.setThumb_url(thumbUrl);
                    walls.setImg_url(imgUrl);
                    wallpaper.add(walls);
                    Log.e(TAG, "thumbname: " + wallpaper);
                }
                // setRvadapter(wallpaper);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RvAdapter myAdapter1 = new RvAdapter(MainActivity.this, wallpaper);
            myrv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            myrv.setAdapter(myAdapter1);
        }
    }
    */

}
