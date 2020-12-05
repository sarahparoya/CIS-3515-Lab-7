package edu.temple.webbrowser.bookmarks;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 * A collection of bookmarks that can be saved and read from disk
 */
public class BookmarkList {
    private ArrayList<Bookmark> bookmarks;
    private static final String BOOKMARKS_FILE = "browser_bookmarks";

    private BookmarkList (Context context) {

        // Read saved bookmark list if one exists,
        // otherwise initialize new, empty list
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(BOOKMARKS_FILE);
            ObjectInputStream is = new ObjectInputStream(fis);
            bookmarks = (ArrayList<Bookmark>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            Log.d("No bookmarks found", "initializing bookmark list");
        }
        if (bookmarks == null)
            bookmarks = new ArrayList<>();
    }

    public static BookmarkList getInstance(Context context) {
        return new BookmarkList(context);
    }

    public void addBookmark(Bookmark bookmark) {
        for (Bookmark checkBookmark : bookmarks) {
            if (checkBookmark.equals(bookmark)) {
                // Update title if URL matches
                checkBookmark.setTitle(bookmark.getTitle());
                return;
            }
        }
        bookmarks.add(bookmark);
    }

    public Bookmark getBookmark (int position) {
        return bookmarks.get(position);
    }

    public int getSize() {
        return bookmarks.size();
    }

    public void deleteBookmark(Bookmark bookmark) {
        bookmarks.remove(bookmark);
    }

    // Save the bookmark list to disk
    public void save (final Context context) {
        // Threads are good for file operations
        new Thread() {
            @Override
            public void run() {
                FileOutputStream fos = null;
                try {
                    fos = context.openFileOutput(BOOKMARKS_FILE, Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(bookmarks);
                    os.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}