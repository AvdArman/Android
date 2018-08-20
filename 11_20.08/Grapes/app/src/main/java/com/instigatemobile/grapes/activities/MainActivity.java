package com.instigatemobile.grapes.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codekidlabs.storagechooser.Content;
import com.codekidlabs.storagechooser.StorageChooser;
import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.adapters.DataTransferAdapter;
import com.instigatemobile.grapes.adapters.FilesAdapter;
import com.instigatemobile.grapes.adapters.MainFragmentsAdapter;
import com.instigatemobile.grapes.fragments.ActiveNodesFragment;
import com.instigatemobile.grapes.fragments.HomeFragment;
import com.instigatemobile.grapes.fragments.RemoteFragment;
import com.instigatemobile.grapes.models.ActiveNodesModel;
import com.instigatemobile.grapes.models.DataTransfer;
import com.instigatemobile.grapes.models.FileModel;
import com.instigatemobile.grapes.util.FileTransferService;
import com.instigatemobile.grapes.util.ReadWriteJson;
import com.instigatemobile.grapes.util.Util;
import com.instigatemobile.grapes.util.WiFiDirectBroadCastReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static android.os.Looper.getMainLooper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String KEY_DROPDOWN = "passId";
    public static Spinner categories;
    public static Spinner sort;
    private DataTransferAdapter mDataTransferAdapter;
    private RecyclerView mDatatransferRecyclerView;
    private List<DataTransfer> mDatatransferList;
    private Button mBtnExit;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    List<Integer> portList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 199);
        }
        setDrawerLayout(getToolbar());
        setSearchView();

        TextView symb = findViewById(R.id.name_circle);
        final TextView nickName = findViewById(R.id.nick_name);
        Util.mNickname = prefs.getString(Util.NICKNAME, null);
        if (Util.mNickname != null) {
            symb.setText(String.valueOf(Util.mNickname.charAt(0)));
            nickName.setText(String.valueOf(Util.mNickname));
        }
        mBtnExit = findViewById(R.id.button_exit);
        mBtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                prefs.edit().putString(Util.NICKNAME, null).apply();
                Util.HOME_FILE_NAME = "files.json";
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
                finish();
            }
        });

        if (Util.HOME_FILE_NAME.equals("files.json")) {
            Util.HOME_FILE_NAME = Util.mNickname + Util.HOME_FILE_NAME;
        }

        setPaths();
        fillList();

        final ViewPager viewPager = setViewPagers();
        setTabLayout(viewPager);
        categories = findViewById(R.id.categories);
        sort = findViewById(R.id.sort_by);
        sortListener(sort);
        categoriesListener(categories);
        adapter();
        init();
        listeners();
        connectNode();
    }

    private void adapter() {
        mDatatransferRecyclerView = findViewById(R.id.datatransferRecyclerView);
        mDatatransferRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatatransferList = new ArrayList<>();
        mDatatransferList.add(new DataTransfer(true,"-music","15mb/s",15));
        mDatatransferList.get(0).setProgress(60);
        mDatatransferList.add(new DataTransfer(true,"image","2 fdfagargergergergegrmb/s",15));
        mDatatransferList.add(new DataTransfer(false,"music","2 mb/s",90));
        mDatatransferList.add(new DataTransfer(true,"video","1 mb/s",55));
        mDatatransferList.add(new DataTransfer(false,"video","1 mb/s",69));
        mDatatransferList.add(new DataTransfer(false,"pfd_file","3 mb/s",40));
        mDatatransferList.add(new DataTransfer(true,"video1","2 mb/s",33));
        mDatatransferList.add(new DataTransfer(false,"image1","2 mb/s",12));
        mDatatransferList.add(new DataTransfer(false,"book","3 mb/s",88));
        mDatatransferList.add(new DataTransfer(false,"photo","1 mb/s",45));
        mDatatransferList.add(new DataTransfer(true,"video","3 mb/s",14));

        mDataTransferAdapter = new DataTransferAdapter(this,mDatatransferList);
        mDatatransferRecyclerView.setAdapter(mDataTransferAdapter);
    }


    private void setPaths() {
        File file = new File(Util.HOME_FILE_NAME);
        Util.mHomeFilePath = file.getAbsolutePath();
        Util.mCurrentList = Util.mHomeFileList;
        file = new File(Util.REMOTE_FILE_NAME);
        Util.mRemoteFilePath = file.getAbsolutePath();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(this, DropdownActivity.class);
        intent.putExtra(KEY_DROPDOWN, id);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ic_exit) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            prefs.edit().putString(Util.NICKNAME, null).apply();
            startActivity(new Intent(MainActivity.this, LogInActivity.class));
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Toolbar getToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void setDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setTabLayout(ViewPager viewPager) {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_remote);
    }

    @NonNull
    private ViewPager setViewPagers() {
        Util.mBuilder = getBuilder();
        final ViewPager viewPager = findViewById(R.id.pager);
        MainFragmentsAdapter adapter = new MainFragmentsAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new RemoteFragment());
        viewPager.setAdapter(adapter);
        return viewPager;
    }

    private void fillList() {
        ReadWriteJson read = new ReadWriteJson(this);
        String tmp = read.readJson(null, Util.HOME_FILE_NAME);
        Util.mHomeFileList.clear();
        JSONArray jsonArray;
        try {
            if (tmp.length() > 4) {
                JSONObject tmpObj = new JSONObject(tmp);
                jsonArray = tmpObj.getJSONArray("files");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject tmpObject = jsonArray.getJSONObject(i);
                    Util.mHomeFileList.add(new FileModel(tmpObject.getString(Util.SIZE),
                            tmpObject.getString(Util.ICON),
                            tmpObject.getString(Util.NAME),
                            tmpObject.getString(Util.PATH),
                            tmpObject.getString(Util.DATE),
                            tmpObject.getString(Util.EXTENSION)));
                }
            }
        } catch (JSONException e) {
            String TAG_JSON = "Read json";
            Log.e(TAG_JSON, "Can not read from json file" + e.toString());
        }
        Util.mCurrentList = Util.mHomeFileList;
        Util.mBeforeSearchList = Util.mCurrentList;
    }

    private StorageChooser.Builder getBuilder() {
        Content c = new Content();
        c.setmCreateLabel("Create");
        c.setmInternalStorageText("My Storage");
        c.setmCancelLabel("Cancel");
        c.setmSelectLabel("Select");
        c.setmOverviewHeading("Choose Drive");
        StorageChooser.Builder builder = new StorageChooser.Builder();
        builder.withActivity(this)
                .withFragmentManager(getFragmentManager())
                .setMemoryBarHeight(1.5f)
                .disableMultiSelect()
                .withContent(c);
        return builder;
    }

    private void setSearchView() {
        SearchView search = findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, "Into search", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s == null) {
                    Util.mCurrentList = Util.mBeforeSearchList;
                } else {
                    Util.search(s);
                    FilesAdapter.setList(Util.mCurrentList);
                    if (Util.isHomeFragment) {
                        HomeFragment.updateList();
                    } else {
                        RemoteFragment.updateList();
                    }
                }
                return true;
            }
        });
    }

    private void sortListener(Spinner sort) {
        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (Util.isHomeFragment) {
                    Util.homeSort = position;
                } else {
                    Util.remoteSort = position;
                }
                switch (selectedItem.toLowerCase()) {
                    case Util.NAME:
                        Util.sortBy(Util.NAME);
                        break;
                    case Util.DATE:
                        Util.sortBy(Util.DATE);
                        break;
                    case Util.FILE_SIZE:
                        Util.sortBy(Util.SIZE);
                        break;
                }
                if (Util.isHomeFragment) {
                    HomeFragment.updateList();
                } else {
                    RemoteFragment.updateList();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void categoriesListener(Spinner sort) {
        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (Util.isHomeFragment) {
                    Util.mCurrentList = Util.mHomeFileList;
                    Util.homeCategorie = position;
                } else {
                    Util.mCurrentList = Util.mRemoteFileList;
                    Util.remoteCategorie = position;
                }

                switch (selectedItem.toLowerCase()) {
                    case "all":
                        Util.mCurrentList = Util.mHomeFileList;
                        break;
                    case "pictures":
                        Util.filter("jpg", "png", "ico", "gif");
                        break;
                    case "videos":
                        Util.filter("mp4", "avi");
                        break;
                    case "musics":
                        Util.filter("mp3");
                        break;
                    case "books":
                        Util.filter("pdf");
                        break;
                    case "other":
                        filterForOther();
                        break;
                }
                Util.mBeforeSearchList = Util.mCurrentList;
                FilesAdapter.setList(Util.mCurrentList);
                if (Util.isHomeFragment) {
                    HomeFragment.updateList();
                } else {
                    RemoteFragment.updateList();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void filterForOther() {
        String[] extArray = {"jpg", "png", "ico", "gif", "pdf", "mp3", "mp4", "avi"};
        List<FileModel> filterList = new ArrayList<>();
        for (FileModel file : Util.mCurrentList) {
            if (!hasExtension(file.getExtension(), extArray)) {
                filterList.add(file);
            }
        }
        Util.mCurrentList = filterList;
    }

    private boolean hasExtension(String extension, String[] extensions) {
        for (String tmpExtension : extensions) {
            if (extension.contains(tmpExtension)) {
                return true;
            }
        }
        return false;
    }


///////////////////////////////////////////////////////////////////////////////


    private void init() {
        mManager = (WifiP2pManager) Objects.requireNonNull(getApplicationContext()).getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(getApplicationContext(), getMainLooper(), null);
        mReceiver = new WiFiDirectBroadCastReceiver(mManager, mChannel, this);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    private void listeners() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Search Device", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i) {
                Toast.makeText(getApplicationContext(), "No Device Found", Toast.LENGTH_LONG).show();
            }
        });

        WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
                final InetAddress groupOwnerAdress = wifiP2pInfo.groupOwnerAddress;

                if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
//                    connectionStatus.setText("Host");
//                    serverClass = new ServerClass();
//                    serverClass.start();
                } else if (wifiP2pInfo.groupFormed) {
//                    connectionStatus.setText("Client");
//                    clientClass = new ClientClass(groupOwnerAdress);
//                    clientClass.start();
                }
            }
        };
    }

    private void connect(final WifiP2pDevice device, final int port, final String path) {
        final WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                sendFile(device, port, path);
            }

            @Override
            public void onFailure(int i) {

            }
        });
    }

    public void sendFile(WifiP2pDevice device, int port, String path) {

        Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
        Log.d("SendFile", "Intent----------- " + path);
        Intent serviceIntent = new Intent(getApplicationContext(), FileTransferService.class);
        serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
        serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, path);
        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                device.deviceAddress);
        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, port);
        getApplicationContext().startService(serviceIntent);
    }

    private void connectNode() {
        Random random = new Random();
        portList.clear();
        for(WifiP2pDevice device : peers) {
            int port = random.nextInt(1000 + 1) + 8000;
            connect(device, port, Util.mHomeFilePath);
            portList.add(port);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Objects.requireNonNull(getApplicationContext()).registerReceiver(mReceiver, mIntentFilter);
    }

    public WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if (!peerList.getDeviceList().equals(peers)) {

                peers.clear();
                ActiveNodesFragment.mNodes.clear();
                Log.d("Listener", "=======================================================================================");
                peers.addAll(peerList.getDeviceList());
                int index = 0;
                for (WifiP2pDevice device : peerList.getDeviceList()) {
                    ActiveNodesFragment.mNodes.add(new ActiveNodesModel(device.deviceName, device.deviceAddress));
                }
//                ActiveNodesFragment.myAdapter.notifyDataSetChanged();
            }

            if (peers.size() == 0) {
                Toast.makeText(getApplicationContext(), "No Device Found", Toast.LENGTH_LONG).show();
            }

        }
    };

    public interface DeviceActionListener {
        void showDetails(WifiP2pDevice device);
        void cancelDisconnect();
        void connect(WifiP2pConfig config);
        void disconnect();
    }
}