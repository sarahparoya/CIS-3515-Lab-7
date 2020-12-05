package edu.temple.webbrowser.bookmarks;

import java.io.Serializable;
import java.util.Objects;

/**
 * A class to represent the title and URL of a bookmark
 */
public class Bookmark implements Serializable {
    private String title, url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public Bookmark(String title, String url) {
        this.title = title;
        this.url = url;
    }

    /**
     * Overridden equals() method to determine if two urls are the same
     * @param o bookmark to compare
     * @return true if URLs are the same, regardless of title
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bookmark)) return false;
        Bookmark bookmark = (Bookmark) o;
        return Objects.equals(url, bookmark.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }
}