package com.example.final_android;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.EditText;


import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private TextView movieInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieInfoTextView = findViewById(R.id.movie_info_text_view);

        // Example search term
        String searchTerm = "Nemo";

        // Make the network request
        fetchMovieInfo(searchTerm);
    }

    private void fetchMovieInfo(String searchTerm) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-RapidAPI-Key", "a46065425fmsh6bc3eb4e8517312p17619ejsn537ee3169b0d");
        client.addHeader("X-RapidAPI-Host", "movie-database-alternative.p.rapidapi.com");

        String url = "https://movie-database-alternative.p.rapidapi.com/?s=" + searchTerm +"&page=1";

        client.get(url, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("Search");
                    if (results.length() > 0) {
                        JSONObject movie = results.getJSONObject(0);
                        String title = movie.getString("Title");
                        String year = movie.getString("Year");
                        movieInfoTextView.setText("Title: " + title + "\nYear: " + year);
                    } else {
                        movieInfoTextView.setText("No movie found");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // Handle failure
                movieInfoTextView.setText("Failed to fetch movie information");
            }
        });
    }
}