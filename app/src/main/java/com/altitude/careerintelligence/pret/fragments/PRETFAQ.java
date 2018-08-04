package com.altitude.careerintelligence.pret.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.altitude.careerintelligence.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PRETFAQ extends Fragment {


    public static PRETFAQ newInstance() {
        // Required empty public constructor
        PRETFAQ fragment = new PRETFAQ();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pretfaq, container, false);
    }

}
