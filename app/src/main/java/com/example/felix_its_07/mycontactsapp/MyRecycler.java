package com.example.felix_its_07.mycontactsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRecycler extends RecyclerView.Adapter<MyRecycler.ViewHolder>{
    private LayoutInflater layoutInflater;
    public List<MyContact> contactList;
    Context context;

    public MyRecycler(Context context,List<MyContact> contactList) {
        this.context=context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.list_row,null);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MyContact myContact= contactList.get(i);
        viewHolder.tvName.setText(myContact.getName());
        viewHolder.tvPno.setText(myContact.getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName,tvPno;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.txtName);
            tvPno=itemView.findViewById(R.id.txtPhoneNumber);
        }
    }
}
