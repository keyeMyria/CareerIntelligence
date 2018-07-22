package com.altitude.careerintelligence.mcc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.altitude.careerintelligence.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MCCPaymentHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */

public class MCCPaymentHistoryFragment extends Fragment{

        public MCCPaymentHistoryFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_mccpayment_history, container, false);
        }

    }