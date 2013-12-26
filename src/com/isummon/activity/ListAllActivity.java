package com.isummon.activity;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.model.HDType;
import com.isummon.model.SimpleHDActivity;
import com.isummon.widget.ProgressTaskBundle;

import java.util.ArrayList;


/**
 * 列表显示当前所有的活动，无需传入活动列表，此界面中自动发起请求从服务器端获取活动列表。
 */
public class ListAllActivity extends ListActActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<String> modeStrings = new ArrayList<String>();
        modeStrings.add("所有类别");
        for(String s : HDType.getChns()) {
            modeStrings.add(s);
        }
        final ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                modeStrings) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = super.getView(position, convertView, parent);
                ((TextView)convertView).setTextColor(Color.WHITE);
                return convertView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                convertView = super.getDropDownView(position, convertView, parent);
                convertView.setBackgroundColor(Color.WHITE);
                return convertView;
            }
        };
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            actionBar.setListNavigationCallbacks(modeAdapter,
                    new ActionBar.OnNavigationListener() {
                        @Override
                        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                            if (itemPosition == 0) {
                                showAllItems();
                            } else {
                                showSelectedItems(HDType.values()[itemPosition - 1]);
                            }
                            return true;
                        }
                    });
        }
    }

    private void showAllItems() {
        new ProgressTaskBundle<HDType, ArrayList<SimpleHDActivity>>(
                this,
                R.string.searching
        ) {
            @Override
            protected ArrayList<SimpleHDActivity> doWork(HDType... params) {
                return GlobalVariables.netHelper.getAllActs();
            }

            @Override
            protected void dealResult(ArrayList<SimpleHDActivity> result) {
                displayedActs = result;
                init();
            }
        }.action();
    }

    private void showSelectedItems(HDType type) {
        new ProgressTaskBundle<HDType, ArrayList<SimpleHDActivity>>(
                this,
                R.string.searching
        ) {
            @Override
            protected ArrayList<SimpleHDActivity> doWork(HDType... params) {
                return GlobalVariables.netHelper.getHDActivityByHdType(params[0]);
            }

            @Override
            protected void dealResult(ArrayList<SimpleHDActivity> result) {
                displayedActs = result;
                init();
            }
        }.action(type);
    }
}
