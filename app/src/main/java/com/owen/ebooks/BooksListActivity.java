package com.owen.ebooks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BooksListActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        //find the id related to the progress bar
        mProgressBar = findViewById(R.id.pb_loading);

        try {
            //create a URL
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
            TextView tvError = findViewById(R.id.tv_errorLoading);
            //set the visibility of the PB to invisble
            mProgressBar.setVisibility(View.INVISIBLE);
            //use else if to check for results
            if (results==null)
            {
                tvResults.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            }
            else
            {
                tvError.setVisibility(View.INVISIBLE);
                tvResults.setVisibility(View.VISIBLE);
            }
            ArrayList<Books> book = ApiUtil.getBooksFromJson(results);

            String resultString = "";

            for (Books books: book)
            {
                resultString  = resultString + book1.title
            }

            //set the textView to the results
            tvResults.setText(results);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}