package com.example.androidpractice1;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class PhotoGalleryFragment extends Fragment {
	private static final String TAG = "PhotoGalleryFragment";
	GridView mGridView;
	ArrayList<FlickrModel> mItems;
	
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
            mGridView.setAdapter(new ArrayAdapter<FlickrModel>(getActivity(),
                    android.R.layout.simple_gallery_item, mItems));
        } else {
            mGridView.setAdapter(null);
        }
    }
}
