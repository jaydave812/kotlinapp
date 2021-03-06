package com.example.assignment.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment.R;
import com.example.assignment.model.User;
import com.example.assignment.views.MainActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private List<User> itemList;
    private LayoutInflater mInflater;
    private MainActivity context;

    // data is passed into the constructor
    public CardViewAdapter(MainActivity context, List<User> itemList) {
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final User itemsItem = itemList.get(position);
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
                                                context.deleteUser(itemList.get(position));
                                            }
                                        }
                                );

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        }
                                );

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
                                                showAddNoteDialog(position);
                                                dialog.cancel();
                                            }
                                        }
                                );

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        }
                                );

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
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, lastName, emailAddress;
        CircleImageView profile;
        ImageView postIcon;

        ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            lastName = itemView.findViewById(R.id.lastname);
            emailAddress = itemView.findViewById(R.id.emailAddress);
            profile = itemView.findViewById(R.id.profilePhoto);
            postIcon = itemView.findViewById(R.id.postMenu);
        }

    }
// This function is use for showing dialog box layout
    public void showAddNoteDialog(final int position) {
        // create an alert builder
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);

        // set the custom layout
        final View customLayout = LayoutInflater.from(context).inflate(R.layout.add_user_dialog, null);
        builder.setView(customLayout);

        TextView send_btn = (TextView) customLayout.findViewById(R.id.send_btn);
        TextView cancel_btn = (TextView) customLayout.findViewById(R.id.cancel_btn);

        final EditText note = (EditText) customLayout.findViewById(R.id.add_note_et);
        final EditText lastname=(EditText)customLayout.findViewById(R.id.lastName);
        final EditText emailAddress=(EditText)customLayout.findViewById(R.id.emailAddress);
//        This textchange listner which is used for the add value to db
        note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itemList.get(position).setFirstName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itemList.get(position).setLastName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        emailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itemList.get(position).setEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        note.setFocusable(true);
        note.setFocusableInTouchMode(true);
        note.setCursorVisible(true);
        note.setClickable(true);
        // create and show the alert dialog
        final androidx.appcompat.app.AlertDialog dialog = builder.create();

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//on send_bt  user can edit there value
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.updateUser(itemList.get(position));
                dialog.dismiss();
            }
        });


        dialog.show();
    }

}
