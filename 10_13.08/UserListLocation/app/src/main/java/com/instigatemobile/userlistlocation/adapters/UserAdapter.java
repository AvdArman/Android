package com.instigatemobile.userlistlocation.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.instigatemobile.userlistlocation.activities.MapsActivity;
import com.instigatemobile.userlistlocation.R;
import com.instigatemobile.userlistlocation.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final byte TYPE_MALE = 0;
    private static final byte TYPE_FEMALE = 1;
    private List<User> userList;
    private Context context;
    private FragmentManager fragmentManager;

    public UserAdapter(FragmentManager fragmentManager, Context context, List<User> list) {
        this.context = context;
        userList = list;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getItemViewType(int position) {
        User user = userList.get(position);
        if (user.getGender().equals("male")) {
            return TYPE_MALE;
        }
        if (user.getGender().equals("female")) {
            return TYPE_FEMALE;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        int viewType = holder.getItemViewType();
        User user = userList.get(position);
        switch (viewType) {
            case TYPE_MALE:
                MaleViewHolder maleHolder = (MaleViewHolder) holder;
                Picasso.get().load(user.getPicture()).into(maleHolder.image);
                maleHolder.name.setText(user.getName());
                break;
            case TYPE_FEMALE:
                FemaleViewHolder femaleHolder = (FemaleViewHolder) holder;
                Picasso.get().load(user.getPicture()).into(femaleHolder.image);
                femaleHolder.name.setText(user.getName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case TYPE_MALE:
                View maleView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.user_item_male, parent, false);
                holder = new MaleViewHolder(maleView);
                break;
            case TYPE_FEMALE:
                View femaleView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.user_item_female, parent, false);
                holder = new FemaleViewHolder(femaleView);
                break;
            default:
                holder = null;
                break;
        }
        return holder;
    }

    public class MaleViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView image;
        private TextView name;

        MaleViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.male_image);
            name = itemView.findViewById(R.id.tv_male_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(getAdapterPosition());
                }
            });
        }
    }

    private void startActivity(int position) {
        Intent intent = new Intent(context, MapsActivity.class);
        User.Location location = userList.get(position).getLocation();
        intent.putExtra("lat", location.getCoordinates().getLatitude());
        intent.putExtra("lng", location.getCoordinates().getLatitude());
        intent.putExtra("addr", location.getAddress());
        context.startActivity(intent);
    }

    public class FemaleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView image;
        private TextView name;
        private ImageButton btnMail;
        private ImageButton btnCall;

        FemaleViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.female_image);
            name = itemView.findViewById(R.id.tv_female_name);
            btnMail = itemView.findViewById(R.id.btn_mail);
            btnCall = itemView.findViewById(R.id.btn_call);
            btnCall.setOnClickListener(this);
            btnMail.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {
            User user = userList.get(getAdapterPosition());
            switch (view.getId()) {
                case R.id.btn_call:
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + user.getCell()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        context.startActivity(callIntent);
                    }
                    break;
                case R.id.btn_mail:
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getEmail()});
                    try {
                        context.startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}