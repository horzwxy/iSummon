package com.isummon.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.isummon.R;
import com.isummon.activity.AddActActivity;
import com.isummon.activity.PickMapAddressActivity;
import com.isummon.activity.ShowHdDetailActivity;
import com.isummon.model.SimpleHDActivity;
import com.isummon.net.FakeDataProvider;
import com.isummon.net.NetHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by horzwxy on 12/15/13.
 */
public class ISummonMapView extends MapView {

    private Handler handler;
    private MyOverlay mOverlay = null;
    private PointOverlay pointOverlay;
    private PickMapAddressActivity.AddressPickedListener listener;

    public ISummonMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBuiltInZoomControls(true);
        getController().enableClick(true);

        // 设置启用内置的缩放控件
        MapController mMapController = getController();
        // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
        // 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
        mMapController.setCenter(getDefaultGeoPoint());// 设置地图中心点
        mMapController.setZoom(getDefaultZoomClass());// 设置地图zoom级别

        handler = new Handler();
        //initialize mOverlay with default marker;
        mOverlay = new MyOverlay(getContext().getResources().getDrawable(R.drawable.icon_gcoding), this);
        getOverlays().add(mOverlay);

        pointOverlay = new PointOverlay(getContext().getResources().getDrawable(R.drawable.icon_gcoding), this);

        //register mapView touch listener.
        regMapTouchListner(new MKMapTouchListener() {

            @Override
            public void onMapLongClick(GeoPoint point) {

                final int longitude = point.getLatitudeE6();
                final int latitude = point.getLatitudeE6();

                mOverlay.addItem(new OverlayItem(point, "", ""));
                refresh();

                // I don't know why, but MapView completes invalidating not before the AddActActivity starts
                // So I cannot see the newly-added balloon through the AddActActivity background
                // this is the dummy solution: delay starting activity
                // todo
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        showAddActActivity(longitude, latitude);
                    }
                }, 500);

            }

            @Override
            public void onMapDoubleClick(GeoPoint arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onMapClick(GeoPoint arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void setAddressPickedListener(PickMapAddressActivity.AddressPickedListener listener) {
        // register the callback
        // call listener.onAddressPicked when user has done something on the view(single tap or long tap or some kind else)
        this.listener = listener;
    }

    public void setLongTouchAvailable(boolean isAvailable) {
        if(!isAvailable){
            getOverlays().add(pointOverlay);
            refresh();
        }else{
            if(getOverlays().contains(pointOverlay)){
                getOverlays().remove(pointOverlay);
                refresh();
            }
        }
    }

    public void showHd(List<SimpleHDActivity> hdIdList) {

    }

    private void showAddActActivity(double longitude, double latitude) {
        Intent intent = new Intent(getContext(), AddActActivity.class);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);
        getContext().startActivity(intent);
    }


    public void showAllCurrentActivities(){
        new Thread() {
            @Override
            public void run() {
                final ArrayList<OverlayItem> itemList = getItemFromHdActivity(NetHelper.getCurrentSimpleHDActivities());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mOverlay.addItem(itemList);
                        refresh();
                    }
                });
            }
        }.start();
    }


    /*********************** Private method ************************/

    private ArrayList<OverlayItem> getItemFromHdActivity(ArrayList<SimpleHDActivity> hdList){
        ArrayList<OverlayItem> itemList = new ArrayList<OverlayItem>();
        for(SimpleHDActivity hd: hdList){
            GeoPoint point = new GeoPoint((int) hd.getHdLatitude(), (int) hd.getHdLongitude());
            itemList.add(new OverlayItem(point, hd.getHdName(), ""));
        }
        return itemList;
    }


    private GeoPoint getDefaultGeoPoint() {
        GeoPoint point = new GeoPoint((int) (31.195719 * 1E6),
                (int) (121.604423 * 1E6));
        return point;
    }

    private int getDefaultZoomClass() {
        // zoom from 3 to 19,
        return 19;

    }

    /**
     * ********************** Inner class, itemized layout ********
     */
    private class MyOverlay extends ItemizedOverlay<OverlayItem> {

        public MyOverlay(Drawable defaultMarker, MapView mapView) {
            super(defaultMarker, mapView);
            // TODO Auto-generated constructor stub
        }

        protected boolean onTap(int index) {
            getItem(index);
//			Toast.makeText(getApplicationContext(), "item index: " + index + " content: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            // how can I get hdId?
            //intent.putExtra(ShowHdDetailActivity.HDACTIVITY, FakeDataProvider.getHDById(index));
            intent.setClass(getContext().getApplicationContext(), ShowHdDetailActivity.class);
            intent.putExtra("index", index);
            getContext().startActivity(intent);
            return false;
        }
    }

    private class PointOverlay extends  ItemizedOverlay<OverlayItem>{
        private PointOverlay(Drawable drawable, MapView mapView) {
            super(drawable, mapView);
        }

        @Override
        public boolean onTap(GeoPoint geoPoint, MapView mapView) {
//            Toast.makeText(getContext(), "hello!" + geoPoint.getLatitudeE6() + " " + geoPoint.getLongitudeE6(), Toast.LENGTH_SHORT).show();
            //Now I get the geo point, so I can give it back
            listener.onAddressPicked(geoPoint.getLongitudeE6(), geoPoint.getLongitudeE6());
            return true;
        }
    }

}
