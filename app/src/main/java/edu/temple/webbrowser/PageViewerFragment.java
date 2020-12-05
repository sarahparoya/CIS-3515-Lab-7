package edu.temple.webbrowser;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;


public class PageViewerFragment extends Fragment implements Parcelable {

    WebView browser;

    String reqStr = "https://";

    public Context activity;

    public PageViewerFragment() {
        // Required empty public constructor
    }

    public PageViewerFragment(Parcel in) {
        browser.restoreState(in.readBundle());
    }

    public static final Creator<PageViewerFragment> CREATOR = new Creator<PageViewerFragment>() {
        @Override
        public PageViewerFragment createFromParcel(Parcel in) {
            return new PageViewerFragment(in);
        }

        @Override
        public PageViewerFragment[] newArray(int size) {
            return new PageViewerFragment[size];
        }
    };

    public static PageViewerFragment newInstance() {
        PageViewerFragment fragment = new PageViewerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        browser.saveState(outState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            //browser.loadUrl(getArguments().getString("URL_"));
            browser.loadUrl("https://google.com");
        } else {
            browser.restoreState(savedInstanceState);
        }



    }

    public String getTitle(){
        return browser.getTitle();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.activity = context;

        Log.i("CONTEXT",  "context: " + this.activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        browser = v.findViewById(R.id.browser);
        browser.setWebViewClient(new WebViewClient());

        final Context act = this.activity;


        browser.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("CONTEXT",  "context: " + act);
                ((PageControlFragment.setURLInterface) act).setURL();
            }
        });


        return v;
    }

    public String boolString(boolean b){
        if(b) return "true";
        return "false";
    }

    public void goBack(){



        Log.i("Back",  "back");
        if(browser.canGoBack()){
            browser.goBack();
        }


    }

    public void goNext(){

        Log.i("Next",  "next");
        if(browser.canGoForward()){
            browser.goForward();
        }

    }

    public void setURL(String url) {
        Log.i("URL",  " url: " + url);
        Log.i("BROWSER",  " browser: " + browser);
        browser.loadUrl(url);
    }

    public String testBrowser(){
        return browser.getUrl();
    }

    public String getURL(){
        return browser.getUrl();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bun = new Bundle();

        browser.saveState(bun);

        parcel.writeBundle(bun);
    }
}