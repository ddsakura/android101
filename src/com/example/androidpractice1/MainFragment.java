package com.example.androidpractice1;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {
	
	private Button mTrueButton;
    private Button mFalseButton;
    private Button mDatePicker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main, container, false);
		
        mTrueButton = (Button)v.findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Does nothing yet, but soon!
            	Intent i = new Intent(getActivity(), SecondActivity.class);
            	i.putExtra("PassValue", "ABC");
            	i.putExtra("PassValue2", "DEF");
                startActivity(i);
            	//startActivityForResult(i, 0);
            }
        });
        mFalseButton = (Button)v.findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
            	Intent i = new Intent(getActivity(), ViewPagerActivity.class);
            	i.putExtra("PassValue", "Pager1");
            	i.putExtra("PassValue2", "DEF");
                startActivity(i);
            	//startActivityForResult(i, 0);
				
			}
		});
        mDatePicker = (Button)v.findViewById(R.id.date_button);
        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	FragmentManager fm = getActivity().getSupportFragmentManager();
            	DatePickerFragment dialog = DatePickerFragment
                        .newInstance(new Date());
            	dialog.show(fm, "DIALOG");
            }
        });
		
        return v;
	}

}
