package org.nus.trailblaze.adapters;

import android.content.Intent;
import android.os.Build;

/**
 * Created by liu.cao on 26/3/2018.
 */

public class IntentHelper {

    public static Intent SetIntentType(String type,Intent intent) {
        String[] mimeTypes=getMimeTypes(type);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0)
            {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        }
        else
        {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes)
            {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        return  intent;
    }

    public  static String[] getMimeTypes(String  type)
    {
        String[] mimeTypes;
        switch (type) {
            case "document":
                mimeTypes = new String[]{"text/plain", "application/pdf"};
                break;
            case "image":
                mimeTypes = new String[]{"image/*"};
                break;
            case "audio/video":
                mimeTypes = new String[]{"audio/*", "video/mp4"};
                break;
            default:
                mimeTypes = new String[]{""};
                break;
        }
        return mimeTypes;
    }

}
