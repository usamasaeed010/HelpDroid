package com.tpxsofts.helpdroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class messages extends AppCompatActivity {
    RecyclerView recyclerView,recyclergridView;
    ArrayList<MessagesDataForPopullation> dataarray= new ArrayList<>();
    ArrayList<MessagesnotificationDataForPopullation> notificationarray= new ArrayList<>();
    MyadapterMessages myadapterMessages;
    MyadapterMessagesnotification myadapterMessagesnotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        recyclerView= findViewById(R.id.Rcmessage);
        recyclergridView= findViewById(R.id.gridmessage);
        // dummy data to populate message class
        dataarray.add(new MessagesDataForPopullation("CMH","Cant, Lahore","11223"));
        dataarray.add(new MessagesDataForPopullation("Foji foundation","Cant, Lahore","11223"));
        dataarray.add(new MessagesDataForPopullation("services","Cant, Lahore","11223"));
        myadapterMessages=new MyadapterMessages(dataarray,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(myadapterMessages);

        // dummy data to populate Notification message  grid class
        notificationarray.add(new MessagesnotificationDataForPopullation(R.drawable.male));
        notificationarray.add(new MessagesnotificationDataForPopullation(R.drawable.male));
        notificationarray.add(new MessagesnotificationDataForPopullation(R.drawable.male));
        myadapterMessagesnotification=new MyadapterMessagesnotification(notificationarray,this);
        recyclergridView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclergridView.setAdapter(myadapterMessagesnotification);
    }
}
