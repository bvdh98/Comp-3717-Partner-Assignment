package com.example.harrison_wark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Results extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    // URL to get contacts JSON
    // https://newsapi.org/v2/everything?q=bitcoin&from=2020-1-28&sortBy=publishedAt&apiKey=a8584850a6134eb79c7c8c9d367faf5e
    // http://data.vncvr.ca/api/people
    private static String SERVICE_URL = "http://data.vncvr.ca/api/people";
    private ArrayList<Parser> resultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultList = new ArrayList<Parser>();
        lv = findViewById(R.id.resultsList);
        new GetContacts().execute();

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = null;

            // Making a request to url and getting response
            jsonStr = sh.makeServiceCall(SERVICE_URL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                Log.d(TAG, "Json: " + jsonStr);
                // this step is needed to wrap the JSON array inside
                // a JSON object that looks like this { "toons": . . . . }
                jsonStr = "{\"results\":" + jsonStr + "}";
                Gson gson = new Gson();
                BaseParser baseParser = gson.fromJson(jsonStr, BaseParser.class);
                resultList = baseParser.getResults();
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ResultsAdapter adapter = new ResultsAdapter(Results.this, resultList);

            // Attach the adapter to a ListView
            lv.setAdapter(adapter);
        }
    }

}
