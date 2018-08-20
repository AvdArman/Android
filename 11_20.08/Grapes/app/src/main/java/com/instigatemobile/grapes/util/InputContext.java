package com.instigatemobile.grapes.util;

import android.annotation.SuppressLint;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.instigatemobile.grapes.fragments.ActiveNodesFragment.TAG;
import static com.instigatemobile.grapes.util.Util.copyFile;


// Make service., or thrade...........

public class InputContext implements WifiP2pManager.ConnectionInfoListener {
    private WifiP2pInfo info;
    List<Integer> portList = new ArrayList<>();
    static int port;

    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
        this.info = info;
//        Toast.makeText(getContext(), "begean transfer ======", Toast.LENGTH_SHORT).show();

        if (info.groupFormed && info.isGroupOwner) {
            new FileServerAsyncTask().execute();
        } else if (info.groupFormed) {
            // The other device acts as the client. In this case, we enable the
            // get file button.

        }

        // hide the connect button
    }

    public static class FileServerAsyncTask extends AsyncTask<Void, Void, String> {


        @SuppressLint("StaticFieldLeak")

        public FileServerAsyncTask() {
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Random random = new Random();
                port = random.nextInt(1000 + 1) + 8000;
                ServerSocket serverSocket = new ServerSocket(port);
                Log.d(TAG, "Server: Socket opened");
                Socket client = serverSocket.accept();
                Log.d(TAG, "Server: connection done");
                final File f = new File(Environment.getExternalStorageDirectory() + "/Grapes");  // writh in filder ==================

                File dirs = new File(f.getParent());
                if (!dirs.exists())
                    dirs.mkdirs();
                f.createNewFile();

                Log.d(TAG, "server: copying files " + f.toString());
                InputStream inputstream = client.getInputStream();
                copyFile(inputstream, new FileOutputStream(f));
                serverSocket.close();
                return f.getAbsolutePath();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {


            if (result != null) {
//                get mac adress from info
//        if (result is json) {save file , update list} else {get string, send file at path(String)}
            }

        }

        @Override
        protected void onPreExecute() {
        }

    }
}
