package com.isummon.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isummon.R;
import com.isummon.model.UserModel;

import java.util.List;

/**
 * Created by horzwxy on 12/19/13.
 */
public class ContactAdapter extends BaseAdapter {

    private List<UserModel> contacts;
    private Context context;

    public ContactAdapter(Context context, List<UserModel> contacts) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public UserModel getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contacts.get(position).getUserId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.contact_list_item, null);
        }
        ImageView avatar = (ImageView) convertView.findViewById(R.id.contact_avatar);
        avatar.setImageResource(R.drawable.head);
        TextView name = (TextView) convertView.findViewById(R.id.contact_name);
        name.setText(getItem(position).getNickName());

        return convertView;
    }
}
