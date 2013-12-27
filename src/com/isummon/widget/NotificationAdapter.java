package com.isummon.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isummon.R;
import com.isummon.activity.ISummonActivity;
import com.isummon.model.Notification;

import java.util.List;

/**
 * 列表显示用户收到的邀请信息的adapter
 */
public class NotificationAdapter extends BaseAdapter {

    private List<Notification> notifications;
    private Context context;

    public NotificationAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Notification getItem(int position) {
        return notifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notifications.get(position).getHdId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.notification_item,
                    null
            );
        }
        ImageView avatar = (ImageView) convertView.findViewById(R.id.origin_avatar);
        avatar.setImageResource(
                ((ISummonActivity)context).getAvatarResourceId(
                        getItem(position).getOriginAvatar()
                )
        );
        TextView originName = (TextView) convertView.findViewById(R.id.origin_name);
        originName.setText(getItem(position).getHdName());

        return convertView;
    }
}
