package com.example.travelmantics;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static FirebaseUtil firebaseUtil;
    public static ArrayList<TravelDeals> mDeals;
    private FirebaseUtil(){}

    public static void openFbReference(String ref) {
 if (firebaseUtil==null){
     firebaseUtil=new FirebaseUtil();
     mFirebaseDatabase=FirebaseDatabase.getInstance();
     mDeals=new ArrayList<TravelDeals>();
 }
 mDatabaseReference=mFirebaseDatabase.getReference().child(ref);
    }
}
