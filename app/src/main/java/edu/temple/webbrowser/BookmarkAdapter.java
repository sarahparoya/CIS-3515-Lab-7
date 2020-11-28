package edu.temple.webbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookmarkAdapter extends BaseAdapter implements ListAdapter {



    Context context;
    ArrayList<String> bookmarkList;

    public BookmarkAdapter(Context context_, ArrayList<String> bookmarkList_){
        this.context = context_;
        bookmarkList = bookmarkList_;
    }

    @Override
    public int getCount() {
        return bookmarkList.size();
    }

    @Override
    public String getItem(int i) {
        return bookmarkList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public void addBookmark(String s){
        bookmarkList.add(s);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_bookmark_item, null);
        }

        final TextView txtUrl = view.findViewById(R.id.txtUrl);
        txtUrl.setText(getItem(position));

        ImageButton btnDelete = view.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                bookmarkList.remove(position);
                notifyDataSetChanged();
            }
        });

        txtUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PageControlFragment.SendURLInterface) context).SendURL((String) txtUrl.getText());
            }
        });

        return view;
    }
}