package iotmaster.com.internetofthings.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Abhishek on 18/07/2017.
 */

public class LocationContract {

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "iotmaster.com.internetofthings2";

    public static final String PATH = "location";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static class LocationEntry implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String TABLE_NAME = "location";
        public static final String PLACE_ID = "place_id";
        public static final String PLACE_NAME = "place_name";
        public static final String PLACE_LATITUTE = "latitude";
        public static final String PLACE_LONGITUTE = "longitude";

    }
}
