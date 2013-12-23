package com.isummon.activity;

import android.os.Bundle;

import com.isummon.R;
import com.isummon.widget.ISummonMapView;

/**
 * Created by horzwxy on 12/19/13.
 */
public class MapActivity extends ISummonActivity {

    protected ISummonMapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_display);

        mMapView = (ISummonMapView) findViewById(R.id.bmapsView);

//        mMapView.setLongTouchAvailable(false);
        mMapView.setDisplayMode(ISummonMapView.DisplayMode.NORMAL);
    }

    @Override
    protected void onDestroy() {
        mMapView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMapView.onRestoreInstanceState(savedInstanceState);
    }
}
