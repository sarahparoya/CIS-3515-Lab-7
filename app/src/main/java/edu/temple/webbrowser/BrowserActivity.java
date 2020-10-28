package edu.temple.webbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.WebMenuListener {
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment pageControl = PageControlFragment.newInstance("url");
        Fragment pageView = PageViewerFragment.newInstance("test", "test");
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.page_control, pageControl)
                .add(R.id.page_viewer_fragment, pageView)
                .commit();
    }

    @Override
    public void buttonPressed(String url) {
        PageViewerFragment fragment = new PageViewerFragment();
        fragment.defineUrl(url);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.browser, fragment)
                .addToBackStack("add")
                .commit();

   /* PageControlFragment pageControlFragment;
    PageViewerFragment pageViewerFragment;
    EditText urlText;
    ImageButton goButton;
    WebView browser;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Web Browser");

        PageControlFragment pageControlFragment = new PageControlFragment();
        PageViewerFragment pageViewerFragment = new PageViewerFragment();


     //   fragmentList = new ArrayList<WebViewFragment>();
     //   fragmentList.add(new WebViewFragment());

        urlText =  findViewById(R.id.editURL);
        goButton =  findViewById(R.id.btnGo);
        browser = findViewById(R.id.browser);


        FragmentManager FM = getSupportFragmentManager();

        FragmentTransaction FT = FM.beginTransaction();
        FT.add(R.id.page_control, pageControlFragment);
        FT.add(R.id.page_viewer_fragment, pageViewerFragment);
        FT.commit();


     //   browser.setWebViewClient(new WebViewClient());


       // Bundle bundle = new Bundle();
        //bundle.putString("urlText", "From PageControlFragment");

      //  PageViewerFragment.setArguments(bundle);

      /*  goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browser.loadUrl(urlText.getText().toString());
                browser.getSettings().getJavaScriptEnabled();
                browser.setWebViewClient(new WebViewClient());
            }
        });

    }*/
    }
}