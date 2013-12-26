package com.isummon.activity;

import android.os.Bundle;

import com.isummon.R;

/**
 * 列表显示传入的一些活动信息，活动列表通过Intent传入。
 */
public class ListSomeActivity extends ListActActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_some);
        init();
    }
}
