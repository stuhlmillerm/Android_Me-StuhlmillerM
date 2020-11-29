/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.android_me.R;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
//https://www.youtube.com/watch?v=r6f7B7diM9w

public class AndroidMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        View selectButton = (Button) findViewById(R.id.selectButton);
        if (selectButton != null) {
            // If we have a button for selecting, we're in landscape mode - add a handler to navigate
            selectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Go to the parts selection activity.
                    Intent myIntent = new Intent(AndroidMeActivity.this, AndroidMeParts.class);
                    startActivity(myIntent);
                }
            });

            // And stop here - no need to do the fragments unless we're in landscape.
            return;
        }
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
