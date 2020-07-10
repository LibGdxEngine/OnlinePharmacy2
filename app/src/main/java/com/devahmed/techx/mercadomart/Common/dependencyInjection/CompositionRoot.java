package com.devahmed.techx.mercadomart.Common.dependencyInjection;

import com.google.firebase.database.FirebaseDatabase;

public class CompositionRoot {

    public FirebaseDatabase ConnectToFirebase() {
        //get Data reference from server or database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        return db;
    }


}