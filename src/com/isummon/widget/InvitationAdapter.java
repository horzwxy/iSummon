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
import com.isummon.model.DisplayInvitation;

import java.util.ArrayList;

/**
 * 用于列表显示邀请信息的adapter
 */
public class InvitationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DisplayInvitation> invitations;

    public InvitationAdapter(Context context, ArrayList<DisplayInvitation> invitations) {
        this.context = context;
        this.invitations = invitations;
    }

    @Override
    public int getCount() {
        return invitations.size();
    }

    @Override
    public DisplayInvitation getItem(int i) {
        return invitations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.invitation_item,null);
        }
        ImageView avatarView = (ImageView) view.findViewById(R.id.targetAvatar);
        avatarView.setImageResource(
                ((ISummonActivity)context).getAvatarResourceId(
                        getItem(position).getTargetAvatar()
                )
        );

        TextView contentText = (TextView) view.findViewById(R.id.invitation_content);
        DisplayInvitation myInvitation = getItem(position);
        contentText.setText("邀请" + myInvitation.getTargetName() + "\"" + myInvitation.getActivityName() + "\"");

        TextView statusText = (TextView) view.findViewById(R.id.invitation_status);
        statusText.setText("邀请状态：" + myInvitation.getInvitationStatus().getChn());

        return view;
    }
}
