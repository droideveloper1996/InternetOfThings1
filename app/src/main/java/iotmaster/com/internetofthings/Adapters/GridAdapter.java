package iotmaster.com.internetofthings.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import iotmaster.com.internetofthings.R;

/**
 * Created by Abhishek on 15/07/2017.
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    int array[] = new int[]{R.drawable.home,
            R.drawable.home_smart1,
            R.drawable.powered_by_google_light,
            R.drawable.plug};

    public GridAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(105, 105));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(array[position]);
        return imageView;
    }
}
