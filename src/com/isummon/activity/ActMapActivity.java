package com.isummon.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.isummon.R;
import com.isummon.model.SimpleHDActivity;
import com.isummon.widget.ISummonMapView;

import java.util.List;

/**
 * Created by horzwxy on 12/19/13.
 */
public class ActMapActivity extends MapActivity {

    public static final String SIMPLE_ACTS = "simplehds";

    private List<SimpleHDActivity> displayedActs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        displayedActs = (List<SimpleHDActivity>)getIntent().getSerializableExtra(SIMPLE_ACTS);

        mMapView.setDisplayMode(ISummonMapView.DisplayMode.BALLOON_ONLY);
        mMapView.showHd(displayedActs);
    }
}
