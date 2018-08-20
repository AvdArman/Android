package com.instigatemobile.grapes.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.activities.MainActivity;
import com.instigatemobile.grapes.models.DataTransfer;

import java.util.List;

public class DataTransferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<DataTransfer> mTransferList;
    private final Context mContext;

    public DataTransferAdapter(Context context, List<DataTransfer> transferList) {
        this.mTransferList = transferList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View downloadView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download, parent, false);
            return new DownloadViewHolder(downloadView);
        } else  {
            View uploadView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload, parent, false);
            return new UploadViewHolder(uploadView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == 1) {
            DownloadViewHolder downloadViewHolder = (DownloadViewHolder) holder;
            downloadViewHolder.progress.setProgress(mTransferList.get(position).getProgress());
            downloadViewHolder.name.setText(mTransferList.get(position).getName());
            downloadViewHolder.speed.setText(mTransferList.get(position).getSpeed());
            downloadViewHolder.progress.setProgress(mTransferList.get(position).getProgress());
        } else  {
            UploadViewHolder uploadViewHolder = (UploadViewHolder) holder;
            uploadViewHolder.progress.setProgress(mTransferList.get(position).getProgress());
            uploadViewHolder.name.setText(mTransferList.get(position).getName());
            uploadViewHolder.speed.setText(mTransferList.get(position).getSpeed());
            uploadViewHolder.progress.setProgress(mTransferList.get(position).getProgress());

        }

    }
    @Override
    public int getItemCount() {
        return mTransferList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mTransferList.get(position).isDownload()) {
            return 1;
        }
        return 0;

    }

    public class UploadViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView icon;
        private final ProgressBar progress;
        private final TextView speed;
        public UploadViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.upload_name);
            icon = itemView.findViewById(R.id.upload_icon);
            progress = itemView.findViewById(R.id.upload_progressBar);
            speed = itemView.findViewById(R.id.upload_speed);

        }
    }

    public class DownloadViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView icon;
        private final ProgressBar progress;
        private final TextView speed;
        private final Button cancle;
        public DownloadViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.download_name);
            icon = itemView.findViewById(R.id.download_icon);
            progress = itemView.findViewById(R.id.download_progressBar);
            speed = itemView.findViewById(R.id.download_speed);
            cancle = itemView.findViewById(R.id.cancle_download);
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO
                    Toast.makeText(mContext, "Cancle Button", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}