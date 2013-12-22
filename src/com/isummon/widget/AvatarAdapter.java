package com.isummon.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.isummon.R;

import java.util.ArrayList;

/**
 * Created by horzwxy on 12/22/13.
 */
public class AvatarAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> ids;

    public AvatarAdapter(Context context, ArrayList<Integer> ids) {
        this.context = context;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return ids.size();
    }

    @Override
    public Integer getItem(int position) {
        return ids.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ids.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = new ImageView(context);
            float width = context.getResources().getDimension(R.dimen.avatar_main_width);
            float height = context.getResources().getDimension(R.dimen.avatar_main_height);
            convertView.setLayoutParams(
                    new GridView.LayoutParams((int) (width + 0.5), (int) (height + 0.5)));
        }
        ((ImageView)convertView).setImageResource(
                getItem(position)
        );
        return convertView;
    }
}
