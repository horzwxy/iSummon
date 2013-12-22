package com.isummon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.activity.listmodel.OptionListItem;
import com.isummon.model.SimpleHDActivity;
import com.isummon.widget.ISummonMapView;
import com.isummon.widget.ProgressTaskBundle;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ISummonActivity {
    private BMapManager mBMapMan;
    private ISummonMapView mMapView;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(R.string.fake_nickname);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawer = (LinearLayout) findViewById(R.id.main_drawer);
        mDrawer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // block the touch event
                return true;
            }
        });

        ListView options = (ListView) findViewById(R.id.main_drawer_options);
        options.setAdapter(new ArrayAdapter<OptionListItem>(
                this,
                android.R.layout.simple_list_item_1,
                OptionListItem.values()
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = super.getView(position, convertView, parent);
                TextView textView = (TextView) convertView;
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(
                        new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT)
                );
                return convertView;
            }
        });
        options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OptionListItem item = (OptionListItem) parent.getItemAtPosition(position);
                switch (item) {
                    case MANAGE_CONTACT:
                        startActivity(new Intent(MainActivity.this, ManageContactActivity.class));
                        break;
                    case MY_MESSAGE:
                        startActivity(new Intent(MainActivity.this, NotificationListActivity.class));
                        break;
                    case VIEW_ALL:
                        onListAllActs();
                        break;
                    case MY_INVITATIONS:
                        startActivity(new Intent(MainActivity.this, ListInvitationActivity.class));
                        break;
                    case LOG_OUT:
                        GlobalVariables.netHelper.logOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                    case ADD_ACT:
                        startActivity(new Intent(getApplicationContext(), AddActActivity.class));
                        break;
                    case EXIT:
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                }
                mDrawerLayout.closeDrawers();
            }
        });

        mBMapMan = ((TestApplication) this.getApplication()).getBMapManager();
        mMapView = (ISummonMapView) findViewById(R.id.bmapsView);
        mMapView.setDisplayMode(ISummonMapView.DisplayMode.NORMAL);

        ImageView avatarView = (ImageView) findViewById(R.id.main_head_pic);
        avatarView.setImageResource(getAvatarResourceId(
                GlobalVariables.currentUser.getAvatar()
        ));
    }

    @Override
    protected void onDestroy() {
        mMapView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        if (mBMapMan != null) {
            mBMapMan.stop();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mMapView.onResume();
        // I found if you wants to respond the long-tap action, you must start the manager.
        // And if you don't want to respond, it's useless to do anything while the manager is started.
        if (mBMapMan != null) {
            mBMapMan.start();
        }

        new ProgressTaskBundle<Void, ArrayList<SimpleHDActivity>>(
                this,
                R.string.fetching_act
        ) {
            @Override
            protected ArrayList<SimpleHDActivity> doWork(Void... params) {
                return GlobalVariables.netHelper.getAllActs();
            }

            @Override
            protected void dealResult(ArrayList<SimpleHDActivity> result) {
                mMapView.showHd(result);
            }
        }.action();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_act_prompt));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawer);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(!drawerOpen);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.menu_search:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ModifyAvatarActivity.MODIFY_AVATAR
                && resultCode == RESULT_OK) {
            int avatarId = data.getIntExtra(ModifyAvatarActivity.AVATAR_ID, 0);
            ImageView imageView = (ImageView) findViewById(R.id.main_head_pic);
            imageView.setImageResource(getAvatarResourceId(avatarId));
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onListAllActs() {
        new ProgressTaskBundle<Void, List<SimpleHDActivity>>(
                this,
                R.string.searching
        ) {
            @Override
            protected List<SimpleHDActivity> doWork(Void... params) {
                return GlobalVariables.netHelper.getAllActs();
            }

            @Override
            protected void dealResult(List<SimpleHDActivity> result) {
                Intent intent = new Intent();
                intent.putExtra(ListAllActivity.SIMPLE_ACTS,
                        new ArrayList<SimpleHDActivity>(result));
                intent.setClass(getApplicationContext(), ListAllActivity.class);
                startActivity(intent);
            }
        }.action();
    }

    private void onQuery(String query) {
        new ProgressTaskBundle<String, List<SimpleHDActivity>>(this, R.string.searching) {
            @Override
            protected List<SimpleHDActivity> doWork(String... params) {
                return GlobalVariables.netHelper.getHDActivityByHdName(params[0]);
            }

            @Override
            protected void dealResult(List<SimpleHDActivity> result) {
                Intent intent = new Intent(MainActivity.this, ListSomeActivity.class);
                intent.putExtra(
                        ListActActivity.SIMPLE_ACTS,
                        new ArrayList<SimpleHDActivity>(result));
                startActivity(intent);
            }
        }.action(query);
    }

    public void modifyAvatar(View v) {
        startActivityForResult(
                new Intent(this, ModifyAvatarActivity.class),
                ModifyAvatarActivity.MODIFY_AVATAR
        );
    }
}
