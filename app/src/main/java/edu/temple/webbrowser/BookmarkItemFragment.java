package edu.temple.webbrowser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class BookmarkItemFragment extends Fragment {

    TextView txtUrl;
    Button btnDelete;
    static String url;

    public BookmarkItemFragment() {
        // Required empty public constructor
    }

    public static BookmarkItemFragment newInstance(String url_) {
        BookmarkItemFragment fragment = new BookmarkItemFragment();

        Bundle args = new Bundle();
        args.putString("TheUrl", url_);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bun = getArguments();
        url = bun.getString("TheUrl");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bookmark_item, container, false);
        txtUrl = v.findViewById(R.id.txtUrl);
        txtUrl.setText(url);

        btnDelete = v.findViewById(R.id.btnDelete);
        return v;
    }
}