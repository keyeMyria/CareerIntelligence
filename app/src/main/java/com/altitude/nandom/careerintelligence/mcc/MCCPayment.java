package com.altitude.nandom.careerintelligence.mcc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.altitude.nandom.careerintelligence.MainActivity;
import com.altitude.nandom.careerintelligence.OffersRecyclerViewAdapter;
import com.altitude.nandom.careerintelligence.R;
import com.altitude.nandom.careerintelligence.classes.JavaScriptReceiver;
import com.altitude.nandom.careerintelligence.classes.SessionManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.paystack.android.PaystackSdk;

public class MCCPayment extends AppCompatActivity {

    public TextView tvTotal;
    private RecyclerView offerRecyclerView;
    public String myTotal;

    private Button bMCCPayment;

    // Session Manager Class
    SessionManager session;

    public String amount = "10";

    public MyDialogFragment frag = new MyDialogFragment();

    public static String email, jwt, name;

    private RequestQueue MyRequestQueue;

    private static ProgressDialog progressDialog;

    private static String nairaValue = "";

    String response = null;

    public static int nairaAmount;

    public static double nairaAmountdouble;

    public  static int finalAmount;

    RequestQueue ExampleRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mccpayment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bMCCPayment = (Button) findViewById(R.id.bMCCPayment);

        progressDialog = new ProgressDialog(this);

        ExampleRequestQueue = Volley.newRequestQueue(this);

        progressDialog.setMessage("Please Wait");

        progressDialog.show();

        getNairaAmount();

        Toast.makeText(MCCPayment.this, nairaValue, Toast.LENGTH_SHORT).show();


        // Initialize the volley request
        MyRequestQueue = Volley.newRequestQueue(this);

        // Session class instance
        session = new SessionManager(MCCPayment.this);

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // email
        email = user.get(SessionManager.KEY_EMAIL);
        // email
        jwt = user.get(SessionManager.KEY_JWT);

        offerRecyclerView = (RecyclerView) findViewById(R.id.payment_list);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        offerRecyclerView.setLayoutManager(recyclerLayoutManager);


        PaymentRecyclerViewAdapter recyclerViewAdapter = new
                PaymentRecyclerViewAdapter(getBrands(), this);
        offerRecyclerView.setAdapter(recyclerViewAdapter);

        bMCCPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvTotal.getText().toString() == "$10.00") {
                    amount = "10";
                } else if (tvTotal.getText().toString() == "$15.00") {
                    amount = "15";
                } else if (tvTotal.getText().toString() == "$40.00") {
                    amount = "40";
                }

//                Intent paymentIntent = new Intent(MCCPayment.this, PaymentWebview.class);
//                paymentIntent.putExtra("amount", amount);
//                paymentIntent.putExtra("email", email);
//                startActivity(paymentIntent);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                MyDialogFragment frag = new MyDialogFragment();
                Bundle args = new Bundle();
                double intAmount = Double.parseDouble(amount);
                args.putDouble("amount", intAmount);
                frag.setArguments(args);
                frag.show(ft, "txn_tag");


            }
        });

    }

    public void getNairaAmount() {
        String url = "http://apilayer.net/api/live?access_key=abbc7ae80a8e367c0ed80743b0cd73d4&currencies=NGN&format=1";
        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.

                try {
                    JSONObject reader = null;
                    reader = new JSONObject(response);
                    JSONObject sys = reader.getJSONObject("quotes");
                    nairaValue = sys.getString("USDNGN");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MCCPayment.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        nairaAmountdouble = Double.parseDouble(nairaValue);
                        nairaAmount = (int) Math.ceil(nairaAmountdouble);
                        finalAmount = nairaAmount * 10;
                        tvTotal.setText("$10.00 (N" + finalAmount  + ")");
                    }
                });

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("Field", "Value"); //Add the data you'd like to send to the server.
                return MyData;
            }
        };

        ExampleRequestQueue.add(ExampleStringRequest);

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

                        nairaAmountdouble = Double.parseDouble(nairaValue);
                        nairaAmount = (int) Math.ceil(nairaAmountdouble);

//                        Toast.makeText(context, nairaAmount + "", Toast.LENGTH_SHORT).show();

                        if (lastSelectedPosition == 0) {
                            finalAmount = nairaAmount * 10;
                            tvTotal.setText("$10.00 (N" + (finalAmount) + ")");
                        } else if (lastSelectedPosition == 1) {
                            finalAmount = nairaAmount * 15;
                            tvTotal.setText("$15.00  (N" + (finalAmount) + ")");
                        } else if (lastSelectedPosition == 2) {
                            finalAmount = nairaAmount * 40;
                            tvTotal.setText("$40.00  (N" + (finalAmount) + ")");
                        }
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
            d.setCancelable(false);
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


//            webView.addJavascriptInterface(new Object() {
//
//                @RequiresApi(api = Build.VERSION_CODES.M)
//                public void closeDialog() {
//                    Intent mIntent = new Intent(getContext(), MCCPayment.class);
//                    ComponentName component = new ComponentName(
//                            "com.altitude.nandom.careerintelligence.mcc;",
//                            "com.altitude.nandom.careerintelligence.mcc.MCCPayment");
//                    mIntent.setComponent(component);
//
//                    startActivity(mIntent);
////                    MyDialogFragment frag = new MyDialogFragment();
////                    frag.dismiss();
//                }
//            }, "paystack");

            webView.addJavascriptInterface(javaScriptReceiver, "JSReceiver");

            webView.loadUrl("file:///android_asset/paystack.html");

            webView.setWebViewClient(new WebViewClient() {
                String msgToSend = "paul4nank@gmail.com";
                //                String amount = MCCPayment.nairaAmount+"00";
                String another = (finalAmount * 100) + "";

                public void onPageFinished(WebView view, String url) {
                    webView.loadUrl("javascript:payWithPaystack(\"" + email + "\", \"" + another + "\")");
//                    webView.loadUrl("javascript:payWithPaystack()");
                }

            });
            return root;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
