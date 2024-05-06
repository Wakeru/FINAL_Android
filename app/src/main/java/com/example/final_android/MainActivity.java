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

    private TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jokeTextView = findViewById(R.id.joke_text_view);
        Button generateJokeButton = findViewById(R.id.generate_joke_button);

        generateJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchChuckNorrisJoke();
            }
        });

        fetchChuckNorrisJoke(); // Fetch a joke when the activity starts
    }

    private void fetchChuckNorrisJoke() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.chucknorris.io/jokes/random", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String joke = response.getString("value");
                    jokeTextView.setText(joke);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                jokeTextView.setText("Failed to fetch Chuck Norris joke");
            }
        });
    }
}