package com.isummon.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.isummon.R;
import com.isummon.model.SimpleHDActivity;
import com.isummon.model.UserModel;
import com.isummon.net.NetHelper;
import com.isummon.widget.ContactAdapter;
import com.isummon.widget.ProgressTaskBundle;

import java.util.Arrays;
import java.util.List;

/**
 * Created by horzwxy on 12/19/13.
 */
public class AddContactActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_contact);

        getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg_black);
    }

    public void doSearch(View v) {
        EditText input = (EditText) findViewById(R.id.search_contact_name_input);
        String username = input.getText().toString();
        if("".equals(username)) {
            Toast.makeText(this, R.string.error_input_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            new ProgressTaskBundle<String, UserModel>(
                    this,
                    R.string.searching
            ) {
                @Override
                protected UserModel doWork(String... params) {
                    return NetHelper.findUserByName(params[0]);
                }

                @Override
                protected void dealResult(UserModel result) {
                    if(result == null) {
                        Toast.makeText(AddContactActivity.this,
                                R.string.no_user_found,
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ContactAdapter adapter = new ContactAdapter(AddContactActivity.this, Arrays.asList(result));
                        ListView listView = (ListView) findViewById(R.id.contact_search_result);
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                showConfirmDialog((UserModel) parent.getItemAtPosition(position));
                            }
                        });
                    }
                }
            }.action(username);
        }
    }

    private void showConfirmDialog(UserModel contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.add_contact_confirm_1)
        + contact.getNickName() + getString(R.string.add_contact_confirm_2));
        builder.setPositiveButton(R.string.add_contact_confirm_position,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setNegativeButton(R.string.add_contact_confirm_negative,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing
                    }
                });
        builder.create().show();
    }
}
