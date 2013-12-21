package com.isummon.activity;

import android.app.Activity;
import android.os.Bundle;

import com.isummon.R;

/**
 * Created by horz on 12/20/13.
 */
public class ListSomeActivity extends ListActActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_some);
        init();
    }
}
