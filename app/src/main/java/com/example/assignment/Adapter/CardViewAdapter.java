package com.example.assignment.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.assignment.Model.DataItem;
import com.example.assignment.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CardViewAdapter  extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private List<DataItem> itemList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public CardViewAdapter(Context context, List<DataItem> itemList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.itemList = itemList;

    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_user_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DataItem itemsItem = itemList.get(position);
        holder.name.setText(itemsItem.getFirstName());
        Glide.with(context).load(itemsItem.getAvatar()).into(holder.profile);
        holder.lastName.setText(itemsItem.getLastName().trim());
        holder.emailAddress.setText(itemsItem.getEmail().trim());
        holder.postIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, v);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    popup.setGravity(Gravity.END);
                }

                //inflating menu from xml resource
                popup.inflate(R.menu.menu_latyout);

                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        AlertDialog alert11;
                        switch (item.getItemId()) {
                            case R.id.delete_post:
                                //handle delete post click
                                builder1.setTitle("Are you sure you want to delete?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                alert11 = builder1.create();
                                alert11.show();
                                return true;
                            case R.id.edit_post:
                                builder1.setTitle("Are you sure you want to edit?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                alert11 = builder1.create();
                                alert11.show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return itemList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView name, lastName, emailAddress;
        CircleImageView profile;
        ImageView postIcon;
        ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            lastName = itemView.findViewById(R.id.lastname);
            emailAddress = itemView.findViewById(R.id.emailAddress);
            profile = itemView.findViewById(R.id.profilePhoto);
            postIcon=itemView.findViewById(R.id.postMenu);
        }

    }
}
