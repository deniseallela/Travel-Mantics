package com.example.travelmantics;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewolder>{
    ArrayList<TravelDeals> deals;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    public DealAdapter(){
        FirebaseUtil.openFbReference("traveldeals");
        mFirebaseDatabase=FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference=FirebaseUtil.mDatabaseReference;
        deals=FirebaseUtil.mDeals;
        mChildListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TravelDeals td=dataSnapshot.getValue(TravelDeals.class);
                Log.d("Deal:",td.getTitle());
                td.setId(dataSnapshot.getKey());
                deals.add(td);
                notifyItemInserted(deals.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildListener);
    }
    @NonNull
    @Override
    public DealViewolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View itemView= LayoutInflater.from(context)
                .inflate(R.layout.rv_row,parent,false);
        return new DealViewolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewolder holder, int position) {
    TravelDeals deal=deals.get(position);
    holder.bind(deal);
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    //viewholder makes scrolling easier
    public class DealViewolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
TextView tvTitle;
TextView tvDesccription;
TextView tvPrice;
        public DealViewolder(@NonNull View itemView) {
            //descibe how to bind data to single row
            super(itemView);
            tvTitle=(TextView)itemView.findViewById(R.id.tvTitle);
            tvDesccription=(TextView)itemView.findViewById(R.id.tvDescription);
            tvDesccription=(TextView)itemView.findViewById(R.id.tvDescription);
            tvPrice=(TextView)itemView.findViewById(R.id.tvPrice);
            itemView.setOnClickListener(this );
        }
        public void bind(TravelDeals deals){
            tvTitle.setText(deals.getTitle());
            tvDesccription.setText(deals.getDescription());
            tvPrice.setText(deals.getPrice());

        }

        @Override
        public void onClick(View v) {
            int position =getAdapterPosition();
            Log.d("Click", String.valueOf(position));
            TravelDeals selectedDeal=deals.get(position);
            Intent intent=new Intent(v.getContext(),MainActivity.class);
            intent.putExtra("Deals",selectedDeal);
            v.getContext().startActivity(intent);
        }
    }
}
