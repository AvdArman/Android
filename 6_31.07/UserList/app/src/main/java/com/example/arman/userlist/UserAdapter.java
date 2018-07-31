package com.example.arman.userlist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> implements Filterable {

    public static final String POSITION = "itemPosition";
    public static final String IMGPOSITION = "imgId";

    private final Context context;
    private List<User> users;
    private List<User> filteredUsers;
    private Intent intent;
    private int imgPosition;

    public UserAdapter(List<User> users, Context context) {
        this.users = users;
        filteredUsers = users;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final User user = filteredUsers.get(position);
        holder.tvName.setText(user.getFullName());
        holder.tvDescription.setText(user.getDescription());
        Random rand = new Random();
        imgPosition = rand.nextInt(user.getImgUrlList().size() - 1);
        Picasso.get().load(user.getImgUrlList().get(imgPosition)).into(holder.image);
        setItemListener(holder, position);
        setMailBtnListener(holder, user);
        setCallBtnListener(holder, user);
    }

    private void setCallBtnListener(@NonNull MyViewHolder holder, final User user) {
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + user.getPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    context.startActivity(callIntent);
                }
            }
        });
    }

    private void setMailBtnListener(@NonNull MyViewHolder holder, final User user) {
        holder.btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getMailAddress()});
                try {
                    context.startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setItemListener(@NonNull MyViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context, ScrollingActivity.class);
                intent.putExtra(POSITION, position);
                intent.putExtra(IMGPOSITION, imgPosition);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredUsers.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredUsers = users;
                } else {
                    List<User> filteredList = new ArrayList<>();
                    for (User row : users) {
                        if (row.getFullName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredUsers = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUsers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUsers = (List<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView image;
        private TextView tvName;
        private TextView tvDescription;
        private ImageButton btnMail;
        private ImageButton btnCall;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            btnMail = itemView.findViewById(R.id.btn_mail);
            btnCall = itemView.findViewById(R.id.btn_call);
        }
    }
}