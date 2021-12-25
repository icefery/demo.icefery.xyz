package xyz.icefery.demo.cp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import xyz.icefery.demo.util.DemoDBHelper;

public class GlobalContentProvider extends ContentProvider {
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI("xyz.icefery.demo.cp.GlobalContentProvider", "student", 1);
    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        DemoDBHelper demoDBHelper = new DemoDBHelper(super.getContext());
        this.db = demoDBHelper.getWritableDatabase();
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (URI_MATCHER.match(uri)) {
            case 1:
                long id = this.db.insert("student", null, values);
                return ContentUris.withAppendedId(uri, id);
            default:
                return uri;
        }

    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (URI_MATCHER.match(uri)) {
            case 1:
                return this.db.query("student", projection, selection, selectionArgs, null, null, sortOrder);
            default:
                return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (URI_MATCHER.match(uri)) {
            case 1:
                return this.db.update("student", values, selection, selectionArgs);
            default:
                return -1;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (URI_MATCHER.match(uri)) {
            case 1:
                return this.db.delete("student", selection, selectionArgs);
            default:
                return -1;
        }

    }
}