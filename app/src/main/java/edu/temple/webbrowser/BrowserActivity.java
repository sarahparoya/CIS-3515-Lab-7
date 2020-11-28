package edu.temple.webbrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.SendURLInterface, PageControlFragment.goBackInterface, PageControlFragment.goNextInterface,
        BrowserControlFragment.AddPageInterface, PageControlFragment.setURLInterface, PagerFragment.getListInterface, PageListFragment.goToPageInterface,
        BrowserControlFragment.SwapFragsInterface, BrowserControlFragment.addBookmarkInterface{



    PageControlFragment PCFrag;
    PagerFragment PVFrag;
    BrowserControlFragment BCFrag;
    PageListFragment PLFrag;
    BookmarkFragment BMFrag;

    FrameLayout BookmarkContainer;


    ArrayList<PageViewerFragment> PVList;



    protected void onStop(){
        super.onStop();
        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        editor.putStringSet("bookmarks", BMFrag.getBookmarkSet());
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Browser Activity");

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);

        if(getSupportFragmentManager().findFragmentById(R.id.page_viewer) == null && getSupportFragmentManager().findFragmentById(R.id.page_control) == null){
            BookmarkContainer = findViewById(R.id.bookmark_view);
            PVList = new ArrayList<PageViewerFragment>();
            PVList.add(new PageViewerFragment());

//            Set<String> BMSet = prefs.getStringSet("bookmarks", null);

            BMFrag = new BookmarkFragment();
//            if(BMSet!=null && BMSet.size()>0){
////                for (String str : BMSet){
////                    BMFrag.addBookmark(str);
////                }
//            }


            PCFrag = new PageControlFragment();
            BCFrag = new BrowserControlFragment();

            PVFrag = new PagerFragment();

            FragmentManager FM = getSupportFragmentManager();

            FragmentTransaction FT = FM.beginTransaction();


            FT.add(R.id.page_viewer, PVFrag);
            FT.add(R.id.page_control, PCFrag);
            FT.add(R.id.browser_control, BCFrag);
            FT.add(R.id.bookmark_view, BMFrag);
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
            BMFrag = (BookmarkFragment) getSupportFragmentManager().findFragmentById(R.id.bookmark_view);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
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

    @Override
    public void swapFrags() {
        if(BookmarkContainer.getVisibility() == View.VISIBLE){
            BookmarkContainer.setVisibility(View.INVISIBLE);
        } else {
            BookmarkContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addBookmark() {
        String s = PVList.get(PVFrag.getCurrent()).getURL();
        BMFrag.addBookmark(s);
    }
}