package com.devahmed.demo.onlinepharmacy.Screens.AdminDashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.devahmed.demo.onlinepharmacy.Common.Navigator.Navigator;
import com.devahmed.demo.onlinepharmacy.R;

public class AdminFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment , container , false);

        CardView users = view.findViewById(R.id.users);
        CardView branches = view.findViewById(R.id.branches);
        CardView orders = view.findViewById(R.id.orders);
        CardView bills = view.findViewById(R.id.Bills);
        CardView notifications = view.findViewById(R.id.Notfications);


        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.instance(requireActivity()).navigate(R.id.usersControlFragment);
            }
        });

        branches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.instance(requireActivity()).navigate(R.id.branchesControlFragment);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.instance(requireActivity()).navigate(R.id.ordersControlFragment);
            }
        });

        bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.instance(requireActivity()).navigate(R.id.billsFragment);
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.instance(requireActivity()).navigate(R.id.pushNotificationsFragment);
            }
        });

        return view;
    }
}
