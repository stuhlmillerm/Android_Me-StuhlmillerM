package com.example.android.android_me.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartsFragment extends Fragment {

    private SharedPreferences prefs;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARTTYPE = "partType";

    private String mPartType;
    private GridView partGrid;
    private List<Integer> imageList;

    public PartsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param partType Parameter for type of part .
     * @return A new instance of fragment PartsFragment.
     */
    public static PartsFragment newInstance(String partType) {
        PartsFragment fragment = new PartsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARTTYPE, partType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPartType = getArguments().getString(ARG_PARTTYPE);
        }
    }

    public void updateSelected(int resourceId) {
        // If we are not passed a specific ID, look up the saved value
        if (resourceId == 0) {
            resourceId = prefs.getInt(mPartType, 0);
        }
        if (resourceId > 0) {

            // Find this resource in our list and use it to update the selected item highlight
            MasterListAdapter listAdapter = (MasterListAdapter) partGrid.getAdapter();
            for (int i = 0; i < listAdapter.getCount(); i++) {
                if (resourceId == (int) listAdapter.getItem(i)) {
                    listAdapter.setSelectedPosition(i);
                    if (partGrid != null)
                        partGrid.smoothScrollToPosition(i);
                    break;
                }
            }
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_parts, container, false);

        partGrid = (GridView) rootView.findViewById(R.id.partsGrid);

        // Set up our image list for the part of the body this fragment controls
        if (mPartType.equals("head")) imageList = AndroidImageAssets.getHeads();
        else if (mPartType.equals("body")) imageList = AndroidImageAssets.getBodies();
        else if (mPartType.equals("leg")) imageList = AndroidImageAssets.getLegs();
        else imageList = AndroidImageAssets.getAll();
        partGrid.setAdapter(new MasterListAdapter(rootView.getContext(), imageList));

        // See if we have a saved / current value and update the selected item
        prefs = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        int currentSelected = prefs.getInt(mPartType, 0);
        if (currentSelected > 0) {
            updateSelected(currentSelected);
        }

        // Set up our event listener for clicks.
        partGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Save the selected image for rotation
                SharedPreferences.Editor edit = prefs.edit();
                edit.putInt(mPartType, imageList.get(position));
                edit.apply();

                // Update the selection highlight
                updateSelected(imageList.get(position));

                // See if we have the android me fragment active and update it.
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AndroidMeFragment frag = (AndroidMeFragment) fm.findFragmentByTag("android_me");
                if (frag != null) {
                    frag.updatePartImages();
                }

            }
        });

        return rootView;
    }
}