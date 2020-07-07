package com.owen.ebooks;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiUtil {

    //create an empty constructor;
    private ApiUtil(){};

    //create Constant that wont change using final keyword
    public static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes";

    //create a URL builder function
    public static URL buildURL(String title){
        String fullUrlbuilder = BASE_API_URL + "?=" + title;

        URL url = null;
        try
        {
            url = new URL(fullUrlbuilder);
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
            //create a scanner object and inputStream for conversion
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);

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
