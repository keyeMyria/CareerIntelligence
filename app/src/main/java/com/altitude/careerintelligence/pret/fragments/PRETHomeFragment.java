package com.altitude.careerintelligence.pret.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.altitude.careerintelligence.R;
import com.altitude.careerintelligence.mcc.MCCPaymentModel;
import com.altitude.careerintelligence.pret.classes.AnimationItem;
import com.altitude.careerintelligence.pret.classes.CardAdapter;
import com.altitude.careerintelligence.pret.classes.ItemOffsetDecoration;
import com.altitude.careerintelligence.pret.classes.PretModel;

import java.util.ArrayList;
import java.util.List;

public class PRETHomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private AnimationItem mSelectedItem;
    private AnimationItem[] mAnimationItems;

    private List<PretModel> pretMenu;

    private AnimationItem[] getAnimationItems() {
        return new AnimationItem[] {
                new AnimationItem("Slide from bottom", R.anim.layout_animation_from_bottom)
        };
    }


    public static PRETHomeFragment newInstance() {
        // Required empty public constructor
        PRETHomeFragment fragment = new PRETHomeFragment();
        return fragment;
    }

    protected RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prethome, container, false);


        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAnimationItems = getAnimationItems();

        mSelectedItem = mAnimationItems[0];

        mRecyclerView = (RecyclerView) view.findViewById(R.id.pret_recycler_view);

        setupRecyclerView();
        runLayoutAnimation(mRecyclerView, mSelectedItem);

        getLayoutManager(getContext());

    }

    private void runLayoutAnimation(final RecyclerView recyclerView, final AnimationItem item) {
        final Context context = recyclerView.getContext();

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, item.getResourceId());

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private void setupRecyclerView() {
        final Context context = mRecyclerView.getContext();
        final int spacing = getResources().getDimensionPixelOffset(R.dimen.default_spacing_small);
        mRecyclerView.setLayoutManager(getLayoutManager(context));
        CardAdapter cardAdapter = new CardAdapter(getPretMenu(), getContext());
        mRecyclerView.setAdapter(cardAdapter);
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(spacing));
    }

    private List<PretModel> getPretMenu(){

        List<PretModel> pretMenu = new ArrayList<PretModel>();

        pretMenu.add(new PretModel("Make Payment", "Pay for PRET test Online", R.drawable.online_payment_pret));
        pretMenu.add(new PretModel("Schedule Test", "Select a Test Date, Time & Venue", R.drawable.schedule));
        pretMenu.add(new PretModel("Upload CV", "Upload your CV online", R.drawable.upload_image));
        pretMenu.add(new PretModel("Receipts", "View receipts of all payments", R.drawable.receipt_main));
        pretMenu.add(new PretModel("Help", "Get Support from our team", R.drawable.help));

        return pretMenu;
    }
}