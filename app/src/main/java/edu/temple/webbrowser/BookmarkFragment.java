package edu.temple.webbrowser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class BookmarkFragment extends Fragment {

    ListView listBookmarks;
    BookmarkAdapter TheAdapter;
    ArrayList<String> bookmarkList;
    Button close;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    public void addBookmark(String s){
        bookmarkList.add(s);
        TheAdapter.notifyDataSetChanged();
    }

    public static BookmarkFragment newInstance() {
        BookmarkFragment fragment = new BookmarkFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Set<String> getBookmarkSet(){
        return new HashSet<String>(bookmarkList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bookmark, container, false);
        v.setClickable(false);
        v.setFocusable(false);
        bookmarkList = new ArrayList<String>();

        listBookmarks = v.findViewById(R.id.listBookmarks);
        TheAdapter = new BookmarkAdapter(getContext(), bookmarkList);
        listBookmarks.setAdapter(TheAdapter);
        close = v.findViewById(R.id.buttonClose);
        close.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();

            }
        });

//        listPages.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ((PageListFragment.goToPageInterface) getActivity()).goToPage(i);
//            }
//        });


        return v;
    }

    private void closeFragment() {
        getFragmentManager().beginTransaction().remove(BookmarkFragment.this).commit();



    }

}