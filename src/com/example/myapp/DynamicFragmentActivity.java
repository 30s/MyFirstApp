package com.example.myapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.example.myapp.fragments.MyFirstFragment;

public class DynamicFragmentActivity extends FragmentActivity {
    public static final String TAG = "DynamicFragmentActivity";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_fragment_activity);
    }

    public void addFragment(View view) {
	Log.d(TAG, "addFragment");
	
        if (findViewById(R.id.fragment_container) != null) {
            // if (savedInstanceState != null) {
            //     return;
            // }
	    if ( getSupportFragmentManager()
		 .findFragmentByTag("MyFirstFragment") != null) {
		Log.d(TAG, "MyFirstFragment already exist!");
		return;
	    }

            MyFirstFragment firstFragment = new MyFirstFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
		.beginTransaction()
		.add(R.id.fragment_container, firstFragment, "MyFirstFragment")
		.commit();
	}
    }

    public void replaceFragment(View view) {
	Log.d(TAG, "replaceFragment");

	MyFirstFragment newFragment = new MyFirstFragment();
	FragmentTransaction transaction = getSupportFragmentManager()
	    .beginTransaction();

	transaction.replace(R.id.fragment_container, newFragment, 
			    "MyFirstFragment");
	transaction.addToBackStack(null);

	transaction.commit();
    }
}