package com.example.myapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class MyFirstFragmentActivity extends FragmentActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_fragment_view);
    }
}