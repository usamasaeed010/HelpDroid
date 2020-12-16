package com.tpxsofts.helpdroidx.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tpxsofts.helpdroidx.R;

public class NetworkConnection
{
    public boolean checkConnection(Context context)
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void noInternetDialog(final Context context, LayoutInflater inflater, final Activity activity, String msg)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final View custom_alert = inflater.inflate(R.layout.custom_alert_empty_dialog,null);
        TextView showMsg=custom_alert.findViewById(R.id.msg);
        showMsg.setText(msg);
        alert.setView(custom_alert);
        final AlertDialog dialog=alert.create();
        custom_alert.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dialog.dismiss();
              activity.finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}
