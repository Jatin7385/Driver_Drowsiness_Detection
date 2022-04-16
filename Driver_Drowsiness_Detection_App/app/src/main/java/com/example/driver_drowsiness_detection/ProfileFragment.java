package com.example.driver_drowsiness_detection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private EditText fname, lname, pnum, emernum ,vehnum;
    private SharedPreferences sh;
    private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        sh = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        id = sh.getString("id","");

        fname = view.findViewById(R.id.first_name_edt_profile);
        lname = view.findViewById(R.id.last_name_edt_profile);
        pnum = view.findViewById(R.id.contact_edt_profile);
        emernum = view.findViewById(R.id.emergency_contact_edt_profile);
        vehnum = view.findViewById(R.id.vehicle_number_edt_profile);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {

                    UserClass users = snapshot1.getValue(UserClass.class);
                    assert users != null;
                    if(users.getId().equals(id))
                    {
                        fname.setText(users.getfName());
                        lname.setText(users.getlName());
                        pnum.setText(users.getpNum());
                        emernum.setText(users.getEmerNum());
                        vehnum.setText(users.getVehNum());
                        break;
                    }
                    else
                    {
                        System.out.print(users.getId() + "-" + users.getId());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}