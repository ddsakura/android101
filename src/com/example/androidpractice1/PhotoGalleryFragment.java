package com.example.androidpractice1;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

@SuppressLint("ValidFragment")
public class PhotoGalleryFragment extends Fragment {
	private static final String TAG = "PhotoGalleryFragment";
	private TCLruCache cache;
	
	GridView mGridView;
	ArrayList<FlickrModel> mItems;
	ThumbnailDownloader<ImageView> mThumbnailThread;
	
	public class TCLruCache extends LruCache<String, Bitmap> {

        public TCLruCache(int maxSize) {
            super(maxSize);
        }
    }
	
	private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<FlickrModel>> {
        @Override
        protected ArrayList<FlickrModel> doInBackground(Void... params) {
        	return new FlickrFetchr().fetchItems();
        }
        @Override
        protected void onPostExecute(ArrayList<FlickrModel> items) {
            mItems = items;
            setupAdapter();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        new FetchItemsTask().execute();
        

        mThumbnailThread = new ThumbnailDownloader<ImageView>(new Handler());
        mThumbnailThread.setListener(new ThumbnailDownloader.Listener<ImageView>() {
            public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail, String url) {
                if (isVisible()) {
                    imageView.setImageBitmap(thumbnail);
                    Log.i(TAG, "onThumbnailDownloaded" + url);
                    cache.put(url, thumbnail);
                }
            }
        });
        mThumbnailThread.start();
        mThumbnailThread.getLooper();
        Log.i(TAG, "Background thread started");
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailThread.clearQueue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);

        mGridView = (GridView)v.findViewById(R.id.gridView);
        
        setupAdapter();

        return v;
    }
    
    void setupAdapter() {
        if (getActivity() == null || mGridView == null) return;

        if (mItems != null) {
            //mGridView.setAdapter(new ArrayAdapter<FlickrModel>(getActivity(),
            //        android.R.layout.simple_gallery_item, mItems));
            mGridView.setAdapter(new GalleryItemAdapter(mItems));
        } else {
            mGridView.setAdapter(null);
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailThread.quit();
        Log.i(TAG, "Background thread destroyed");
    }
    
    
    public PhotoGalleryFragment(int memoryClass) {
		super();
		
		cache = new TCLruCache(memoryClass);
	}

	private class GalleryItemAdapter extends ArrayAdapter<FlickrModel> {
        public GalleryItemAdapter(ArrayList<FlickrModel> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.gallery_item, parent, false);
            }

            ImageView imageView = (ImageView)convertView
                    .findViewById(R.id.gallery_item_imageView);
            imageView.setImageResource(R.drawable.emptyd);
            FlickrModel item = getItem(position);
            
            /*Bitmap image = cache.get(item.getmUrl());
            if (image != null) {
            	Log.i(TAG, "cache position " + String.valueOf(position));
            	Log.i(TAG, "cache url " + item.getmUrl());
            	imageView.setImageBitmap(image);
            }
            else {*/
            	mThumbnailThread.queueThumbnail(imageView, item.getmUrl(), cache);
            //}
            

            return convertView;
        }
    }
}
