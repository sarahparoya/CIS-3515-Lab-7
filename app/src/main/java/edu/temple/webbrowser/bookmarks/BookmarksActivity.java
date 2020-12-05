package edu.temple.webbrowser.bookmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.temple.webbrowser.R;

public class BookmarksActivity extends AppCompatActivity {

    public static final String BOOKMARK_KEY = "bookmark";

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        BookmarkList bookmarks = BookmarkList.getInstance(this);

        listView = findViewById(R.id.listView);
        listView.setAdapter(new BookmarksAdapter(this, bookmarks));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Inform calling activity of selected bookmark
                Intent bookmarkIntent = new Intent();
                bookmarkIntent.putExtra(BOOKMARK_KEY, (Bookmark) parent.getItemAtPosition(position));
                setResult(RESULT_OK, bookmarkIntent);
                finish();
            }
        });

        findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}