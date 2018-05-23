package com.altitude.nandom.careerintelligence.mcc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.altitude.nandom.careerintelligence.OffersRecyclerViewAdapter;
import com.altitude.nandom.careerintelligence.R;

import java.util.ArrayList;
import java.util.List;

public class MCCPayment extends AppCompatActivity {


    public TextView tvTotal;
    private RecyclerView offerRecyclerView;
    public String myTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mccpayment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        offerRecyclerView = (RecyclerView) findViewById(R.id.payment_list);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        offerRecyclerView.setLayoutManager(recyclerLayoutManager);


        PaymentRecyclerViewAdapter recyclerViewAdapter = new
                PaymentRecyclerViewAdapter(getBrands(), this);
        offerRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private List<MCCPaymentModel> getBrands() {
        List<MCCPaymentModel> paymentList = new ArrayList<MCCPaymentModel>();
        paymentList.add(new MCCPaymentModel("MCC Test Code", "Career Intelligence", "In Stock",
                10.00));
        paymentList.add(new MCCPaymentModel("MCC Test with 1hr Phone call", "Career Intelligence", "Optional",
                15.00));
        paymentList.add(new MCCPaymentModel("MCC Test Code with 1hr Video Call", "Career Intelligence", "Optional",
                40.00));

        return paymentList;
    }


    public class PaymentRecyclerViewAdapter extends
            RecyclerView.Adapter<PaymentRecyclerViewAdapter.ViewHolder> {

        private List<MCCPaymentModel> offersList;
        private Context context;

        private int lastSelectedPosition = 0;

        public PaymentRecyclerViewAdapter(List<MCCPaymentModel> offersListIn
                , Context ctx) {
            offersList = offersListIn;
            this.context = ctx;
        }

        @Override
        public PaymentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.payment_model, parent, false);

            PaymentRecyclerViewAdapter.ViewHolder viewHolder =
                    new PaymentRecyclerViewAdapter.ViewHolder(view);
            return viewHolder;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MCCPaymentModel offersModel = offersList.get(position);
            holder.tvTitlePayment.setText(offersModel.getMccTitle());
            holder.tvAmount.setText("$" + offersModel.getMccAmount());
            holder.tvStatus.setText(offersModel.getMccStatus());
            holder.tvAuthor.setText(offersModel.getMccAuthor());


            if (offersModel.getMccStatus() == "Optional") {
                holder.tvStatus.setTextColor(R.color.optionalColor);
            }

            //since only one radio button is allowed to be selected,
            // this condition un-checks previous selections
            holder.selectionState.setChecked(lastSelectedPosition == position);
        }


        @Override
        public int getItemCount() {
            return offersList.size();
        }

        public void Update(int amount) {
            TextView txtView = (TextView) ((Activity) context).findViewById(R.id.text);
            txtView.setText("" + amount);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tvTitlePayment;
            public TextView tvAmount;
            public TextView tvStatus;
            public TextView tvAuthor;

            public RadioButton selectionState;

            public ViewHolder(View view) {
                super(view);
                tvTitlePayment = (TextView) view.findViewById(R.id.tvTitlePayment);
                tvAmount = (TextView) view.findViewById(R.id.tvAmount);
                tvStatus = (TextView) view.findViewById(R.id.tvStatus);
                tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);

                selectionState = (RadioButton) view.findViewById(R.id.rbPaymentOption);

                selectionState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MCCPaymentModel paymentModel = new MCCPaymentModel();
                        lastSelectedPosition = getAdapterPosition();
                        notifyDataSetChanged();
                        tvTotal.setText(myTotal);

                        if (lastSelectedPosition == 0)
                            tvTotal.setText("$10.00");
                        if (lastSelectedPosition == 1)
                            tvTotal.setText("$15.00");
                        if (lastSelectedPosition == 2)
                            tvTotal.setText("$40.00");
                    }
                });
            }
        }
    }

}
