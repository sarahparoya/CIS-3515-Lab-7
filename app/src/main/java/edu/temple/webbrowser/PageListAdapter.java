package edu.temple.webbrowser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PageListAdapter extends BaseAdapter implements ListAdapter {

    Context context;
    ArrayList<PageViewerFragment> PVList;


    public PageListAdapter(Context context, ArrayList<PageViewerFragment> PVList_) {
        this.context = context;
        PVList = PVList_;
    }


    @Override
    public int getCount() {
        return PVList.size();
    }

    @Override
    public Object getItem(int i) {
        return PVList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(context);
        } else {
            textView = (TextView) convertView;
        }

        textView.setText(PVList.get(position).getTitle());
        textView.setPadding(10,0,0,0);

        return textView;
    }

}