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



    PageControlFragment PCFrag;
    PagerFragment PVFrag;
    BrowserControlFragment BCFrag;
    PageListFragment PLFrag;


    ArrayList<PageViewerFragment> PVList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Browser Activity");

        if(getSupportFragmentManager().findFragmentById(R.id.page_viewer) == null && getSupportFragmentManager().findFragmentById(R.id.page_control) == null){

            PVList = new ArrayList<PageViewerFragment>();
            PVList.add(new PageViewerFragment());


            PCFrag = new PageControlFragment();
            BCFrag = new BrowserControlFragment();

            PVFrag = new PagerFragment();

            FragmentManager FM = getSupportFragmentManager();

            FragmentTransaction FT = FM.beginTransaction();


            FT.add(R.id.page_viewer, PVFrag);
            FT.add(R.id.page_control, PCFrag);
            FT.add(R.id.browser_control, BCFrag);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                PLFrag = new PageListFragment();
                FT.add(R.id.page_list, PLFrag);
            }
            FT.commit();
        } else {
            PVList = savedInstanceState.getParcelableArrayList("TheList");
            Log.i("THE LIST SIZE", String.valueOf(PVList.size()));

            PCFrag = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control);
            BCFrag = (BrowserControlFragment) getSupportFragmentManager().findFragmentById(R.id.browser_control);
            PVFrag = (PagerFragment) getSupportFragmentManager().findFragmentById(R.id.page_viewer);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                Log.i("ACT>>>>>>>>>>", "ACT");
                if(PLFrag==null){
                    PLFrag = new PageListFragment();
                    FragmentManager FM = getSupportFragmentManager();

                    FragmentTransaction FT = FM.beginTransaction();
                    FT.add(R.id.page_list, PLFrag);
                    FT.commit();
                } else {
                    PLFrag = (PageListFragment) getSupportFragmentManager().findFragmentById(R.id.page_list);
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

        PVList.get(PVFrag.getCurrent()).setURL(URL);
        PVFrag.notifyChange();
        if(PLFrag!=null) PLFrag.notifyChange();

        PCFrag.setText(PVList.get(PVFrag.getCurrent()).getURL());
    }

    @Override
    public void goBack() {
        PVList.get(PVFrag.getCurrent()).goBack();
        if(PLFrag!=null) PLFrag.notifyChange();
        setTitle(PVList.get(PVFrag.getCurrent()).getTitle());
    }

    public void goNext() {
        PVList.get(PVFrag.getCurrent()).goNext();
        if(PLFrag!=null) PLFrag.notifyChange();
        setTitle(PVList.get(PVFrag.getCurrent()).getTitle());
    }


    @Override
    public void addPage() {
        PVList.add(new PageViewerFragment());
        PVFrag.notifyChange();
        if(PLFrag!=null) PLFrag.notifyChange();
        PVFrag.goToPage(PVList.size()-1);
        setTitle(PVList.get(PVFrag.getCurrent()).getTitle());
    }

    public void goToPage(int i){
        PVFrag.goToPage(i);
        if(PLFrag!=null) PLFrag.notifyChange();
        setTitle(PVList.get(PVFrag.getCurrent()).getTitle());
    }

    @Override
    public void setURL() {
        PCFrag.setText(PVList.get(PVFrag.getCurrent()).getURL());
        if(PLFrag!=null) PLFrag.notifyChange();
        setTitle(PVList.get(PVFrag.getCurrent()).getTitle());
    }


    @Override
    public ArrayList<PageViewerFragment> getList() {
        return PVList;
    }
}