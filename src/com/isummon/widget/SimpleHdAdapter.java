package com.isummon.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isummon.R;
import com.isummon.model.HDType;
import com.isummon.model.SimpleHDActivity;

import java.util.List;

/**
 * Created by horzwxy on 12/18/13.
 */
public class SimpleHdAdapter extends BaseAdapter {

    private List<SimpleHDActivity> resource;
    private Context context;

    public SimpleHdAdapter(Context context, List<SimpleHDActivity> resource) {
        this.resource = resource;
        this.context = context;
    }

    @Override
    public int getCount() {
        return resource.size();
    }

    @Override
    public Object getItem(int position) {
        return resource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return resource.get(position).getHdId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.act_list_item, null) ;
        }
        SimpleHDActivity hdModel = resource.get(position);

        ImageView typeImage = (ImageView) convertView.findViewById(R.id.type_logo);
        HDType hdType = hdModel.getHdType();
        typeImage.setImageResource(
                context.getResources().getIdentifier(
                        "com.isummon:drawable/" + hdType.name().toLowerCase(),
                        null,
                        null
                )
        );

        TextView typeName = (TextView) convertView.findViewById(R.id.type_name);
        typeName.setText(hdType.getChn());

        TextView originName = (TextView) convertView.findViewById(R.id.origin_name);
        originName.setText(hdModel.getHdOriginName());

        TextView hdName = (TextView) convertView.findViewById(R.id.hd_name);
        hdName.setText(hdModel.getHdName());

        TextView hdStatus = (TextView) convertView.findViewById(R.id.status);
        hdStatus.setText(hdModel.getHdStatus().getChn());

        return convertView;
    }
}
