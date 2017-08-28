package com.whw.phoneinterception.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whw.phoneinterception.R;
import com.whw.phoneinterception.adapter.RecordAdapter;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {

    @Bind(R.id.recycleView)
    RecyclerView recyclerView;
    RecordAdapter recordAdapter;
    List<String> data = new ArrayList<>();
    public RecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        data.add("1");
        data.add("1");
        recordAdapter = new RecordAdapter(data,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recordAdapter);
    }
}
