package com.owen.ebooks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class BooksListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        try {
            //creat a URL
            URL bookUrl = ApiUtil.buildURL("cooking");
            //call the executeMethod
            new BooksQueryTask().execute(bookUrl);
             }
        catch (Exception e)
        {
            Log.d("error", e.getMessage());
        }
    }
    //Create a query AsycTask class
    public class BooksQueryTask extends AsyncTask<URL, Void, String>
    {
        //implement do in background function
        @Override
        protected String doInBackground(URL... urls) {
            //urls are URLs arrays so accessing an an object you have to call the array
            URL searchUrl = urls[0]; //getting the first URL;
            //store the result in a string;
            String result = null; //set to 0 at start
            //use try catch to get the first element
            try {
                result = ApiUtil.getJson(searchUrl);
            }
            catch (IOException e)
            {
                Log.e("Error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String results) {
            //find the textView
            TextView tvResults = findViewById(R.id.tvResponse);
            //set the textView to the results
            tvResults.setText(results);
        }
    }
}