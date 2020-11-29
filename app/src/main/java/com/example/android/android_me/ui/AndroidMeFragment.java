package com.example.android.android_me.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AndroidMeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AndroidMeFragment extends Fragment {

    private SharedPreferences prefs;
    private View rootView;

    public AndroidMeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AndroidMeFragment.
     */
    public static AndroidMeFragment newInstance(String param1, String param2) {
        AndroidMeFragment fragment = new AndroidMeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // update the image for a body part based on saved value
    public void setPartImage(int viewId, String partType) {
        int savedId = prefs.getInt(partType, -1);
        if (savedId > 0) {
            ImageView imgView = (ImageView) rootView.findViewById(viewId);
            imgView.setImageResource(savedId);
        }
    }

    // Update the images in our android me from saved values
    public void updatePartImages() {
        if (rootView != null) {
            setPartImage(R.id.head_image, "head");
            setPartImage(R.id.body_image, "body");
            setPartImage(R.id.leg_image, "leg");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_android_me, container, false);

        prefs = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        // Use our saved values to show the android me images.
        updatePartImages();

        // Set up the click listener for the randomize button
        Button randButton = (Button) rootView.findViewById(R.id.randomizeButton);
        randButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = prefs.edit();
                Random rand = new Random();
                // Use the random object to pull out random images from our three body parts
                List<Integer> imgList = AndroidImageAssets.getHeads();
                edit.putInt("head", imgList.get(rand.nextInt(imgList.size())));
                imgList = AndroidImageAssets.getBodies();
                edit.putInt("body", imgList.get(rand.nextInt(imgList.size())));
                imgList = AndroidImageAssets.getLegs();
                edit.putInt("leg", imgList.get(rand.nextInt(imgList.size())));
                edit.apply();

                // If our body part fragments are active, update them with the new selected values
                FragmentManager fm = getFragmentManager();
                PartsFragment headFragment = (PartsFragment) fm.findFragmentByTag("head");
                if (headFragment != null)
                    headFragment.updateSelected(0);
                PartsFragment bodyFragment = (PartsFragment) fm.findFragmentByTag("body");
                if (bodyFragment != null)
                    bodyFragment.updateSelected(0);
                PartsFragment legFragment = (PartsFragment) fm.findFragmentByTag("leg");
                if (legFragment != null)
                    legFragment.updateSelected(0);

                updatePartImages();
            }
        });

        return rootView;
    }
}