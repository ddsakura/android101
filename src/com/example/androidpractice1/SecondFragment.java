package com.example.androidpractice1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment {
	private String pTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pTitle = (String)getActivity().getIntent().getSerializableExtra("PassValue");
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
