package com.devahmed.demo.onlinepharmacy.Screens.LoginRegister;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.devahmed.demo.onlinepharmacy.R;


public class SignUpFragment extends Fragment {
    static EditText phone;

    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        phone = view.findViewById(R.id.phone_signup);
        progressBar = view.findViewById(R.id.addpost_progressbar);
        return view;
    }


    public static String getUserPhone(){
        return phone.getText().toString();
    }


}
