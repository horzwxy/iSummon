package com.isummon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.model.HDActivity;
import com.isummon.model.SimpleHDActivity;
import com.isummon.widget.ProgressTaskBundle;
import com.isummon.widget.SimpleHdAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示活动列表。显示方式由其子类实现。
 */
public class ListActActivity extends ISummonActivity {

    /**
     * 传入的活动列表key
     */
    public static final String SIMPLE_ACTS = "simple_acts";

    /**
     * 要显示的活动简要信息列表
     */
    protected List<SimpleHDActivity> displayedActs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        displayedActs = (List<SimpleHDActivity>) intent.getSerializableExtra(SIMPLE_ACTS);
    }

    /**
     * 初始化活动列表
     */
    protected void init() {
        ListView listView = ((ListView) findViewById(R.id.list_content));
        listView.setAdapter(
                new SimpleHdAdapter(this, displayedActs));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position=" + position);
                System.out.println("id=" + id);
                onShowDetails((int)id);
            }
        });
    }

    private void onShowDetails(int id) {
        new ProgressTaskBundle<Integer, HDActivity>(
                this,
                R.string.delivering
        ) {
            @Override
            protected HDActivity doWork(Integer... params) {
                return GlobalVariables.netHelper.getHDActivityById(params[0]);
            }

            @Override
            protected void dealResult(HDActivity result) {
                Intent intent = new Intent(ListActActivity.this, ShowHdDetailsActivity.class);
                intent.putExtra(ShowHdDetailsActivity.HDACTIVITY, result);
                intent.putExtra(ShowHdDetailsActivity.WAY, 1);
                startActivity(intent);
            }
        }.action(id);
    }

    /**
     * 切换至地图界面显示活动
     */
    protected void showOnMap() {
        Intent intent = new Intent(this, ActMapActivity.class);
        intent.putExtra(ActMapActivity.SIMPLE_ACTS,
                new ArrayList<SimpleHDActivity>(displayedActs));
        startActivity(intent);
    }

    /**
     * 创建Action Bar项目
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    /**
     * 响应Action Bar事件
     * @param item
     * @return
     */
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
