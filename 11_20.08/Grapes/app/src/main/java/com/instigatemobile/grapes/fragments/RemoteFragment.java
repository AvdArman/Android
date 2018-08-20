package com.instigatemobile.grapes.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.activities.MainActivity;
import com.instigatemobile.grapes.adapters.FilesAdapter;
import com.instigatemobile.grapes.util.ReadWriteJson;
import com.instigatemobile.grapes.util.Util;


public class RemoteFragment extends Fragment {

    private static FilesAdapter mFileAdapter;

    public RemoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_remote, container, false);
        final int numberOfColumns = 3;
        final RecyclerView fileRv = view.findViewById(R.id.remote_list);
        fileRv.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        ReadWriteJson remoteFiles = new ReadWriteJson(getContext());
        remoteFiles.mergeJsonFiles();
        mFileAdapter = new FilesAdapter(getContext(), Util.mCurrentList, false);
        fileRv.setAdapter(mFileAdapter);
        return view;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            Util.isHomeFragment = false;
            Util.mCurrentList = Util.mRemoteFileList;
            MainActivity.categories.setSelection(Util.remoteCategorie);
            MainActivity.sort.setSelection(Util.remoteSort);
        }
    }

    public static void updateList() {
        mFileAdapter.notifyDataSetChanged();
    }
}
