package edu.temple.webbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Configuration;
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

public class BrowserActivity extends AppCompatActivity {


    FragmentManager fm;
    boolean phoneMode;

    BrowserControlFragment browserControlFragment;
    PageViewerFragment pageViewerFragment;
    PageControlFragment pageControlFragment;
    PagerFragment pagerFragment;
    PageListFragment pageListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fm = getSupportFragmentManager();
        browserControlFragment = new BrowserControlFragment();
        pageControlFragment = new PageControlFragment();
        pageListFragment = new PageListFragment();
        pageViewerFragment = new PageViewerFragment();
        pagerFragment = new PagerFragment();

        if(getSupportFragmentManager().findFragmentById(R.id.page_viewer) == null && getSupportFragmentManager().findFragmentById(R.id.page_control) == null){

            pagerFragment = new PagerFragment();
            pageControlFragment = new PageControlFragment();
            browserControlFragment = new BrowserControlFragment();
            pageListFragment = new PageListFragment();

            FragmentManager FM = getSupportFragmentManager();

            FragmentTransaction FT = FM.beginTransaction();

            FT.add(R.id.page_viewer, pagerFragment);
            FT.add(R.id.page_control, pageControlFragment);
            FT.add(R.id.browser_control, browserControlFragment);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) FT.add(R.id.page_list, pageListFragment);
            FT.commit();
        } else {
            pagerFragment = (PagerFragment) getSupportFragmentManager().findFragmentById(R.id.page_viewer);
            pageControlFragment = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control);
            browserControlFragment = (BrowserControlFragment) getSupportFragmentManager().findFragmentById(R.id.browser_control);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) pageListFragment = (PageListFragment) getSupportFragmentManager().findFragmentById(R.id.page_list);
        }
    }

      /* if(phoneMode)
        {
            fm.beginTransaction()
                    .add(R.id.page_list,pageListFragment)
                    .commit();
        }*/

}
