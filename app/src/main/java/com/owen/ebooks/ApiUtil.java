package com.owen.ebooks;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.Key;
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
}
