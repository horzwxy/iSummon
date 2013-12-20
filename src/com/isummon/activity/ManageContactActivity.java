package com.isummon.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.isummon.R;
import com.isummon.model.UserModel;
import com.isummon.net.NetHelper;
import com.isummon.widget.ContactAdapter;

import java.util.List;

/**
 * Created by horzwxy on 12/19/13.
 */
public class ManageContactActivity extends Activity {

    public static final int ADD_CONTACT = 7865;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_contact);
    }

    @Override
    protected void onResume() {
        super.onResume();

        fetchContacts();
    }

    private void fetchContacts() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final AsyncTask<Void, Void, List<UserModel>> task
                = new AsyncTask<Void, Void, List<UserModel>>() {
            @Override
            protected List<UserModel> doInBackground(Void... params) {
                return NetHelper.getAllContacts();
            }

            @Override
            protected void onPostExecute(List<UserModel> contacts) {
                progressDialog.dismiss();
                updateList(contacts);
            }

            @Override
            protected void onCancelled() {
                progressDialog.dismiss();
            }
        };
        progressDialog.setMessage(getString(R.string.loading_contacts));
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                task.cancel(true);
            }
        });
        progressDialog.show();

        task.execute();

    }

    private void updateList(List<UserModel> contacts) {
        ListView contactsList = (ListView) findViewById(R.id.contact_list);
        contactsList.setAdapter(new ContactAdapter(
                this,
                contacts));
        contactsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showConfirmRemove((UserModel)parent.getItemAtPosition(position));
                return true;
            }
        });
    }

    private void showConfirmRemove(UserModel userModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.remove_contact_confirm_1)
        + userModel.getNickName() + getString(R.string.remove_contact_confirm_2));
        builder.setPositiveButton(R.string.remove_contact_confirm_position,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setNegativeButton(R.string.remove_contact_confirm_negative,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_CONTACT && resultCode == RESULT_OK) {

        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_contact, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_contact:
                startActivity(new Intent(this, AddContactActivity.class));
                break;
            default: return super.onMenuItemSelected(featureId, item);
        }
        return true;
    }
}
