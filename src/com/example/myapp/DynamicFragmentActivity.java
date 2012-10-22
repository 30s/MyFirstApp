package com.example.myapp;

import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.example.myapp.fragments.MyFirstFragment;

public class DynamicFragmentActivity extends FragmentActivity 
    implements MyFirstFragment.OnHeadlineSelectedListener {
    
    public static final String TAG = "DynamicFragmentActivity";
    private static final int PICK_CONTACT_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_fragment_activity);

	Intent intent = getIntent();
	Uri data = intent.getData();
	if ( data != null ) {
	    Log.d(TAG, data.toString());
	} else {
	    Log.d(TAG, "send data is null!");
	}
	if (intent.getType().indexOf("image/") != -1) {
	    
	} else if (intent.getType().equals("text/plain")) {
	    
	}
    }

    public void addFragment(View view) {
	Log.d(TAG, "addFragment");
	
        if (findViewById(R.id.fragment_container) != null) {
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

    public void openMap(View view) {
	// z param is zoom level
	Uri location = Uri.parse("geo:37.422219,-122.08364?z=14");
	Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

	PackageManager packageManager = getPackageManager();
	List<ResolveInfo> activities = packageManager
	    .queryIntentActivities(mapIntent, 0);	
	boolean isIntentSafe = activities.size() > 0;

	Log.d(TAG, "Map app: " + activities.size() + "");
	if (isIntentSafe) {
	    String title = (String)getResources().getText(R.string.chooser_title);
	    Intent chooser = Intent.createChooser(mapIntent, title);
	    startActivity(chooser);
	} else {
	    Log.d(TAG, "No map app!");
	}
    }

    public void getContact(View view) {
	Intent pickContactIntent = new Intent(Intent.ACTION_PICK, 
					      Uri.parse("content://contacts"));
	pickContactIntent.setType(Phone.CONTENT_TYPE);
	startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, 
				    int resultCode, 
				    Intent data) {
	if (requestCode == PICK_CONTACT_REQUEST) {
	    if (resultCode == RESULT_OK) {
		Uri contactUri = data.getData();
		if ( contactUri == null ) {
		    Log.d(TAG, "data is null!");
		    return;
		}
		String[] projection = { Phone.NUMBER };
		Cursor cursor = getContentResolver()
                    .query(contactUri, projection, null, null, null);
		cursor.moveToFirst();

		int column = cursor.getColumnIndex(Phone.NUMBER);
		String number = cursor.getString(column);
		Log.d(TAG, "Phone: " + number);
	    } else {
		Log.d(TAG, "canceled!");
	    }
	}
    }

    public void onArticleSelected(String title) {
	Log.d(TAG, title);
    }

    /**
     * This is for MyFirstFragment
     */
    public void callActivity(View view) { 
	Log.d(TAG, "callActivity in DynamicFragmentActivity");
	MyFirstFragment fragment = (MyFirstFragment)getSupportFragmentManager()
	    .findFragmentByTag("MyFirstFragment");
	if ( fragment != null) {
	    fragment.callActivity(view);
	}	
    }
}