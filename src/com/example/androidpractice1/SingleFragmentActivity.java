package com.example.androidpractice1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public abstract class SingleFragmentActivity extends FragmentActivity {
	private static final String TAG = "AndroidPractice1-SingleFragmentActivity";
	
	protected abstract Fragment createFragment();

	@Override
	public void onCreate(Bundle savedInstanceState) {
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
		Log.d(TAG, "passValue2 is " + newString);
		
		Intent data = new Intent();
        data.putExtra("backValue", 1234);
        setResult(RESULT_OK, data);
        
        Log.d(TAG, "backValue ok");
	}

}
