package com.andro.theflash.walls;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import de.hdodenhof.circleimageview.CircleImageView;



public class HdWallpaper_Activity extends AppCompatActivity {
    Button button,setwall;

    TouchImageView iv;
    private String TAG = MainActivity.class.getSimpleName();
    String image_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hd_wallpaper_);
        iv = findViewById(R.id.imageView);
        button =  findViewById(R.id.button);
        setwall = findViewById(R.id.set_wall);


        getSupportActionBar().hide();
       image_name = getIntent().getExtras().getString("imagename");


        final String image_url = getIntent().getExtras().getString("wall") ;
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        DoubleBounce doubleBounce = new DoubleBounce();

        progressBar.setIndeterminateDrawable(doubleBounce);

       // Glide.with(this).load(image_url).apply(requestOptions).into(iv);
       // CircleImageView imageView = findViewById(R.id.profile_image);
        Picasso.get().load(image_url).into(iv, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });



button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        new DownloadFile().execute(image_url);



    }
});



        Log.e(TAG, "image: " + image_url);

setwall.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        Bitmap bitmap = ((BitmapDrawable)iv.getDrawable()).getBitmap();
        WallpaperManager myWallpaperManager = WallpaperManager
                .getInstance(getApplicationContext());

        try {

            myWallpaperManager.setBitmap(bitmap);
            Toast.makeText(HdWallpaper_Activity.this, "Wallpaper set",
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(HdWallpaper_Activity.this,
                    "Error setting wallpaper", Toast.LENGTH_SHORT)
                    .show();
        }




    }
});
    }


    class DownloadFile extends AsyncTask<String,Integer,Long> {

        ProgressDialog mProgressDialog = new ProgressDialog(HdWallpaper_Activity.this);// Change Mainactivity.this with your activity name.
        String strFolderName;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Downloading");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.show();
        }
        @Override
        protected Long doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL((String) aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                String targetFileName=image_name+".jpg";//Change name and subname
                int lenghtOfFile = conexion.getContentLength();
                String PATH = Environment.getExternalStorageDirectory()+ "/"+"Walls"+"/";
                File folder = new File(PATH);
                if(!folder.exists()){
                    folder.mkdir();//If there is no folder it will be created.
                }
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(PATH+targetFileName);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress ((int)(total*100/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {}
            return null;
        }
        protected void onProgressUpdate(Integer... progress) {
            mProgressDialog.setProgress(progress[0]);
            if(mProgressDialog.getProgress()==mProgressDialog.getMax()){
                mProgressDialog.dismiss();
                //Toast.makeText(fa, "File Downloaded", Toast.LENGTH_SHORT).show();
                Toast.makeText(HdWallpaper_Activity.this, "Downloded", Toast.LENGTH_SHORT).show();
                Toast.makeText(HdWallpaper_Activity.this, "n"+image_name, Toast.LENGTH_SHORT).show();
            }
        }
        protected void onPostExecute(String result) {
        }
    }

}
