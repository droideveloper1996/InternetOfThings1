package iotmaster.com.internetofthings.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Abhishek on 02/08/2017.
 */

public class AlamContract {


    public static final String SCHEME="content://";
    public static final String AUTHORITY="iotmaster.com.internetofthings.ALARM";
    public static final Uri BASE_URI=Uri.parse(SCHEME+AUTHORITY);
    public static final String PATH="alarm";

    public static class AlamEntry implements BaseColumns {

        public static final Uri CONTENT_URI= BASE_URI.buildUpon().appendPath(PATH).build();

        public static final String TABLE_NAME="alarm";
        public static final String TIME="time";
        public static final String ACTIVE="active";
        public static final String ACTION="action";
        public static final String STATUS="status";

        public static final String REPEAT="repeat";

        public static final String MONDAY="mon";
        public static final String TUESDAY="tue";
        public static final String WEDNESDAY="wed";
        public static final String THURSDAY="thu";
        public static final String FRIDAY="fri";
        public static final String SATURDAY="sat";
        public static final String SUNDAY="sun";







    }
}
