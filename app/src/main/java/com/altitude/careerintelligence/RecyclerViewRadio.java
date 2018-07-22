package com.altitude.careerintelligence;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.altitude.careerintelligence.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewRadio extends AppCompatActivity {
    private RecyclerView offerRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_radio);

        offerRecyclerView = (RecyclerView) findViewById(R.id.offers_lst);

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        offerRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(offerRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        offerRecyclerView.addItemDecoration(dividerItemDecoration);


        OffersRecyclerViewAdapter recyclerViewAdapter = new
                OffersRecyclerViewAdapter(getBrands(),this);
        offerRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private List<OffersModel> getBrands(){
        List<OffersModel> modelList = new ArrayList<OffersModel>();
        modelList.add(new OffersModel(300, "Get Upto 20% Off Clothing"));
        modelList.add(new OffersModel(200, "Free Smart Phone"));
        modelList.add(new OffersModel(600, "Pay $100 get big HD TV"));
        modelList.add(new OffersModel(500, "Get Upto 40% Off All"));
        modelList.add(new OffersModel(100, "Buy One Get One Free"));
        modelList.add(new OffersModel(600, "Pay $200 get Laptop"));
        modelList.add(new OffersModel(300, "Get Upto 50% Off Electronics"));
        modelList.add(new OffersModel(400, "Free Movie Ticket"));
        modelList.add(new OffersModel(700, "Pay $100 Travel Europe"));
        modelList.add(new OffersModel( 600, "Get Upto 27% Off Appliance"));
        modelList.add(new OffersModel( 700, "Get Upto 44% Off Jewellery"));
        modelList.add(new OffersModel(500, "Free Coupons"));
        modelList.add(new OffersModel(600, "Pay $100 get Tablet"));
        return modelList;
    }
}