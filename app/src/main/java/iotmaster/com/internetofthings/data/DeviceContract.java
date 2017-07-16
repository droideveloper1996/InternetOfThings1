package iotmaster.com.internetofthings.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Abhishek on 13/07/2017.
 */

public class DeviceContract {

    /**
     * This App can Control up to 5 device at a time
     * Device has unique_key which is major component to control device status;
     * relay is switch which is on/off in device
     * Device can have maximum of 5 relays in it. Table is created accordingly
     *
     * eg. User has 5 devices so there will be 5 table each table having 5 relay_status column
     * so there will be 5 unique_keys.
     *
     * Table Looks like this..
     *
     *      |   id      |   unique_key  |   device_name | relay1    |   relay2  |   relay3  |     relay4  |    relay5      |
     *
     *          1            AIza6737       Living Room     0              1          1             0             1
     **/

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "iotmaster.com.internetofthings";
    public static final Uri BASE_URI = Uri.parse(SCHEME + AUTHORITY);
    public static final String PATH = "devices";


    public static class DeviceEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        public static final String TABLE_NAME = "my_devices";
        //registered username;
        public static final String USERNAME = "username";

        public static  final String UNIQUE_KEY="unique_key";

        public static final String DEVICE_NAME="device_name";



        public static final String MOBILE = "mobile";


        public static final String RELAY1 = "relay1";
        public static final String RELAY2 = "relay2";
        public static final String RELAY3 = "relay3";
        public static final String RELAY4 = "relay4";
        public static final String RELAY5 = "relay5";


    }

}
