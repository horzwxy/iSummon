package com.isummon.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.model.UserModel;
import com.isummon.widget.ContactAdapter;
import com.isummon.widget.ProgressTaskBundle;

import java.util.ArrayList;

/**
 * 添加好友的界面。
 */
public class AddContactActivity extends ISummonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_contact);

        getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg_black);
    }

    /**
     * 点击按钮，按名字搜索用户，并加为好友
     * @param v
     */
    public void doSearch(View v) {
        EditText input = (EditText) findViewById(R.id.search_contact_name_input);
        String username = input.getText().toString();
        if("".equals(username)) {
            showToast(R.string.error_input_empty);
            return;
        }
        else {
            new ProgressTaskBundle<String, ArrayList<UserModel>>(
                    this,
                    R.string.searching
            ) {
                @Override
                protected ArrayList<UserModel> doWork(String... params) {
                    return GlobalVariables.netHelper.findUserByName(params[0]);
                }

                @Override
                protected void dealResult(ArrayList<UserModel> result) {
                    if(result.size() == 0) {
                        showToast(R.string.no_user_found);
                    }
                    else {
                        ContactAdapter adapter = new ContactAdapter(AddContactActivity.this, result);
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
