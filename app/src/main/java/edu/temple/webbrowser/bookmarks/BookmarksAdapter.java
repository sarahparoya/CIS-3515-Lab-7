package edu.temple.webbrowser.bookmarks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import edu.temple.webbrowser.R;

public class BookmarksAdapter extends BaseAdapter {

    Context context;
    BookmarkList bookmarks;
    LayoutInflater layoutInflater;

    /**
     * Generate views for bookmark list
     * @param context to inflate layout
     * @param bookmarks to be displayed
     */
    public BookmarksAdapter (Context context, BookmarkList bookmarks) {
        this.context = context;
        this.bookmarks = bookmarks;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bookmarks.getSize();
    }

    @Override
    public Object getItem(int position) {
        return bookmarks.getBookmark(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ConstraintLayout layout;
        TextView textView;
        ImageButton button;

        if (convertView instanceof ConstraintLayout)
            layout = (ConstraintLayout) convertView;
        else
            // Inflate prebuilt layout so as to reduce complexity of building layout in code
            layout = (ConstraintLayout) layoutInflater.inflate(R.layout.bookmark_adapter_layout, parent, false);

        textView = layout.findViewById(R.id.text1);
        button = layout.findViewById(R.id.deleteButton);

        textView.setText(shortenString(bookmarks.getBookmark(position).getTitle()));

        // Delete button will delete individual bookmark
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                (new AlertDialog.Builder(context))
                        .setMessage(String.format(context.getString(R.string.delete_bookmark_confirmation), bookmarks.getBookmark(position).getTitle()))
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bookmarks.deleteBookmark(bookmarks.getBookmark(position));
                                bookmarks.save(context);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        return layout;
    }

    /**
     * Shorten long titles
     * @param title to be shortened
     * @return shortened title if longer than specified number of characters
     */
    private String shortenString(String title) {
        if (title.length() >= 20)
            return title.substring(0, 17) + "...";
        else
            return title;
    }
}