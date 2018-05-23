package com.altitude.nandom.careerintelligence.mcc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.altitude.nandom.careerintelligence.mcc.MCCPaymentModel;
import com.altitude.nandom.careerintelligence.R;

import java.util.List;

/**
 * Created by Nandom on 5/11/2018.
 */


public class PaymentRecyclerViewAdapter extends
        RecyclerView.Adapter<com.altitude.nandom.careerintelligence.mcc.PaymentRecyclerViewAdapter.ViewHolder> {

    private List<MCCPaymentModel> offersList;
    private Context context;

    private int lastSelectedPosition = -1;

    public PaymentRecyclerViewAdapter(List<MCCPaymentModel> offersListIn
            , Context ctx) {
        offersList = offersListIn;
        this.context = ctx;
    }

    @Override
    public com.altitude.nandom.careerintelligence.mcc.PaymentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                                          int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_model, parent, false);

        com.altitude.nandom.careerintelligence.mcc.PaymentRecyclerViewAdapter.ViewHolder viewHolder =
                new com.altitude.nandom.careerintelligence.mcc.PaymentRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(com.altitude.nandom.careerintelligence.mcc.PaymentRecyclerViewAdapter.ViewHolder holder,
                                 int position) {
        MCCPaymentModel offersModel = offersList.get(position);
        holder.tvTitlePayment.setText(offersModel.getMccTitle());
        holder.tvAmount.setText("$" + offersModel.getMccAmount());
        holder.tvStatus.setText(offersModel.getMccStatus());
        holder.tvAuthor.setText(offersModel.getMccAuthor());

        if(offersModel.getMccStatus().contentEquals("Optional"))
            holder.tvStatus.setTextColor(R.color.optionalColor);

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
        holder.selectionState.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public void Update(int amount){
        TextView txtView = (TextView) ((Activity)context).findViewById(R.id.text);
        txtView.setText(""+ amount);
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
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    if(lastSelectedPosition == 0)
                        Update(10);
                    else if(lastSelectedPosition == 1)
                        Update(15);
                    else
                        Update(40);
                }
            });
        }
    }
}
