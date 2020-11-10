package edu.temple.webbrowser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;


public class PageViewerFragment extends Fragment {

    WebView WebBrowser;

    //hehehe

    String reqStr = "https://";

    ArrayList<String> urlStack = new ArrayList<String>();

    int pageIndex = -1;

    boolean clear = false;

    public PageViewerFragment() {

    }

    public static PageViewerFragment newInstance(String param1, String param2) {
        PageViewerFragment fragment = new PageViewerFragment();

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
        View v = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        WebBrowser = v.findViewById(R.id.browser);

        WebBrowser.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                if(!clear && pageIndex!=urlStack.size()-1){
                    for(int i=pageIndex; i<urlStack.size(); i++){
                        urlStack.remove(i);
                    }
                }
                ((PageViewerFragment.SetURLInterface) getActivity()).SetURL();

                if(!clear)pageIndex++;
                if(!clear)urlStack.add(url);
                Toast toast = Toast.makeText(view.getContext(), boolString(clear) + ", " + urlStack.size(), Toast.LENGTH_LONG);
                toast.show();
                clear = false;
            }
        });


        return v;
    }

    public String boolString(boolean b){
        if(b) return "true";
        return "false";
    }

    public void goBack(){

        if(pageIndex != 0){
            clear = true;
            WebBrowser.loadUrl(urlStack.get(pageIndex-1));
            pageIndex--;
        }


    }

    public void goNext(){

        if(pageIndex != urlStack.size()-1){
            clear= true;
            WebBrowser.loadUrl(urlStack.get(pageIndex+1));
            pageIndex++;
        }

    }

    public void setURL(String url) {



        WebBrowser.loadUrl(url);

    }

    public String getURL(){
        return WebBrowser.getUrl();
    }

    interface SetURLInterface {
        void SetURL();
    }
}