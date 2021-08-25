package com.sossolution.serviceonwayustad.Model_class;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import static android.provider.DocumentsContract.isDocumentUri;
import static com.facebook.internal.Utility.isContentUri;
import static com.facebook.internal.Utility.isFileUri;

public class Get_Real_Path
{
    private Context context;
    private Uri uri;


    public String getUriRealPath(Context ctx, Uri uri)
    {
        String ret = "";
        if(isAboveKitKat())
        {
            // Android sdk version number bigger than 19.
            ret = getUriRealPathAboveKitkat(ctx, uri);
        }else
        {
            // Android sdk version number smaller than 19.
            ret = getImageRealPath(context.getContentResolver(), uri, null);

        }
        return ret;
    }

    private boolean isAboveKitKat()
    {
        boolean ret = false;
        ret = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        return ret;

    }

    private String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause)
    {

        String ret = "";
        // Query the uri with condition.
        Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);
        if(cursor!=null)
        {
            boolean moveToFirst = cursor.moveToFirst();
            if(moveToFirst)
            {
                // Get columns name by uri type.
                String columnName = MediaStore.Images.Media.DATA;
                if( uri==MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Images.Media.DATA;
                }
                // Get column index.
                int imageColumnIndex = cursor.getColumnIndex(columnName);
                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex);
            }
        }
        return ret;
    }

    private String getUriRealPathAboveKitkat(Context ctx, Uri uri)
    {
        String ret = "";
        if(ctx != null && uri != null)
        {
             if(isFileUri(uri))
            {
                ret = uri.getPath();

            }else if(isDocumentUri(ctx, uri))
            {
                // Get uri related document id.
                String documentId = DocumentsContract.getDocumentId(uri);
                // Get uri authority.
                String uriAuthority = uri.getAuthority();
                if(isMediaDoc(uriAuthority))
                {
                    String idArr[] = documentId.split(":");
                    if(idArr.length == 2)
                    {
                        // First item is document type.
                        String docType = idArr[0];
                        // Second item is document real id.
                        String realDocId = idArr[1];
                        // Get content uri by document type.
                        Uri mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        if("image".equals(docType))
                        {
                            mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        }
                        // Get where clause with real document id.
                        String whereClause = MediaStore.Images.Media._ID + " = " + realDocId;
                        ret = getImageRealPath(context.getContentResolver(), mediaContentUri, whereClause);
                    }
                }else if(isExternalStoreDoc(uriAuthority))
                {
                    String idArr[] = documentId.split(":");
                    if(idArr.length == 2)
                    {
                        String type = idArr[0];
                        String realDocId = idArr[1];
                        if("primary".equalsIgnoreCase(type))
                        {
                            ret = Environment.getExternalStorageDirectory() + "/" + realDocId;

                        }
                    }
                }
            }
        }
        return ret;
    }

    private boolean isMediaDoc(String uriAuthority)
    {
        boolean ret = false;
        if("com.android.providers.media.documents/document/image".equals(uriAuthority))
        {
            ret = true;
        }
        return ret;
    }

    private boolean isExternalStoreDoc(String uriAuthority)
    {
        boolean ret = false;
        if("com.android.externalstorage.documents".equals(uriAuthority))
        {
            ret = true;
        }
        return ret;
    }


}
