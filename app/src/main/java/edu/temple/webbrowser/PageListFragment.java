package edu.temple.webbrowser;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class PageListFragment extends Fragment {

    ListView listView;
    private PageListAdapter mAdapter;

    public PageListFragment() {
        // Required empty public constructor
    }

    public static PageListFragment newInstance() {
        PageListFragment fragment = new PageListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v =inflater.inflate(R.layout.fragment_page_list, container, false);

        listView = v.findViewById(R.id.pageListView);
        mAdapter = new PageListAdapter(getContext(), ((PagerFragment.getListInterface) getActivity()).getList());
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((PageListFragment.goToPageInterface) getActivity()).goToPage(i);
            }
        });

        Log.i("THE ADAPTER", ""+mAdapter);

        return v;
    }

    interface goToPageInterface{
        void goToPage(int i);
    }

    public void notifyChange(){
        if(mAdapter!=null) mAdapter.notifyDataSetChanged();
    }
}