package com.isummon.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.model.MyInvitation;
import com.isummon.widget.InvitationAdapter;
import com.isummon.widget.ProgressTaskBundle;

import java.util.ArrayList;

/**
 * Created by horz on 12/22/13.
 */
public class ListInvitationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_invitation);


    }

    @Override
    protected void onResume() {
        super.onResume();

        new ProgressTaskBundle<Void, ArrayList<MyInvitation>>(
                this,
                R.string.fetching_notifications
        ) {
            @Override
            protected ArrayList<MyInvitation> doWork(Void... params) {
                return GlobalVariables.netHelper.getMyInvitations();
            }

            @Override
            protected void dealResult(ArrayList<MyInvitation> result) {
                updateList(result);
            }
        }.action();
    }

    private void updateList(ArrayList<MyInvitation> list) {
        ListView listView = (ListView) findViewById(R.id.invitation_list);
        listView.setAdapter(new InvitationAdapter(
                this,
                list
        ));
    }
}
