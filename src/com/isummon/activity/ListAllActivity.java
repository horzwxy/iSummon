package com.isummon.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.isummon.R;
import com.isummon.activity.listmodel.ActListMode;
import com.isummon.data.GlobalVariables;
import com.isummon.model.HDType;
import com.isummon.model.SimpleHDActivity;
import com.isummon.widget.ProgressTaskBundle;

import java.util.ArrayList;
import java.util.List;


public class ListAllActivity extends ListActActivity {

    public static final String SIMPLE_ACTS = "simple_acts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();

        Spinner modeSpinner = (Spinner) findViewById(R.id.list_mode_selector);
        ArrayList<String> modeStrings = new ArrayList<String>();
        for(String s : HDType.getChns()) {
            modeStrings.add(s);
        }
        modeStrings.add("所有类别");
        final ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                modeStrings);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(modeAdapter);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > HDType.values().length - 1) {
                    showAllItems();
                }
                else {
                    showSelectedItems(HDType.values()[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
