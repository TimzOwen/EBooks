package com.owen.ebooks;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {

    //create an empty constructor;
    private ApiUtil(){}

    //create Constant that wont change using final keyword
    public static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes";

    //create a constant to make queries on URL
    public static final String QUERY_PARAMETER_KEY = "q";
    //create Keys for the APIG
    public static final String KEY = "key";
    public static final String API_KEY = "your google Manager APIs here // IT SHOULD BE SECRET";

    //create a URL builder function
    public static URL buildURL(String title){

        URL url = null;
        //create a URI to parse the url
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, title)
//                .appendQueryParameter(KEY,API_KEY)
                .build();
        try
        {
            //convert the URL request to a string
            url = new URL(uri.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return url;
    }
    //connect the URL to JSON
    public static String getJson(URL url) throws IOException
    {
        //establish  HTTP connection and open url connection
        HttpURLConnection connection = (HttpURLConnection)  url.openConnection();
        try
        {
            //create a scanner object and inputStream for conversion and read string files
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            //read everything using delimeter
            scanner.useDelimiter("\\A");

            //check if the scanner has data by creating a boolean for it.
            boolean hasData = scanner.hasNext();

            if (hasData)
            {
                return scanner.next();
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            Log.d("Error:", e.toString());
            return null;
        }
        finally {
            //disconnect the connection
            connection.disconnect();
        }


    }
    //method to get jsonData from the web
    public static ArrayList<Books> getBooksFromJson(String json)
    {
        //create the constant to hold the books description
        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "publishedDate";
        final String ITEMS = "items";
        final String VOLUMEINFO = "volumeInfo";

        //generate array list of books and start at 0;
        ArrayList<Books> books = new ArrayList<Books>();

        //use try catch to pass in hte json files
        try
        {
            //create JSON object from the string;
            JSONObject jsonBooks = new JSONObject(json);
            //get arrays with all the books
            JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);
            //calculate the numbers of successful retrieved books
            int numOfBooks = arrayBooks.length();
            //loop through and get the titles requirements
            for (int i=0; i<numOfBooks;i++)
            {
                //create another json array
                JSONObject booksJSON = arrayBooks.getJSONObject(i);
                JSONObject volumeInfoJSON = booksJSON.getJSONObject(VOLUMEINFO);
                //get the number of authors
                int authorsNumber = volumeInfoJSON.getJSONArray(AUTHORS).length();
                //create Srting s to store the number of authors
                String [] authors = new String[authorsNumber];
                //get the json authors names and pass them to the string value
                for (int j=0; j<authorsNumber; j++)
                {
                    authors[j] = volumeInfoJSON.getJSONArray(AUTHORS).get(j).toString();
                }
                //create a new book to fetch the data needed from all the books
                Books books1 = new Books(booksJSON.getString(ID),
                        volumeInfoJSON.getString(TITLE),
                        (volumeInfoJSON.isNull(SUBTITLE)?"":volumeInfoJSON.getString(SUBTITLE)),
                        authors,
                        volumeInfoJSON.getString(PUBLISHER),
                        volumeInfoJSON.getString(PUBLISHED_DATE));
                books.add(books1);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return books;
    }
}
