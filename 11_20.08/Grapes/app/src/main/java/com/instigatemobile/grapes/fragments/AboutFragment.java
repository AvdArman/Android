package com.instigatemobile.grapes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.instigatemobile.grapes.adapters.MembersAdapter;
import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.models.TeamMember;

import java.util.LinkedList;
import java.util.List;

public class AboutFragment extends Fragment {

    private List<TeamMember> mMemberList = new LinkedList<>();

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("About");
        fillMemberList();
        final View view = inflater.inflate(R.layout.fragment_about, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        final MembersAdapter adapter = new MembersAdapter(mMemberList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void fillMemberList() {
        TeamMember member = new TeamMember(R.drawable.ic_arman, "Arman");
        mMemberList.add(member);
        member = new TeamMember(R.drawable.ic_gevorg, "Gevorg");
        mMemberList.add(member);
        member = new TeamMember(R.drawable.ic_astghik, "Astghik");
        mMemberList.add(member);
        member = new TeamMember(R.drawable.ic_anush, "Anush");
        mMemberList.add(member);
        member = new TeamMember(R.drawable.ic_karen, "Karen");
        mMemberList.add(member);
        member = new TeamMember(R.drawable.ic_smbat, "Smbat");
        mMemberList.add(member);
        member = new TeamMember(R.drawable.ic_ananimouse, "");
        mMemberList.add(member);
        member = new TeamMember(R.drawable.ic_taron, "Taron");
        mMemberList.add(member);
    }
}
