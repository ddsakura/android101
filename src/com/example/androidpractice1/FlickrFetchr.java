package com.example.androidpractice1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

public class FlickrFetchr {
	public static final String TAG = "FlickrFetchr";

    private static final String ENDPOINT = "http://api.flickr.com/services/rest/";
    private static final String API_KEY = "84921e87fb8f2fc338c3ff9bf51a412e";
    private static final String METHOD_GET_RECENT = "flickr.photos.getRecent";
    private static final String PARAM_EXTRAS = "extras";

    private static final String EXTRA_SMALL_URL = "url_s";
    private static final String JSON_PHOTO = "photo";
    
	byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
 
    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
    
    public ArrayList<FlickrModel> fetchItems() {
    	ArrayList<FlickrModel> items = new ArrayList<FlickrModel>();
    	
        try {
            String url = Uri.parse(ENDPOINT).buildUpon()
                    .appendQueryParameter("method", METHOD_GET_RECENT)
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter(PARAM_EXTRAS, EXTRA_SMALL_URL)
                    .appendQueryParameter("format", "json")
                    .build().toString();
            String xmlString = getUrl(url);
            // don't why flickr need to use this fixed trick
            String fixed = xmlString.replaceFirst("jsonFlickrApi\\(", "");
            Log.i(TAG, "Received Json: " + fixed);
            //readJsonStream("{\"username\":\"Bob\", \"age\": 14}");
            items = readJsonStream(fixed);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
		return items;
    }
    
    public ArrayList<FlickrModel> readJsonStream(String in) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(in));
        Log.i(TAG, "Received readJsonStream");
        try {
          return readMessagesArray(reader);
        }
         finally {
          reader.close();
        }
    }

	public ArrayList<FlickrModel> readMessagesArray(JsonReader reader) throws IOException {
		ArrayList<FlickrModel> items = new ArrayList<FlickrModel>();
		Log.i(TAG, "Received readMessagesArray");
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("photos")) {
				//String username = reader.nextString();
				Log.i(TAG, "Received photos: ");
				reader.beginObject();
				while (reader.hasNext()) {
					String name2 = reader.nextName();
					if (name2.equals("photo")) {
						Log.i(TAG, "Received photo: ");
						reader.beginArray();
						while (reader.hasNext()) {
							items.add(readMessage(reader));
							
						}
						reader.endArray();
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
			} else {
				reader.skipValue();
			}
			//
		}
		reader.endObject();
		return items;
	}

	public FlickrModel readMessage(JsonReader reader) throws IOException {
		String id = null;
        String caption = null;
        String smallUrl = null;
        Log.i(TAG, "Received readMessage");
	     reader.beginObject();
	     while (reader.hasNext()) {
	       String name = reader.nextName();
	       if (name.equals("id")) {
	    	   id = reader.nextString();
	       } else if (name.equals("title")) {
	    	   caption = reader.nextString();
	       } else if (name.equals(EXTRA_SMALL_URL)) {
	    	   smallUrl = reader.nextString();
	       } else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     
	        FlickrModel item = new FlickrModel();
	        item.setmId(id);
	        item.setmCaption(caption);
	        item.setmUrl(smallUrl);
	     return item;
	   }

    
    
}
