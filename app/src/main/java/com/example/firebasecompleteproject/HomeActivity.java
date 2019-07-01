package com.example.firebasecompleteproject;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.firebasecompleteproject.model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;


    //Firebase.....
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //Global variable
    private String title;
    private String description;
    private String budget;
    private String post_key;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Firebase Complete Project");
        setSupportActionBar(toolbar);

        //Firebase......

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uid=mUser.getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("AllData").child(uid);

        //RecyclerView...
        recyclerView=findViewById(R.id.RecyclerId);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //locating ActionButton...
        fab=findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                
            }
        });

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


    public void addData(){


        AlertDialog.Builder mydialog=new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        View myview=inflater.inflate(R.layout.inputlayout,null);
        mydialog.setView(myview);
        final AlertDialog dialog=mydialog.create();

        final EditText mTitle=myview.findViewById(R.id.title);
        final EditText mDescription=myview.findViewById(R.id.Description);
        final EditText mBudget=myview.findViewById(R.id.Budget);
        Button btnSave=myview.findViewById(R.id.save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=mTitle.getText().toString().trim();
                String Description=mDescription.getText().toString().trim();
                String Budget=mBudget.getText().toString().trim();

                String mDate= DateFormat.getDateInstance().format(new Date());
                String id=mDatabase.push().getKey();

                Data data=new Data(title, Description,Budget,id, mDate);
                mDatabase.child(id).setValue(data);
                Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Data,MyViewHolder>adapter=new FirebaseRecyclerAdapter<Data, MyViewHolder>
                (
                        Date.class,
                        R.layout.dataitem,
                        MyViewHolder.class,
                        mDatabase

                ) {
            @NonNull


            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull final Data model) {
                holder.setTitle(model.getTitle());
                holder.setDescription(model.getDescription());
                holder.setBudget(model.getBudget());
                holder.setDate(model.getDate());


                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post_key=getRef(position).getKey();
                        title=model.getTitle();
                        upDateData();

                    }
                });

            }

        };
        recyclerView.setAdapter(adapter);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setTitle(String title){

            TextView mTitle=mView.findViewById(R.id.title_item);
            mTitle.setText(title);
        }
        public void setDescription(String description){
            TextView mDescription=mView.findViewById(R.id.description_item);
            mDescription.setText(description);
        }

        public void setBudget(String budget){
            TextView mBudget=mView.findViewById(R.id.budget_item);
            mBudget.setText(budget);
        }
        public void setDate(String date){
            TextView mdate=mView.findViewById(R.id.date_item);
            mdate.setText(date);
        }

    }


    public  void upDateData(){
        AlertDialog.Builder MyDialog=new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        View myview=inflater.inflate(R.layout.update,null);
        MyDialog.setView(myview);
        final AlertDialog dialog=MyDialog.create();

        final EditText mTitle=myview.findViewById(R.id.title_upd);
        final EditText mDescription=myview.findViewById(R.id.btnDelete_upd);
        final EditText mBudget=myview.findViewById(R.id.budget_upd);
        Button btnUpdate=myview.findViewById(R.id.btnUpdate_upd);
        Button btnDelete=myview.findViewById(R.id.btnDelete_upd);

        mTitle.setText(title);
        mTitle.setSelection(title.length());

        mDescription.setText(description);
        mDescription.setSelection(description.length());

        mBudget.setText(budget);
        mBudget.setSelection(budget.length());



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title=mTitle.getText().toString().trim();
                description=mDescription.getText().toString().trim();
                budget=mBudget.getText().toString().trim();

                String mDate=DateFormat.getDateInstance().format(new Date());
                Data data=new Data(title,description,budget,post_key,mDate);
                mDatabase.child(post_key).setValue(data);

                dialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child(post_key).removeValue();
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
