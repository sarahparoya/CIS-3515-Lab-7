package edu.temple.webbrowser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class BrowserControlFragment extends Fragment {

    ImageButton btnAdd;
    ImageButton btnBookmarks;
    ImageButton btnSave;

    public BrowserControlFragment() {
        // Required empty public constructor
    }


    public static BrowserControlFragment newInstance(String param1, String param2) {
        BrowserControlFragment fragment = new BrowserControlFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browser_control, container, false);

        btnAdd = v.findViewById(R.id.btnAdd);
        btnBookmarks = v.findViewById(R.id.btnBookmarks);
        btnSave = v.findViewById(R.id.btnSave);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BrowserControlFragment.AddPageInterface) getActivity()).addPage();
            }
        });

        btnBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BrowserControlFragment.SwapFragsInterface) getActivity()).swapFrags();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BrowserControlFragment.addBookmarkInterface) getActivity()).addBookmark();
            }
        });


        return v;
    }

    interface AddPageInterface {
        void addPage();
    }

    interface SwapFragsInterface{
        void swapFrags();
    }

    interface addBookmarkInterface{
        void addBookmark();
    }
}