package com.instigatemobile.grapes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.models.TeamMember;

import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MembersViewHolder> {

    private List<TeamMember> mMembers;

    @NonNull
    @Override
    public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_member_item, parent, false);
        return new MembersViewHolder(view);
    }

    public MembersAdapter(List<TeamMember> members) {
        this.mMembers = members;
    }

    @Override
    public void onBindViewHolder(@NonNull MembersViewHolder holder, int position) {
        final TeamMember member = mMembers.get(position);
        holder.textView.setText(member.getmName());
        holder.imageView.setImageResource(member.getmImage());
    }

    @Override
    public int getItemCount() {
        return mMembers.size();
    }

    public class MembersViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public MembersViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.member_image);
            textView = itemView.findViewById(R.id.member_name);
        }
    }
}
