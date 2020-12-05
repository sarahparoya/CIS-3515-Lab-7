package edu.temple.webbrowser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import edu.temple.webbrowser.bookmarks.Bookmark;
import edu.temple.webbrowser.bookmarks.BookmarkList;
import edu.temple.webbrowser.bookmarks.BookmarksActivity;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.PageControlInterface, PageViewerFragment.PageViewerInterface, BrowserControlFragment.BrowserControlInterface, PagerFragment.PagerInterface, PageListFragment.PageListInterface {

    private final int BOOKMARKS_REQUEST_CODE = 1;

    FragmentManager fm;

    private final String PAGES_KEY = "pages";

    PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;
    PagerFragment pagerFragment;

    ArrayList<PageViewerFragment> pages;

    boolean listMode;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            String shareString = pagerFragment.getCurrentUrl();
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareString);
            startActivity(Intent.createChooser(intent, "share the current url"));
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String action = intent.getAction();
        Set<String> Category = intent.getCategories();
        Uri data = intent.getData();



        if (savedInstanceState != null)
            pages = (ArrayList) savedInstanceState.getSerializable(PAGES_KEY);
        else
            pages = new ArrayList<>();

        if(Intent.ACTION_VIEW.equals(action) && data!=null){
            pages.add(PageViewerFragment.newInstance(data.toString()));
        }

        fm = getSupportFragmentManager();

        listMode = findViewById(R.id.page_list) != null;

        Fragment tmpFragment;

        // If PageControlFragment already added (activity restarted) then hold reference
        // otherwise add new fragment. Only one instance of fragment is ever present
        if ((tmpFragment = fm.findFragmentById(R.id.page_control)) instanceof PageControlFragment)
            pageControlFragment = (PageControlFragment) tmpFragment;
        else {
            pageControlFragment = new PageControlFragment();
            fm.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();
        }

        // If BrowserFragment already added (activity restarted) then hold reference
        // otherwise add new fragment. Only one instance of fragment is ever present
        if ((tmpFragment = fm.findFragmentById(R.id.browser_control)) instanceof BrowserControlFragment)
            browserControlFragment = (BrowserControlFragment) tmpFragment;
        else {
            browserControlFragment = new BrowserControlFragment();
            fm.beginTransaction()
                    .add(R.id.browser_control, browserControlFragment)
                    .commit();
        }

        // If PagerFragment already added (activity restarted) then hold reference
        // otherwise add new fragment. Only one instance of fragment is ever present
        if ((tmpFragment = fm.findFragmentById(R.id.page_viewer)) instanceof PagerFragment)
            pagerFragment = (PagerFragment) tmpFragment;
        else {
            pagerFragment = PagerFragment.newInstance(pages);
            fm.beginTransaction()
                    .add(R.id.page_viewer, pagerFragment)
                    .commit();
        }


        // If fragment already added (activity restarted) then hold reference
        // otherwise add new fragment IF container available. Only one instance
        // of fragment is ever present
        if (listMode) {
            if ((tmpFragment = fm.findFragmentById(R.id.page_list)) instanceof PageListFragment)
                pageListFragment = (PageListFragment) tmpFragment;
            else {
                pageListFragment = PageListFragment.newInstance(pages);
                fm.beginTransaction()
                        .add(R.id.page_list, pageListFragment)
                        .commit();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Clear the url bar and activity title
     */
    private void clearIdentifiers() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");
        pageControlFragment.updateUrl("");
    }

    // Notify all observers of collections
    private void notifyWebsitesChanged() {
        pagerFragment.notifyWebsitesChanged();
        if (listMode)
            pageListFragment.notifyWebsitesChanged();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save list of open pages for activity restart
        outState.putSerializable(PAGES_KEY, pages);
    }

    /**
     * Update WebPage whenever PageControlFragment sends new Url
     * Create new page first if none exists
     * Alternatively, you can create an empty page when the activity first loads
     * @param url to load
     */
    @Override
    public void go(String url) {
        if (pages.size() > 0)
            pagerFragment.go(url);
        else {
            pages.add(PageViewerFragment.newInstance(url));
            notifyWebsitesChanged();
            pagerFragment.showPage(pages.size() - 1);
        }

    }

    /**
     * Go back to previous page when user presses Back in PageControlFragment
     */
    @Override
    public void back() {
        pagerFragment.back();
    }

    /**
     * Go forward to next page when user presses Forward in PageControlFragment
     */
    @Override
    public void forward() {
        pagerFragment.forward();
    }

    /**
     * Update displayed Url in PageControlFragment when Webpage Url changes
     * only if it is the currently displayed page, and not another page
     * @param url to display
     */
    @Override
    public void updateUrl(String url) {
        if (url != null && url.equals(pagerFragment.getCurrentUrl())) {
            pageControlFragment.updateUrl(url);

            // Update the ListView in the PageListFragment - results in updated titles
            notifyWebsitesChanged();
        }
    }

    /**
     * Update displayed page title in activity when Webpage Url changes
     * only if it is the currently displayed page, and not another page
     * @param title to display
     */
    @Override
    public void updateTitle(String title) {
        if (title != null && title.equals(pagerFragment.getCurrentTitle()) && getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);

        // Results in the ListView in PageListFragment being updated
        notifyWebsitesChanged();
    }

    /**
     * Add a new page/fragment to the list and display it
     */
    @Override
    public void newPage() {
        // Add page to list
        pages.add(new PageViewerFragment());
        // Update all necessary views
        notifyWebsitesChanged();
        // Display the newly created page
        pagerFragment.showPage(pages.size() - 1);
        // Clear the displayed URL in PageControlFragment and title in the activity
        clearIdentifiers();
    }

    /**
     * Add page to bookmarks
     */
    @Override
    public void newBookmark() {
        String title = pagerFragment.getCurrentTitle(),
                url = pagerFragment.getCurrentUrl();
        if (!title.isEmpty() && !url.isEmpty()) {
            BookmarkList bookmarks = BookmarkList.getInstance(this);
            bookmarks.addBookmark(new Bookmark(pagerFragment.getCurrentTitle(), pagerFragment.getCurrentUrl()));
            bookmarks.save(this);
            Toast.makeText(this, getString(R.string.bookmark_saved_message), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show bookmarks dialog
     */
    @Override
    public void showBookmarks() {
        startActivityForResult(new Intent(this, BookmarksActivity.class), BOOKMARKS_REQUEST_CODE);
    }

    /**
     * Display requested page in the PagerFragment
     * @param position of page to display
     */
    @Override
    public void pageSelected(int position) {
        pagerFragment.showPage(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Open URL selected from Bookmark
        if (requestCode == BOOKMARKS_REQUEST_CODE && resultCode == RESULT_OK && data != null)
            go(((Bookmark) data.getSerializableExtra(BookmarksActivity.BOOKMARK_KEY)).getUrl());
    }
}