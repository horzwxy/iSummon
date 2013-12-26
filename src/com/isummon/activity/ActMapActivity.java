package com.isummon.activity;

import android.os.Bundle;

import com.isummon.model.SimpleHDActivity;
import com.isummon.widget.ISummonMapView;

import java.util.List;

/**
 * 在地图上显示一系列的活动，由通过Intent传入的活动列表指定。
 * 活动列表需为List&lt;SimpleHDActivity&gt;类型。
 */
public class ActMapActivity extends MapActivity {

    /**
     * key in Intent
     */
    public static final String SIMPLE_ACTS = "simplehds";

    private List<SimpleHDActivity> displayedActs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        displayedActs = (List<SimpleHDActivity>) getIntent().getSerializableExtra(SIMPLE_ACTS);

        mMapView.setDisplayMode(ISummonMapView.DisplayMode.BALLOON_ONLY);
        mMapView.showHd(displayedActs);
    }
}
