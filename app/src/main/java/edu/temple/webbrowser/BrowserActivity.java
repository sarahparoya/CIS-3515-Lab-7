package edu.temple.webbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PageControlFragment pageControlFragment = new PageControlFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.page_control, pageControlFragment)
                .commit();




    }
}