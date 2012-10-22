package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.myapp.DisplayMessageActivity;
// import com.example.myapp.MyFirstFragmentActivity;
import com.example.myapp.DynamicFragmentActivity;


public class MyFirstAppActivity extends Activity
{
    public final static String EXTRA_MESSAGE = "com.example.myapp.MESSAGE";
    public final static String TAG = "MyFirstAppActivity";

    private final static String KEY_MESSAGE  = "MESSAGE";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void sendMessage(View view) {
	Intent intent = new Intent(this, DisplayMessageActivity.class);
	EditText editText = (EditText)findViewById(R.id.edit_message);
	String message = editText.getText().toString();
	intent.putExtra(EXTRA_MESSAGE, message);
	startActivity(intent);
    }

    public void saveMessage(View view) {
	EditText editText = (EditText)findViewById(R.id.edit_message);
	String message = editText.getText().toString();

	SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
	SharedPreferences.Editor editor = sharedPref.edit();
	editor.putString(KEY_MESSAGE, message);
	editor.commit();
	Log.d(TAG, "saved: " + message);
    }

    public void getMessage(View view) {
	EditText editText = (EditText)findViewById(R.id.edit_message);
	SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
	String message = sharedPref.getString(KEY_MESSAGE, "default");
	editText.setText(message);
	Log.d(TAG, "get: " + message);
    }

    public void openFragmentView(View view) {
        Intent intent = new Intent(this, DynamicFragmentActivity.class);
	startActivity(intent);
    }
}
