package com.altitude.careerintelligence;

/**
 * Created by Nandom on 5/11/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.altitude.careerintelligence.R;

import java.util.List;

public class OffersRecyclerViewAdapter extends
        RecyclerView.Adapter<OffersRecyclerViewAdapter.ViewHolder> {

    private List<OffersModel> offersList;
    private Context context;

    private int lastSelectedPosition = -1;

    public OffersRecyclerViewAdapter(List<OffersModel> offersListIn
            , Context ctx) {
        offersList = offersListIn;
        context = ctx;
    }

    @Override
    public OffersRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model, parent, false);

        OffersRecyclerViewAdapter.ViewHolder viewHolder =
                new OffersRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OffersRecyclerViewAdapter.ViewHolder holder,
                                 int position) {
        OffersModel offersModel = offersList.get(position);
        holder.offerName.setText(offersModel.getOffer());
        holder.offerAmount.setText("" + offersModel.getSavings());

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
        holder.selectionState.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView offerName;
        public TextView offerAmount;
        public RadioButton selectionState;

        public ViewHolder(View view) {
            super(view);
            offerName = (TextView) view.findViewById(R.id.offer_name);
            offerAmount = (TextView) view.findViewById(R.id.offer_amount);
            selectionState = (RadioButton) view.findViewById(R.id.offer_select);

            selectionState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    Toast.makeText(OffersRecyclerViewAdapter.this.context,
                            "selected offer is " + offerName.getText(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}