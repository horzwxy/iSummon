package com.isummon.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.model.DisplayInvitation;
import com.isummon.widget.InvitationAdapter;
import com.isummon.widget.ProgressTaskBundle;

import java.util.ArrayList;

/**
 * Created by horz on 12/22/13.
 */
public class ListInvitationActivity extends ISummonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_invitation);


    }

    @Override
    protected void onResume() {
        super.onResume();

        new ProgressTaskBundle<Void, ArrayList<DisplayInvitation>>(
                this,
                R.string.fetching_notifications
        ) {
            @Override
            protected ArrayList<DisplayInvitation> doWork(Void... params) {
                return GlobalVariables.netHelper.getMyInvitations();
            }

            @Override
            protected void dealResult(ArrayList<DisplayInvitation> result) {
                updateList(result);
            }
        }.action();
    }

    private void updateList(ArrayList<DisplayInvitation> list) {
        ListView listView = (ListView) findViewById(R.id.invitation_list);
        listView.setAdapter(new InvitationAdapter(
                this,
                list
        ));
    }
}
