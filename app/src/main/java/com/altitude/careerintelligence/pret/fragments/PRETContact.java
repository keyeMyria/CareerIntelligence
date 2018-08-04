package com.altitude.careerintelligence.pret.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.altitude.careerintelligence.R;
import com.altitude.careerintelligence.mcc.fragments.MCCFAQFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PRETContact extends Fragment {


    public static PRETContact newInstance() {
        // Required empty public constructor
        PRETContact fragment = new PRETContact();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pretcontact, container, false);
    }

}
