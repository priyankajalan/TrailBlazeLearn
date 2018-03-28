package org.nus.trailblaze.adapters;

import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import org.nus.trailblaze.models.File;

/**
 * Created by liu.cao on 26/3/2018.
 */

public class FileHelper {
    private Uri uri;
    private Cursor cursor;
    public FileHelper(Uri uri,Cursor cursor)
    {
        this.uri=uri;
        this.cursor=cursor;
    }
    public void SetFileProperty(File mFile)
    {
        int name_index=cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int size_Index = cursor.getColumnIndex(OpenableColumns.SIZE);
        cursor.moveToFirst();
        mFile.setName( cursor.getString (name_index));
        mFile.setSize( cursor.getFloat(size_Index) );
    }

}
