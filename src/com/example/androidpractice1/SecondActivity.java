package com.example.androidpractice1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;


public class SecondActivity extends FragmentActivity {

	private static final String TAG = "AndroidPractice1-Second";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		Log.d(TAG, "onCreate called");
		
		
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentSecondContainer);

        if (fragment == null) {
            fragment = new SecondFragment();
            fm.beginTransaction()
                .add(R.id.fragmentSecondContainer, fragment)
                .commit();
        }
		
		Bundle passValue = getIntent().getExtras();
		String newString= passValue.getString("PassValue2");
		Log.d(TAG, "passValue is " + newString);
		
		Intent data = new Intent();
        data.putExtra("backValue", 1234);
        setResult(RESULT_OK, data);
        
        Log.d(TAG, "backValue ok");
		
	}
	
    /*@Override
    protected Fragment createFragment() {
        return new SecondFragment();
    }*/
	
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "onDestroy called");
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d(TAG, "onPause called");
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onResume called");
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState called");
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "onStart called");
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(TAG, "onStop called");
	}

}
