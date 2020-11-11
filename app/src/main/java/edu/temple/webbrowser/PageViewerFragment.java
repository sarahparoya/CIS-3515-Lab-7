package edu.temple.webbrowser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class PageViewerFragment extends Fragment {

    protected static int position;
    WebView browser;

    String reqStr = "https://";

    private Bundle webViewBundle;
    String url_ = "https://google.com";

    public PageViewerFragment() {
        // Required empty public constructor
    }

    public static PageViewerFragment newInstance(int position_) {
        PageViewerFragment fragment = new PageViewerFragment();
        position = position_;
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
            browser.loadUrl("https://www.google.com");
        } else {
            browser.restoreState(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        browser = v.findViewById(R.id.browser);
        browser.setWebViewClient(new WebViewClient());



        browser.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {

                ((PageViewerFragment.SetURLInterface) getActivity()).SetURL();
            }
        });


        return v;
    }

    public String boolString(boolean b){
        if(b) return "true";
        return "false";
    }

    public void goBack(){



        if(browser.canGoBack()){
            browser.goBack();
        }


    }

    public void goNext(){


        if(browser.canGoForward()){
            browser.goForward();
        }

    }

    public void setURL(String url) {



        browser.loadUrl(url);

    }

    public String getURL(){
        return browser.getUrl();
    }

    interface SetURLInterface {
        void SetURL();
    }

    interface resetUrlInterface{
        void resetUrl();
    }
}