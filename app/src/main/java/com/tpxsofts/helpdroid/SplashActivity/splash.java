package com.tpxsofts.helpdroid.SplashActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.tpxsofts.helpdroid.Activities.home;
import com.tpxsofts.helpdroid.Model.NetworkConnection;
import com.tpxsofts.helpdroid.R;

import static android.view.View.VISIBLE;

public class splash extends AppCompatActivity {
    Dialog dialog;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);






        dialog = new Dialog(context);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(!new NetworkConnection().checkConnection(context))
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    dialog.dismiss();
                    new NetworkConnection().noInternetDialog(context,getLayoutInflater(),splash.this,"No Internet Connection");
                }
            },2000);

        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    dialog(false);

                    startActivity(new Intent(getApplicationContext(), home.class));
                    finish();
                }
            },3000);
        }






    }



    private void dialog(boolean flag)
    {
        if(!flag)
        {
            dialog.dismiss();
        }
        else
        {

            dialog.setContentView(R.layout.dialog);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            ProgressBar prog = (ProgressBar) dialog.findViewById(R.id.myprogress);
            prog.setVisibility(VISIBLE);
            dialog.show();
        }

    }




}