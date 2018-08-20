package com.instigatemobile.grapes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.instigatemobile.grapes.models.ActiveNodesModel;
import com.instigatemobile.grapes.R;

import java.util.List;

public class ActiveNodesAdapter extends RecyclerView.Adapter<ActiveNodesAdapter.MyViewHolder> {

    private List<ActiveNodesModel> mActiveNodes;

    public ActiveNodesAdapter(List<ActiveNodesModel> activeNodes) {
        this.mActiveNodes = activeNodes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_active_nodes_model, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ActiveNodesModel nodes = mActiveNodes.get(position);
        holder.nickname.setText(nodes.getmNickname());
        holder.ip.setText(nodes.getmIp());


    }

    @Override
    public int getItemCount() {
        return mActiveNodes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nickname;
        TextView ip;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.nicknameNode);
            ip = itemView.findViewById(R.id.ipNode);
        }
    }
}
