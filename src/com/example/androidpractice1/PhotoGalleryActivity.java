package com.example.androidpractice1;

import android.support.v4.app.Fragment;


public class PhotoGalleryActivity extends SingleFragmentActivity {
	@Override
    public Fragment createFragment() {
        return new PhotoGalleryFragment(1);
    }
}
