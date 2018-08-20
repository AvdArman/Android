package com.instigatemobile.grapes.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.adapters.ActiveNodesAdapter;
import com.instigatemobile.grapes.models.ActiveNodesModel;
import com.instigatemobile.grapes.util.FileTransferService;
import com.instigatemobile.grapes.util.Util;
import com.instigatemobile.grapes.util.WiFiDirectBroadCastReceiver;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static android.os.Looper.getMainLooper;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveNodesFragment extends Fragment  {
    public static final String TAG = "wifidirect";
    public static List<ActiveNodesModel> mNodes = new LinkedList<>();
    public static ActiveNodesAdapter myAdapter;
    private WifiManager wifiManager;

    boolean sent = false;


    protected static final int CHOOSE_FILE_RESULT_CODE = 20;
    private View mContentView = null;
    private WifiP2pInfo info;


    public ActiveNodesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_nodes, container, false);
        getActivity().setTitle("Active Nodes");
        LinearLayout background = view.findViewById(R.id.nodeBackground);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            background.setBackgroundResource(R.drawable.backgroundgrapeschange);
        } else {
            background.setBackgroundResource(R.drawable.backgroundgrapes);
        }
        mNodes = Util.mNodes;
        RecyclerView recyclerView = view.findViewById(R.id.nodes);
        myAdapter = new ActiveNodesAdapter(mNodes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);


        return view;

    }


}
