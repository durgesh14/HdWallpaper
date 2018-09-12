package com.andro.theflash.walls;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.andro.theflash.walls.adapter.RvAnime;
import com.andro.theflash.walls.adapter.RvSearch;
import com.andro.theflash.walls.model.Walls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    RecyclerView recyclerView_search;
    EditText searchname;
    Button searchbutton;
    private String TAG = MainActivity.class.getSimpleName();
    private List<Walls> wallpaper = new ArrayList<>();
    RvSearch mAdapter=new RvSearch(SearchActivity.this,wallpaper);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_search, contentFrameLayout);

        recyclerView_search = findViewById(R.id.recycler_search);
        searchbutton = findViewById(R.id.namesearch_button);
        searchname = findViewById(R.id.edt_search);
        final boolean[] showingFirst = {true};
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchname.getText().toString();
                if(showingFirst[0] == true) {
                    new SearchWall().execute(search);
                    showingFirst[0] = false;
                }
                else {
                    Toast.makeText(SearchActivity.this, "Second click", Toast.LENGTH_SHORT).show();
                    OnSuccess(mAdapter);
                }


            }
        });

       // setContentView(R.layout.activity_search);




    }

    private void OnSuccess(RvSearch mAdapter) {



    }

    private class SearchWall extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... voids) {

            HttpHandler sh = new HttpHandler();


            String searchurl = "https://wall.alphacoders.com/api2.0/get.php?auth=321a42093f3753ca40879d07bcfbd714&page=1&method=search&term="+voids[0]+"";
            final String jsonCat = sh.makeServiceCall(searchurl);
           // Log.e(TAG, "Response from search url: " + jsonCat);
            Log.e(TAG, "Response search name: " + searchurl);
            try {
                JSONObject jsonObj = new JSONObject(jsonCat);

                JSONArray wallpapersArray = jsonObj.getJSONArray("wallpapers");


                for (int i = 0; i < wallpapersArray.length(); i++) {
                    JSONObject c = wallpapersArray.getJSONObject(i);
                //    String thumbname = c.getString("sub_category");
                  //  Log.e(TAG, "thumbname: " + thumbname);

                    String thumbUrl = c.getString("url_thumb");
                    String imgUrl = c.getString("url_image");
                    Walls walls = new Walls();
                  //  walls.setName(thumbname);
                    walls.setThumb_url(thumbUrl);
                    walls.setImg_url(imgUrl);
                    wallpaper.add(walls);
                   // Log.e(TAG, "thumbname: " + wallpaper);
                }
                // setRvadapter(wallpaper);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return searchurl;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            RvSearch myAdapter1 = new RvSearch(SearchActivity.this, wallpaper);
            recyclerView_search.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            recyclerView_search.setAdapter(myAdapter1);
            /*-------------------------------------------------------------------------------------------------*/
//                       myAdapter1.clear();
//                       myAdapter1.addall(wallpaper);
//                       myAdapter1.notifyDataSetChanged();

            /*-------------------------------------------------------------------------------------------------*/

        }
    }
}
