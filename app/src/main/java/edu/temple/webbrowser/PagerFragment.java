package edu.temple.webbrowser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PagerFragment extends Fragment {



    PageAdapter mAdapter;

    ViewPager mPager;

    View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Watch for button clicks.
//        Button button = (Button)findViewById(R.id.goto_first);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPager.setCurrentItem(0);
//            }
//        });
//        button = (Button)findViewById(R.id.goto_last);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPager.setCurrentItem(mPager.getCurrentItem()+1);
//            }
//        });
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pager, container, false);

        mPager = v.findViewById(R.id.pager);
        mAdapter = new PageAdapter(getFragmentManager());
        mPager.setAdapter(mAdapter);

        return v;
    }

    public void setURL(String url) {
    }

    public String getURL() {
        return null;
    }

    public void goBack() {
    }

    public void goNext() {
    }
}