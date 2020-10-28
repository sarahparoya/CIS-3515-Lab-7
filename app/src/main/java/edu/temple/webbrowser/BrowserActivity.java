package edu.temple.webbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.SendURLInterface, PageControlFragment.goBackInterface, PageControlFragment.goNextInterface, PageViewerFragment.SetURLInterface {
    static PageControlFragment PageControlFragment = new PageControlFragment();
    static PageViewerFragment PageViewerFragment = new PageViewerFragment();

    PageControlFragment pageControlFragment;
    PageViewerFragment pageViewerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Browser Activity");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        pageControlFragment = new PageControlFragment();
        pageViewerFragment = new PageViewerFragment();

        FragmentManager FM = getSupportFragmentManager();

        FragmentTransaction FT = FM.beginTransaction();
        FT.add(R.id.page_control, pageControlFragment);
        FT.add(R.id.page_viewer_fragment, pageViewerFragment);
        FT.commit();
    }

    public void SendURL(String URL){
        String reqStr = "https://";




        if(!URL.substring(0,reqStr.length()).equals(reqStr)){
            URL = reqStr.concat(URL);
        }

        pageViewerFragment.setURL(URL);

        pageControlFragment.setText(pageViewerFragment.getURL());


    }

    @Override
    public void SetURL() {

        pageControlFragment.setText(pageViewerFragment.getURL());
    }

    @Override
    public void goBack() {
        pageViewerFragment.goBack();
    }

    public void goNext() {
        pageViewerFragment.goNext();
    }
}