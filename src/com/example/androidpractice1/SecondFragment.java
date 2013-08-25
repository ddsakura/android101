package com.example.androidpractice1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment {
	private static final String TAG_TITLE = "AndroidPractice1-SF-Title";
	private String pTitle;
	
	/*public SecondFragment(String title) {
		pTitle = title;
	}*/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG_TITLE, (String)getActivity().getIntent().getSerializableExtra("PassValue"));
		
		if ((boolean)getActivity().getIntent().getSerializableExtra("PassValue").equals("ABC")) {
			Log.d(TAG_TITLE, "OK");
			pTitle = (String)getActivity().getIntent().getSerializableExtra("PassValue");
		} else {
			pTitle = "Pager" + (String)getArguments().getSerializable("POS");
			
		}
		
		Log.d(TAG_TITLE, pTitle);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_second, container, false);

		TextView title = (TextView)v.findViewById(R.id.fragment_title);
		title.setText(pTitle);
		
		return v;
	}

}
