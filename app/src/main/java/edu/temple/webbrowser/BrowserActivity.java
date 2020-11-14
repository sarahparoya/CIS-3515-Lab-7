package edu.temple.webbrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.SendURLInterface, PageControlFragment.goBackInterface, PageControlFragment.goNextInterface, BrowserControlFragment.AddPageInterface, PageControlFragment.setURLInterface, PagerFragment.getListInterface, PageListFragment.goToPageInterface {



    PageControlFragment pageControlFragment;
    PagerFragment pagerFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;


    ArrayList<PageViewerFragment> PVList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Browser Activity");

        if(getSupportFragmentManager().findFragmentById(R.id.page_viewer) == null && getSupportFragmentManager().findFragmentById(R.id.page_control) == null){

            PVList = new ArrayList<PageViewerFragment>();
            PVList.add(new PageViewerFragment());


            pageControlFragment = new PageControlFragment();
            browserControlFragment = new BrowserControlFragment();

            pagerFragment = new PagerFragment();

            FragmentManager FM = getSupportFragmentManager();

            FragmentTransaction FT = FM.beginTransaction();


            FT.add(R.id.page_viewer, pagerFragment);
            FT.add(R.id.page_control, pageControlFragment);
            FT.add(R.id.browser_control, browserControlFragment);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                pageListFragment = new PageListFragment();
                FT.add(R.id.page_list, pageListFragment);
            }
            FT.commit();
        } else {
            PVList = savedInstanceState.getParcelableArrayList("TheList");
            Log.i("THE LIST SIZE", String.valueOf(PVList.size()));

            pageControlFragment = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control);
            browserControlFragment = (BrowserControlFragment) getSupportFragmentManager().findFragmentById(R.id.browser_control);
            pagerFragment = (PagerFragment) getSupportFragmentManager().findFragmentById(R.id.page_viewer);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                Log.i("ACT>>>>>>>>>>", "ACT");
                if(pageListFragment==null){
                    pageListFragment = new PageListFragment();
                    FragmentManager FM = getSupportFragmentManager();

                    FragmentTransaction FT = FM.beginTransaction();
                    FT.add(R.id.page_list, pageListFragment);
                    FT.commit();
                } else {
                    pageListFragment = (PageListFragment) getSupportFragmentManager().findFragmentById(R.id.page_list);
                }

            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("TheList", PVList);
    }

    public void SendURL(String URL){
        String reqStr = "https://";
        if(!URL.substring(0,reqStr.length()).equals(reqStr)){
            URL = reqStr.concat(URL);
        }

        PVList.get(pagerFragment.getCurrent()).setURL(URL);
        pagerFragment.notifyChange();
        if(pageListFragment!=null)
            pageListFragment.notifyChange();

        pageControlFragment.setText(PVList.get(pagerFragment.getCurrent()).getURL());
    }

    @Override
    public void goBack() {
        PVList.get(pagerFragment.getCurrent()).goBack();
        if(pageListFragment!=null)
            pageListFragment.notifyChange();
        setTitle(PVList.get(pagerFragment.getCurrent()).getTitle());
    }

    public void goNext() {
        PVList.get(pagerFragment.getCurrent()).goNext();
        if(pageListFragment!=null)
            pageListFragment.notifyChange();
        setTitle(PVList.get(pagerFragment.getCurrent()).getTitle());
    }


    @Override
    public void addPage() {
        PVList.add(new PageViewerFragment());
        pagerFragment.notifyChange();
        if(pageListFragment!=null)
            pageListFragment.notifyChange();
        pagerFragment.goToPage(PVList.size()-1);
        setTitle(PVList.get(pagerFragment.getCurrent()).getTitle());
    }

    public void goToPage(int i){
        pagerFragment.goToPage(i);
        if(pageListFragment!=null) pageListFragment.notifyChange();
        setTitle(PVList.get(pagerFragment.getCurrent()).getTitle());
    }

    @Override
    public void setURL() {
        pageControlFragment.setText(PVList.get(pagerFragment.getCurrent()).getURL());
        if(pageListFragment!=null)
            pageListFragment.notifyChange();
        setTitle(PVList.get(pagerFragment.getCurrent()).getTitle());
    }


    @Override
    public ArrayList<PageViewerFragment> getList() {
        return PVList;
    }
}