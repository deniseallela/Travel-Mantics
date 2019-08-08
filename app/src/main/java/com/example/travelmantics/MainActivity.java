package com.example.travelmantics;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText txtTitle, txtDescription, txtPrice;
    TravelDeals deal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUtil.openFbReference("traveldeals");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        txtDescription = findViewById(R.id.txtDescription);
        txtPrice = findViewById(R.id.txtPrice);
        txtTitle = findViewById(R.id.txtTitle);
        Intent intent = getIntent();
        TravelDeals deal = (TravelDeals) intent.getSerializableExtra("Deal");
        if (deal == null) {
            deal = new TravelDeals();
        }
        this.deal = deal;
        txtTitle.setText(deal.getTitle());
        txtDescription.setText(deal.getDescription());
        txtPrice.setText(deal.getPrice());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save_menu:
                saveDeal();
                Toast.makeText(this, "Save deal", Toast.LENGTH_LONG).show();
                clean();
                return true;
            case R.id.delete_menu:
                deleteDeal();
                Toast.makeText(this, "Deal deleted", Toast.LENGTH_SHORT).show();
                backToList();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    private void saveDeal() {
        deal.setTitle(txtTitle.getText().toString());
        deal.setDescription(txtDescription.getText().toString());
        deal.setPrice(txtPrice.getText().toString());
        if (deal.getId() == null) {
            mDatabaseReference.push().setValue(deal);
        } else {
            mDatabaseReference.push().setValue(deal);
        }

    }

    private void deleteDeal() {
        if (deal == null) {
            Toast.makeText(this, "Save before deleting", Toast.LENGTH_SHORT).show();
            return;
        }
        mDatabaseReference.child(deal.getId()).removeValue();
    }


    private void backToList(){
        Intent intent=new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void clean(){
txtTitle.setText("");
txtPrice.setText("");
txtDescription.setText("");
txtTitle.requestFocus();
    }
}
