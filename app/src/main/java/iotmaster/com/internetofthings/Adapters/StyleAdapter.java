package iotmaster.com.internetofthings.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import iotmaster.com.internetofthings.R;

/**
 * Created by Abhishek on 25/07/2017.
 */


public class StyleAdapter extends RecyclerView.Adapter<StyleAdapter.ViewHolder> {
    private ArrayList<String> mData;
    private Context mContext;
    int lastPosition = -1;
    int imageList[] = {R.drawable.blue, R.drawable.bulb, R.drawable.red, R.drawable.off, R.drawable.white, R.drawable.yellow, R.drawable.cfl100, R.drawable.cfl200};
    int index = 0;
    public final ListItemClickListener listItemClickListner;

    public StyleAdapter(Context context, ArrayList<String> mData, ListItemClickListener listItemClickListner) {
        this.mData = mData;
        this.listItemClickListner = listItemClickListner;
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_item, parent, false);
        StyleAdapter.ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(StyleAdapter.ViewHolder holder, final int position) {

        holder.mTextView.setText(mData.get(position));
        setAnimation(holder.itemView, position);
        holder.btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Clicked on Button "+position,Toast.LENGTH_SHORT).show();
            }
        });
        Random random = new Random();
        int index = random.nextInt(8);
        holder.image.setImageResource(imageList[index]);
        ++index;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        } else {
            if (position < lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_out_right);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

    }

    public interface ListItemClickListener {
        void onClick(int index);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        TextView mTextView;
        ImageView image;
        ImageView btn;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            btn=(ImageView)itemView.findViewById(R.id.my_switch);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getPosition();
            listItemClickListner.onClick(clickedPosition);
        }
    }
}
