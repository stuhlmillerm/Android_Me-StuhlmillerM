package com.example.android.android_me.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.android_me.R;

public class AndroidMeParts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me_parts);

        FragmentManager fm = getSupportFragmentManager();

        // See if we've already done this (e.g. if rotating device)
        if (fm.findFragmentByTag("head") == null) {
            // Begin the transaction
            FragmentTransaction ft = fm.beginTransaction();
            // Add the new fragment to the container if it doesn't already exist.
            ft.add(R.id.head_container, PartsFragment.newInstance("head"), "head");
            ft.add(R.id.body_container, PartsFragment.newInstance("body"), "body");
            ft.add(R.id.leg_container, PartsFragment.newInstance("leg"), "leg");
            // Complete the changes added above
            ft.commit();
        }

    }
}