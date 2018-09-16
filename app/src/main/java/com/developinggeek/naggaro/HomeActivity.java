package com.developinggeek.naggaro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabase;
    private String verified = "false";
    private DatabaseReference demand,supply;
    private TextView tvDemand,tvSupply,needed;
    double s = 0f,d=0f;
    private BarChart barChart;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvDemand = (TextView)findViewById(R.id.report_demand);
        tvSupply = (TextView)findViewById(R.id.report_supply);
        needed = (TextView)findViewById(R.id.report_needed);
        barChart = (BarChart) findViewById(R.id.barchart);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null)
        {
            Intent loginIntent = new Intent(HomeActivity.this,LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
        }
        else {
            userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    verified = dataSnapshot.child("verified").getValue().toString();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            supply = FirebaseDatabase.getInstance().getReference().child("Crops").child("Rice");
            demand = FirebaseDatabase.getInstance().getReference().child("Demand").child("rice");

            supply.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    s = Double.parseDouble(dataSnapshot.child("supply").getValue().toString());
                    tvSupply.setText(s + "");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            demand.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("data",dataSnapshot.toString());
                d = Double.parseDouble(dataSnapshot.child("year").getValue().toString());
                tvDemand.setText(d+"");

                    d = d - (s*0.0035);
                    if (d < 0)
                        d = 0;
                    needed.setText(d + "");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!verified.equals("false"))
                        startActivity(new Intent(HomeActivity.this, AddLedgerActivity.class));
                    else
                        Toast.makeText(HomeActivity.this, "You are not verified yet", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_logout)
        {
            mAuth.signOut();
            Intent loginIntent = new Intent(HomeActivity.this,LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
