package com.instigatemobile.grapes.adapters;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.FileUriExposedException;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.models.FileModel;
import com.instigatemobile.grapes.util.ReadWriteJson;
import com.instigatemobile.grapes.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder> {

    private Context mContext;
    private AlertDialog alertDialog;
    private boolean isHome;
    private static List<FileModel> mList;
    private static final String TAG_ACTIVITY = "Activity Not Found";

    public FilesAdapter(Context context, List<FileModel> list, boolean isHome) {
        Util.mCurrentList = Util.mHomeFileList;
        mContext = context;
        mList = list;
        this.isHome = isHome;
    }

    public static void setList(List<FileModel> list) {
        mList = list;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        return new FileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, final int position) {
        FileModel file = mList.get(position);
        holder.fileIcon.setImageResource(Integer.parseInt(file.getIcon()));
        holder.fileName.setText(file.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void openCustomDialog(final int position) {
        final LayoutInflater li = LayoutInflater.from(mContext);
        final View fileView = li.inflate(R.layout.file_info_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(fileView);
        final Button btnDelete = fileView.findViewById(R.id.btn_delete);
        final ImageView imageView = fileView.findViewById(R.id.info_file_icon);
        final ListView infoList = fileView.findViewById(R.id.file_data_list);
        final List<String> info = getInfoList(position);
        final ArrayAdapter<String> infoAdapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, info);
        imageView.setImageResource(Integer.parseInt(mList.get(position).getIcon()));
        infoList.setAdapter(infoAdapter);
        alertDialog = alertDialogBuilder.create();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConfirmDialog(position);
            }
        });
        alertDialog.show();
        alertDialogBuilder.setCancelable(true);
    }

    private void openConfirmDialog(final int position) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        ReadWriteJson delete = new ReadWriteJson(mContext);
                        mList.remove(position);
                        delete.writeListIntoFile(mList, Util.HOME_FILE_NAME);
                        Util.mHomeFileList = mList;
                        Util.mCurrentList = mList;
                        notifyDataSetChanged();
                        alertDialog.cancel();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(mContext));
        builder.setMessage("Are you sure you want to delete file?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void openFile(final int position) {
        final String type = Util.mCurrentList.get(position).getExtension();
        final String path = Util.mCurrentList.get(position).getPath();
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        switch (type) {
            case "jpg":
            case "png":
            case "jpeg":
            case "gif":
                intent.setDataAndType(Uri.parse(path), "image/*");
                break;
            case "mp3":
                intent.setDataAndType(Uri.parse(path), "video/*");
                break;
            case "mp4":
            case "avi":
                intent.setDataAndType(Uri.parse(path), "video/*");
                break;
            case "pdf":
                File file = new File(path);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                break;
            default:
                Toast.makeText(mContext, "Select a program to open this file", Toast.LENGTH_LONG).show();
                break;
        }
        try {
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "Activity Not Found ", Toast.LENGTH_LONG).show();
            Log.e(TAG_ACTIVITY, "Activity Not Found " + e.toString());
        } catch (FileUriExposedException e) {
            Toast.makeText(mContext, "File Uri Exposed Exception ", Toast.LENGTH_LONG).show();
        }
    }


    private List<String> getInfoList(int position) {
        final FileModel file = mList.get(position);
        final String dots = " : ";
        List<String> list = new ArrayList<>();
        list.add(Util.NAME + dots + file.getName());
        list.add(Util.SIZE + dots + file.getSize());
        list.add(Util.DATE + dots + file.getDate());
        list.add(Util.EXTENSION + dots + file.getExtension());
        return list;
    }

    class FileViewHolder extends RecyclerView.ViewHolder {

        private ImageView fileIcon;
        private TextView fileName;

        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileIcon = itemView.findViewById(R.id.file_icon);
            fileName = itemView.findViewById(R.id.list_file_name);
            if (isHome) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        openCustomDialog(getAdapterPosition());
                        return false;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        openFile(getAdapterPosition());
                    }
                });
            } else {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        //TODO
                    }
                });
            }

        }
    }
}