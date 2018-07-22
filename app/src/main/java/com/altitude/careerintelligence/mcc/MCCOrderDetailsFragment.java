package com.altitude.careerintelligence.mcc;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.altitude.careerintelligence.R;
import com.altitude.careerintelligence.classes.ConnectivityReceiver;
import com.altitude.careerintelligence.classes.JavaScriptReceiver;
import com.altitude.careerintelligence.classes.SessionManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MCCOrderDetailsFragment extends Fragment {

    public TextView tvTotal;
    private RecyclerView offerRecyclerView;
    public String myTotal;

    private Button bMCCPayment;

    public static double nairaAmountdouble;

    private static String nairaValue = "";

    public String amount = "10";

//    public MCCPayment.MyDialogFragment frag = new MCCPayment.MyDialogFragment();

    public static String email, jwt, name;

    public static int nairaAmount;
    public static int finalAmount;
    private double testAmount;

    private TextView tvOrderType;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    private LinearLayoutManager recyclerLayoutManager;

    // Session Manager Class
    SessionManager session;



    public MCCOrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mccorder_detials, container, false);

        bMCCPayment = (Button)view.findViewById(R.id.bMCCPayment);

        tvOrderType = (TextView)view.findViewById(R.id.tvOrderType);

        offerRecyclerView = (RecyclerView) view.findViewById(R.id.payment_list);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);

        Bundle args = getArguments();
        double testAmount = args.getDouble("testAmount");

        String amount = testAmount+"";

        // Session class instance
        session = new SessionManager(getActivity());

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // email
        email = user.get(SessionManager.KEY_EMAIL);

        // token
//        token = user.get(SessionManager.KEY_JWT);


        recyclerLayoutManager = new LinearLayoutManager(getActivity());
        offerRecyclerView.setLayoutManager(recyclerLayoutManager);

        PaymentRecyclerViewAdapter recyclerViewAdapter = new
                PaymentRecyclerViewAdapter(getBrands(testAmount), getContext());
        offerRecyclerView.setAdapter(recyclerViewAdapter);



        DecimalFormat df = new DecimalFormat("#,###.00");
        nairaAmount = (int) Math.ceil(testAmount);
        finalAmount = nairaAmount * 1;
        tvTotal.setText("₦" + df.format(nairaAmount));

        bMCCPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkConnection()) {
                    Toast.makeText(getActivity(), "Please Check connection and try again", Toast.LENGTH_SHORT).show();
                } else {
//                    if (tvTotal.getText().toString() == "$10.00") {
//                        amount = "10";
//                    } else if (tvTotal.getText().toString() == "$15.00") {
//                        amount = "15";
//                    } else if (tvTotal.getText().toString() == "$40.00") {
//                        amount = "40";
//                    }


//                Intent paymentIntent = new Intent(MCCPayment.this, PaymentWebview.class);
//                paymentIntent.putExtra("amount", amount);
//                paymentIntent.putExtra("email", email);
//                startActivity(paymentIntent);

                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    MyDialogFragment frag = new MyDialogFragment();
                    Bundle args = new Bundle();
                    double intAmount = Double.parseDouble(testAmount+"");
                    args.putDouble("amount", intAmount);
                    frag.setArguments(args);
                    frag.show(ft, "txn_tag");
                }

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    // Method to manually check connection status
    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        return isConnected;

    }

    private List<MCCPaymentModel> getBrands(double something) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        List<MCCPaymentModel> paymentList = new ArrayList<MCCPaymentModel>();
        paymentList.add(new MCCPaymentModel("MCC Test Code only", "Career Intelligence", "In Stock",
                "₦"+df.format(something)));
//        paymentList.add(new MCCPaymentModel("MCC Test with 1hr Phone call", "Career Intelligence", "Optional",
//                15.00));
//        paymentList.add(new MCCPaymentModel("MCC Test Code with 1hr Video Call", "Career Intelligence", "Optional",
//                nairaAmount * 40.00));

        return paymentList;
    }

    public class PaymentRecyclerViewAdapter extends
            RecyclerView.Adapter<PaymentRecyclerViewAdapter.ViewHolder> {

        private List<MCCPaymentModel> offersList;
        private Context context;

        public int lastSelectedPosition;

        PaymentRecyclerViewAdapter() {

        }

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
            holder.tvAmount.setText(offersModel.getMccAmount()+"");
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

                        nairaAmountdouble = Double.parseDouble(nairaValue);
                        nairaAmount = (int) Math.ceil(nairaAmountdouble);

//                        Toast.makeText(context, nairaAmount + "", Toast.LENGTH_SHORT).show();

                        if (lastSelectedPosition == 0) {
                            finalAmount = nairaAmount * 10;
                            tvTotal.setText("N" + finalAmount);
                            tvOrderType.setText("MCC Test Code only");
                        }
//                        } else if (lastSelectedPosition == 1) {
//                            finalAmount = nairaAmount * 15;
//                            tvTotal.setText("N" + (finalAmount));
//                            tvOrderType.setText("MCC Test Code with 1hr Voice Call");
//                        } else if (lastSelectedPosition == 2) {
//                            finalAmount = nairaAmount * 40;
//                            tvTotal.setText("N" + (finalAmount));
//                            tvOrderType.setText("MCC Test Code with 1hr Video Call");
//                        }
                    }
                });
            }
        }
    }

    static public class MyDialogFragment extends DialogFragment {

        private WebView webView;
        private Dialog d = getDialog();

        public static SessionManager sessionManager;
        JavaScriptReceiver javaScriptReceiver;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public void onStart() {
            super.onStart();
            d = getDialog();
//            d.setCancelable(false);
            if (d != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @SuppressLint("JavascriptInterface")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.my_fragment, container, false);

            webView = (WebView) root.findViewById(R.id.web_view);

            Bundle args = getArguments();
            double amount = args.getDouble("amount", 0);

//            mymcc.isBackAllowed = false;

            javaScriptReceiver = new JavaScriptReceiver(getActivity());

            webView.clearCache(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.clearHistory();
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


            webView.addJavascriptInterface(new Object() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                public void closeDialog() {
                    Intent mIntent = new Intent(getContext(), MCCPayment.class);
                    ComponentName component = new ComponentName(
                            "com.altitude.careerintelligence.mcc;",
                            "MCCPayment");
                    mIntent.setComponent(component);

                    startActivity(mIntent);
//                    MyDialogFragment frag = new MyDialogFragment();
//                    frag.dismiss();
                }
            }, "paystack");

            webView.addJavascriptInterface(javaScriptReceiver, "JSReceiver");

            webView.loadUrl("file:///android_asset/paystack.html");

            webView.setWebViewClient(new WebViewClient() {
                String msgToSend = "paul4nank@gmail.com";
                //                String amount = MCCPayment.nairaAmount+"00";
                String another = (finalAmount * 100) + "";

                public void onPageFinished(WebView view, String url) {
                    webView.loadUrl("javascript:payWithPaystack(\"" + email + "\", \""+ another +"\")");
//                    webView.loadUrl("javascript:payWithPaystack()");
                }

            });
            return root;
        }
    }




}
