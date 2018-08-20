package com.instigatemobile.grapes.models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.instigatemobile.grapes.R;

public class ActiveNodesModel extends AppCompatActivity {

    private String mNickname;
    private String mIp;

    public ActiveNodesModel(String nickname, String ip) {
        this.mNickname = nickname;
        this.mIp = ip;
    }

    public String getmNickname() {
        return mNickname;
    }

    public void setmNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public String getmIp() {
        return mIp;
    }

    public void setmIp(String mIp) {
        this.mIp = mIp;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_nodes_model);
    }
}
