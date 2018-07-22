package com.altitude.careerintelligence.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.altitude.careerintelligence.mcc.MCCPayment;
import com.altitude.careerintelligence.mcc.MCCPaymentSuccess;

/**
 * Created by Jaison
 */

public class JavaScriptReceiver
{
    Context mContext;
    Activity act;

    /** Instantiate the receiver and set the context */
    public JavaScriptReceiver(Context c) {
        mContext = c;
        act= (Activity) mContext;
    }

    @JavascriptInterface
    public void showResponse(String response)
    {
        Intent intent = new Intent(mContext, MCCPaymentSuccess.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("response", response);
        mContext.startActivity(intent);
        act.finish();
    }

    @JavascriptInterface
    public void showOrders()
    {

//        MCCPayment mymcc = new MCCPayment();
//
//        mymcc.isBackAllowed = false;
        Intent intent = new Intent(mContext, MCCPayment.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("status", "cancelled");
        mContext.startActivity(intent);
//        act.finish();
    }
}
