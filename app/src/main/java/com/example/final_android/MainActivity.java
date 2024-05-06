package com.example.final_android;

import android.os.Bundle;

import com.squareup.picasso.Picasso;
import android.widget.ImageView;
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

    private TextView songInfoTextView;
    private TextInputEditText inputText;
    //TEsting ImageURL
    String imageUrl = "https://is5-ssl.mzstatic.com/image/thumb/Features124/v4/4e/0d/1f/4e0d1ff5-9f2e-0170-0ec9-3dbe25f3a471/pr_source.png/800x800cc.jpg"; // Replace with your image URL
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songInfoTextView = findViewById(R.id.song_info_text_view);
        inputText = findViewById(R.id.input_text);
        Button searchButton = findViewById(R.id.search_button);
        ImageView imageView = findViewById(R.id.song_image_view);


        searchButton.setOnClickListener(new View.OnClickListener() {//when you click button, run this method
            @Override
            public void onClick(View v) {
                String searchTerm = inputText.getText().toString().trim();
                if (!searchTerm.isEmpty()) {//if input text view NOT empty, then run the method
                    fetchSongDetails(searchTerm);
                }
            }
        });
    }

    private void fetchSongDetails(String searchTerm) {
        AsyncHttpClient client = new AsyncHttpClient();
        //KEY AND HOST
        client.addHeader("X-RapidAPI-Key", "a46065425fmsh6bc3eb4e8517312p17619ejsn537ee3169b0d");
        client.addHeader("X-RapidAPI-Host", "shazam.p.rapidapi.com");

        //putting them toegther
        String url = "https://shazam.p.rapidapi.com/search?term=" + searchTerm + "&locale=en-US&offset=0&limit=1";

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject tracks = response.getJSONObject("tracks");
                    JSONArray hits = tracks.getJSONArray("hits");
                    if (hits.length() > 0) { //if "hits" or acutal songs are present then go on
                        JSONObject hit = hits.getJSONObject(0);//these are to get information from the array
                        JSONObject track = hit.getJSONObject("track");//to get track information
                        String artistName = track.getString("subtitle"); //"subtitle" or Artsts name
                        String songName = track.getString("title");// song namne
                        String coverImageUrl = track.getJSONObject("images").getString("coverart"); //from the images array, get only the cover Art URL

                        // Display image using Picasso
                        ImageView songImageView = findViewById(R.id.song_image_view);
                        Picasso.get().load(coverImageUrl).into(songImageView);
                        songImageView.setVisibility(View.VISIBLE);

                        //Display Song name
                        songInfoTextView.setText("Artist: " + artistName + "\nSong: " + songName);
                    } else {
                        songInfoTextView.setText("No song found");
                    }
                    //in case it went bad
                } catch (JSONException e) {
                    e.printStackTrace();
                    songInfoTextView.setText("Failed to get song details, but api working tho");
                }
            }

            //in case all hell is let loose
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                songInfoTextView.setText("Failed to get Api, it's just not your day...");
            }
        });
    }
}
