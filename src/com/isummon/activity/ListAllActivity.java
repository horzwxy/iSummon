package com.isummon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.model.ActListMode;
import com.isummon.model.HDType;
import com.isummon.model.SimpleHDActivity;
import com.isummon.widget.SimpleHdAdapter;

import java.util.ArrayList;
import java.util.List;


public class ListAllActivity extends ListActActivity {

    public static final String SIMPLE_ACTS = "simple_acts";

    private List<SimpleHDActivity> displayedActs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();

        final Spinner submodeSpinner = (Spinner) findViewById(R.id.list_submode_selector);
        ArrayAdapter<HDType> submodeAdapter = new ArrayAdapter<HDType>(
                this,
                android.R.layout.simple_spinner_item,
                HDType.values());
        submodeSpinner.setAdapter(submodeAdapter);
        submodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        submodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HDType type = (HDType) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner modeSpinner = (Spinner) findViewById(R.id.list_mode_selector);
        final ArrayAdapter<ActListMode> modeAdapter = new ArrayAdapter<ActListMode>(
                this,
                android.R.layout.simple_spinner_item,
                ActListMode.values());
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(modeAdapter);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                submodeSpinner.setEnabled(false);

                ActListMode mode = (ActListMode) parent.getItemAtPosition(position);
                switch (mode) {
                    case ALL:
                        break;
                    case TYPE:
                        submodeSpinner.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
