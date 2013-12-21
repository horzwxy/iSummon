package com.isummon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.model.SimpleHDActivity;
import com.isummon.widget.SimpleHdAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by horz on 12/20/13.
 */
public class ListActActivity extends Activity {

    public static final String SIMPLE_ACTS = "simple_acts";

    private List<SimpleHDActivity> displayedActs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        displayedActs = (List<SimpleHDActivity>) intent.getSerializableExtra(SIMPLE_ACTS);
    }

    protected void init() {
        ListView listView = ((ListView)findViewById(R.id.list_content));
        listView.setAdapter(
                new SimpleHdAdapter(this, displayedActs));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActActivity.this, ShowHdDetailsActivity.class);
                intent.putExtra(ShowHdDetailsActivity.HDACTIVITY, GlobalVariables.netHelper.getHDActivityById((int) id));
                startActivity(intent);
            }
        });
    }

    protected void showOnMap() {
        Intent intent = new Intent(this, ActMapActivity.class);
        intent.putExtra(ActMapActivity.SIMPLE_ACTS,
                new ArrayList<SimpleHDActivity>(displayedActs));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toMap:
                showOnMap();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
