package com.example.amit.facebooklogindemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AfterLoginPage extends AppCompatActivity {

    ProgressBar progressBar;
    TextView firstName, lastName, email, gender, url;
    String imageUrl;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_page);

        firstName = findViewById(R.id.firstname_tv);
        lastName = findViewById(R.id.lastname_tv);
        email = findViewById(R.id.email_tv);
        gender = findViewById(R.id.gender_tv);
        url = findViewById(R.id.url_tv);
        progressBar = findViewById(R.id.prgs);
        photo = findViewById(R.id.user_img);

        firstName.setText(getIntent().getExtras().getString("first_name"));
        lastName.setText(getIntent().getExtras().getString("last_name"));
        email.setText(getIntent().getExtras().getString("email"));
        gender.setText(getIntent().getExtras().getString("gender"));
        url.setText(getIntent().getExtras().getString("idFacebook"));

        imageUrl = getIntent().getExtras().getString("profile_pic");

        UserLoadAsyncTask userLoadAsyncTask = new UserLoadAsyncTask();
        userLoadAsyncTask.execute();

    }

    public class UserLoadAsyncTask extends AsyncTask<Void, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                URL urlConnection = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            photo.setImageResource(R.drawable.image_not_available);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            photo.setImageBitmap(result);
        }
    }
}
