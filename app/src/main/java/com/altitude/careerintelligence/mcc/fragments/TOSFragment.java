package com.altitude.careerintelligence.mcc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.altitude.careerintelligence.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TOSFragment extends Fragment {


    public static TOSFragment newInstance() {
        // Required empty public constructor
        TOSFragment fragment = new TOSFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tos, container, false);
    }

}
