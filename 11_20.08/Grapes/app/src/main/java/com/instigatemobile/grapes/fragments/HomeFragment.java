package com.instigatemobile.grapes.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codekidlabs.storagechooser.StorageChooser;
import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.activities.MainActivity;
import com.instigatemobile.grapes.adapters.FilesAdapter;
import com.instigatemobile.grapes.models.FileModel;
import com.instigatemobile.grapes.util.ReadWriteJson;
import com.instigatemobile.grapes.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.instigatemobile.grapes.fragments.ActiveNodesFragment.TAG;
import static com.instigatemobile.grapes.util.Util.copyFile;

public class HomeFragment extends Fragment {

    private StorageChooser mChooser;
    @SuppressLint("StaticFieldLeak")
    public static FilesAdapter mFileAdapter;
    private StorageChooser.Builder mBuilder;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        final int numberOfColumns = 3;
        final RecyclerView fileRv = view.findViewById(R.id.files_recycler_view);
        fileRv.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        mFileAdapter = new FilesAdapter(getContext(), Util.mCurrentList, true);
        setAddButtonListener(view);
        fileRv.setAdapter(mFileAdapter);
        return view;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            Util.isHomeFragment = true;
            Util.mCurrentList = Util.mHomeFileList;
            MainActivity.categories.setSelection(Util.homeCategorie);
            MainActivity.sort.setSelection(Util.homeSort);
        }
    }

    public static void updateList() {
        mFileAdapter.notifyDataSetChanged();
    }

    private void setAddButtonListener(View view) {
        mBuilder = Util.mBuilder;
        mBuilder.withMemoryBar(true);
        mBuilder.allowAddFolder(true);
        mBuilder.allowCustomPath(true);
        mBuilder.setType(StorageChooser.FILE_PICKER);
        FloatingActionButton fab = view.findViewById(R.id.add_file_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChooser = mBuilder.build();
                mChooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
                    @Override
                    public void onSelect(String path) {
                        if (isUniqueFile(path)) {
                            final String fileName = getFileName(path);
                            final String fileSize = getFileSize(path);
                            final String extension = getExtension(path);
                            final String icon = String.valueOf(getIconId(extension));
                            final String date = getLastModifiedDate(path);
                            FileModel file = new FileModel(fileSize, icon,
                                    fileName, path, date, extension);
                            Util.mHomeFileList.add(file);
                            Util.mCurrentList.size();
                            Util.mCurrentList = Util.mHomeFileList;
                            ReadWriteJson writeJson = new ReadWriteJson(getContext());
                            writeJson.writeListIntoFile(Util.mHomeFileList, Util.HOME_FILE_NAME);
                            mFileAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "File already exists",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mChooser.show();
            }
        });
    }

    private boolean isUniqueFile(String path) {
        for (FileModel file : Util.mHomeFileList) {
            if (file.getPath().equals(path)) {
                return false;
            }
        }
        return true;
    }

    private String getLastModifiedDate(final String filePath) {
        File file = new File(filePath);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(file.lastModified());
    }

    private static int getIconId(final String extension) {
        switch (extension) {
            case "jpg":
            case "png":
                return R.drawable.icons_jpg;
            case "mp3":
                return R.drawable.icons_mp3;
            case "pdf":
                return R.drawable.icons_pdf;
            case "mp4":
                return R.drawable.icons_video;
            default:
                return R.drawable.icons_other;
        }
    }

    private static String getExtension(final String filePath) {
        if (filePath.lastIndexOf(".") != -1 && filePath.lastIndexOf(".") != 0) {
            return filePath.substring(filePath.lastIndexOf(".") + 1);
        }
        return "";
    }

    private String getFileSize(final String filePath) {
        File file = new File(filePath);
        long size = file.length();
        String hrSize;
        double m = size/1024.0;
        DecimalFormat dec = new DecimalFormat("0.00");
        if (m > 1) {
            hrSize = dec.format(m).concat(" KB");
        } else {
            hrSize = dec.format(size).concat(" MB");
        }
        return hrSize;
    }

    private String getFileName(final String path) {
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        if (fileName.indexOf(".") > 0) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

}