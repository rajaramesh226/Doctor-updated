package com.Udoctor.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.Udoctor.doctor.Adapters.EyeAdapter;
import com.Udoctor.doctor.Adapters.EyeDoctor;
import com.Udoctor.doctor.Adapters.HeartAdapter;
import com.Udoctor.doctor.Adapters.HeartDoctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EyeActivity extends AppCompatActivity {


    private Toolbar toolbar;

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    EyeAdapter eyeAdapter;
    ArrayList<EyeDoctor> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye);

        //statusbar color
        getWindow().setStatusBarColor(ContextCompat.getColor(EyeActivity.this, R.color.btncol));

        toolbar=(Toolbar)findViewById(R.id.tool_bar_eye);
        recyclerView = findViewById(R.id.eyeDoctorlist);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Eyelist");


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        eyeAdapter = new EyeAdapter(this,list);
        recyclerView.setAdapter(eyeAdapter);

        databaseReference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    EyeDoctor eyeDoctor = dataSnapshot.getValue(EyeDoctor.class);
                    list.add(eyeDoctor);
                }
                eyeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}