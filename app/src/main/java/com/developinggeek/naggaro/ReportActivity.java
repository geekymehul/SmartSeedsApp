package com.developinggeek.naggaro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReportActivity extends AppCompatActivity
{
    private DatabaseReference demand,supply;
    private TextView tvDemand,tvSupply,needed;
    float s = 0f,d=0f;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

       /* supply = FirebaseDatabase.getInstance().getReference().child("Crops").child("Rice");
        demand = FirebaseDatabase.getInstance().getReference().child("Demand").child("rice");

        supply.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 s = Long.parseLong(dataSnapshot.child("supply").getValue().toString());
                 tvSupply.setText(s+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        demand.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                d = Long.parseLong(dataSnapshot.child("2018").getValue().toString());
                tvDemand.setText(d+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        d = d-s;
        if(d<0)
            d = 0;
        needed.setText(d+"");*/
    }

}
