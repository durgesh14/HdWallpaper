package com.andro.theflash.walls;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import com.andro.theflash.walls.adapter.RvAnime;
import com.andro.theflash.walls.adapter.RvcatAdapter;
import com.andro.theflash.walls.model.Walls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Anime_Activity extends BaseActivity {
    RecyclerView recyclerView_anime;
    private String TAG = MainActivity.class.getSimpleName();
    private List<Walls> wallpaper = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_anime_, contentFrameLayout);
        new AnimeWall().execute();
        recyclerView_anime = findViewById(R.id.rvanime);
        //setContentView(R.layout.activity_anime_);
    }

    private class AnimeWall extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();


            final String jsonCat = sh.makeServiceCall("https://wall.alphacoders.com/api2.0/get.php?auth=321a42093f3753ca40879d07bcfbd714&method=category&id=3&page=1&info_level=2");

            Log.e(TAG, "Response from Anime url: " + jsonCat);
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
            RvAnime myAdapter1 = new RvAnime(Anime_Activity.this, wallpaper);
            recyclerView_anime.setLayoutManager(new LinearLayoutManager(Anime_Activity.this));
            recyclerView_anime.setAdapter(myAdapter1);
        }
    }
}
