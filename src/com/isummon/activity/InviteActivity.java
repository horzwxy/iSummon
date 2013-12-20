package com.isummon.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.isummon.R;
import com.isummon.model.UserModel;
import com.isummon.net.NetHelper;
import com.isummon.widget.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by horzwxy on 12/20/13.
 */
public class InviteActivity extends Activity {

    public static final String HD_ID = "hdid";

    private List<UserModel> invitedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_invite);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg_black);

        invitedList = new ArrayList<UserModel>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        final AsyncTask<Void, Void, List<UserModel>> fetchTask
                = new AsyncTask<Void, Void, List<UserModel>>() {
            @Override
            protected List<UserModel> doInBackground(Void... params) {
                return NetHelper.getAllContacts();
            }

            @Override
            protected void onPostExecute(List<UserModel> userModels) {
                if(userModels.size() == 0) {
                    Button submitButton = (Button) findViewById(R.id.submit_invitation);
                    submitButton.setText(R.string.no_contact);
                    submitButton.setEnabled(false);
                }
                else {
                    findViewById(R.id.contact_list).setVisibility(View.VISIBLE);
                    updateContactList(userModels);
                }
            }
        };
        progressDialog.setMessage(getString(R.string.loading_contacts));
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                fetchTask.cancel(true);
            }
        });
        fetchTask.execute();
    }

    private void updateContactList(List<UserModel> contacts) {
        ListView listView = (ListView) findViewById(R.id.contact_list);
        listView.setAdapter(new ContactAdapter(this, contacts) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = super.getView(position, convertView, parent);
                convertView.setBackgroundResource(R.drawable.contact_item_bg_notinvited);
                TextView name = (TextView) convertView.findViewById(R.id.contact_name);
                name.setTextColor(Color.BLACK);
                return convertView;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserModel pickedContact = (UserModel) parent.getItemAtPosition(position);
                if(invitedList.contains(pickedContact)) {
                    invitedList.remove(pickedContact);
                    view.setBackgroundResource(R.drawable.contact_item_bg_notinvited);
                }
                else {
                    invitedList.add(pickedContact);
                    view.setBackgroundResource(R.drawable.contact_item_bg_invited);
                }
            }
        });
    }

    public void submitInivitation(View v) {
        finish();
    }

    public void skip(View v) {
        finish();
    }
}
