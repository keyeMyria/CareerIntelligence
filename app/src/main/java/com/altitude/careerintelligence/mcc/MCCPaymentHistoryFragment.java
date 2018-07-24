package com.altitude.careerintelligence.mcc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.altitude.careerintelligence.R;

import java.util.ArrayList;
import java.util.List;


public class MCCPaymentHistoryFragment extends Fragment {

    private RecyclerView recyclerViewPaymentHistory;
    private PaymentHistoryAdapter paymentHistoryAdapter;
    private List<PaymentHistoryModel> paymentHistoryModel;

    private LinearLayout llNoRecord;

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
        View view = inflater.inflate(R.layout.fragment_mccpayment_history, container, false);

        Bundle args = getArguments();
        String[] myPaymentLists = args.getStringArray("myPaymentList");


        recyclerViewPaymentHistory = (RecyclerView) view.findViewById(R.id.payment_history_recycler_view);
        llNoRecord = (LinearLayout) view.findViewById(R.id.llNoReCord);

        paymentHistoryModel = new ArrayList<>();

        recyclerViewPaymentHistory.setHasFixedSize(true);
        recyclerViewPaymentHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPaymentHistory.setItemAnimator(new DefaultItemAnimator());


        if(myPaymentLists.length >=  1) {

            for (int i = 0; i < myPaymentLists.length; i++) {

//            PaymentHistoryModel payments = new PaymentHistoryModel();
//
//            payments.setOrderTitle("MCC Test Only");
//            payments.setPaymentDay("12");
//            payments.setPaymentDate("16th November, 2018");
//            payments.setPaymentMonth("November");
//            payments.setPaymentAmount("₦12,000");
//            payments.setReferenceCode(myPaymentLists[i]);
//
//            paymentHistoryModel.add(payments);

                paymentHistoryModel.add(new PaymentHistoryModel("MCC Test Only", "Successful", "17th Nov. 2018", "17", "November", myPaymentLists[i], "₦12,000"));

            }
        }else{

            recyclerViewPaymentHistory.setVisibility(View.GONE);
            llNoRecord.setVisibility(View.VISIBLE);



        }

//        paymentHistoryAdapter.notifyDataSetChanged();



//        paymentHistoryModel.add(new PaymentHistoryModel("MCC Test Only", "Successful", "17th Nov. 2018", "17", "November", "689684", "₦15,000"));
//        paymentHistoryModel.add(new PaymentHistoryModel("MCC Test Only", "Failed", "30th Jul. 2018", "30", "July", "32434454", "₦12,000"));

        paymentHistoryAdapter = new PaymentHistoryAdapter(getContext(), paymentHistoryModel);
        recyclerViewPaymentHistory.setAdapter(paymentHistoryAdapter);

        return view;
    }

}