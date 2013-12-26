package com.isummon.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.isummon.R;
import com.isummon.activity.AddActActivity;
import com.isummon.activity.PickMapAddressActivity;
import com.isummon.activity.ShowHdDetailsActivity;
import com.isummon.data.GlobalVariables;
import com.isummon.model.SimpleHDActivity;
import com.isummon.net.NetHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.widget.Toast;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by horzwxy on 12/15/13.
 */
public class ISummonMapView extends MapView {

    public final static int ADD_ACT = 876543;
    public final static String SIMPLE_HD = "simple_hd";

    private MyOverlay mOverlay = null;
    private PointOverlay pointOverlay;
    private PickMapAddressActivity.AddressPickedListener listener;
    private boolean longTouchEnable = true;
    private ArrayList<SimpleHDActivity> currentHDList;



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


        //initialize mOverlay with default marker;
        mOverlay = new MyOverlay(getContext().getResources().getDrawable(R.drawable.icon_gcoding), this);
        getOverlays().add(mOverlay);

        pointOverlay = new PointOverlay(getContext().getResources().getDrawable(R.drawable.icon_gcoding), this);

        //register mapView touch listener.
        regMapTouchListner(new MKMapTouchListener() {

            @Override
            public void onMapLongClick(GeoPoint point) {
                if(!longTouchEnable)
                    return;
                final int longitude = point.getLongitudeE6();
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
            }
            @Override
            public void onMapClick(GeoPoint arg0) {
            }
        });
    }

    public void setAddressPickedListener(PickMapAddressActivity.AddressPickedListener listener) {
        // register the callback
        // call listener.onAddressPicked when user has done something on the view(single tap or long tap or some kind else)
        this.listener = listener;
    }

    public void setDisplayMode(DisplayMode mode) {
        switch (mode){
            case NORMAL:{
                getOverlays().clear();
                longTouchEnable = true;
                getOverlays().add(mOverlay);
                break;
            }case BALLOON_ONLY:{
                getOverlays().clear();
                longTouchEnable = false;
                getOverlays().add(mOverlay);
                break;
            }case SINGLE_TAP:{
                getOverlays().clear();
                longTouchEnable = false;
                getOverlays().add(pointOverlay);
                break;
            }default:{
                Log.v("ISummonMapView", "Invalid display mode!");
                return;
            }

        }
    }

    public void showHd(List<SimpleHDActivity> hdIdList) {
       currentHDList = (ArrayList<SimpleHDActivity>)hdIdList;
       mOverlay.addItem(getItemFromHdActivity(currentHDList));
    }

    private void showAddActActivity(int longitude, int latitude) {
        Intent intent = new Intent(getContext(), AddActActivity.class);
        intent.putExtra(AddActActivity.LONGITUDE, longitude);
        intent.putExtra(AddActActivity.LATITUDE, latitude);
        getContext().startActivity(intent);
    }

    /*********************** Private method ************************/

    private ArrayList<OverlayItem> getItemFromHdActivity(ArrayList<SimpleHDActivity> hdList){
        if(hdList == null || hdList.size() == 0)
            return null;
        ArrayList<OverlayItem> itemList = new ArrayList<OverlayItem>();
        for(int i = 0; i < hdList.size(); i++){
            SimpleHDActivity hd = hdList.get(i);
            GeoPoint point = new GeoPoint((int) hd.getHdLatitude(), (int) hd.getHdLongitude());
            itemList.add(new OverlayItem(point, hd.getHdName(), hd.getHdOriginName()));
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
        private TextView popupText = null;
        private View viewCache = null;
        private View popupInfo = null;
        private View popupLeft = null;
        private View popupRight = null;
        private PopupOverlay pop ;
        public MyOverlay(Drawable defaultMarker, MapView mapView) {
            super(defaultMarker, mapView);

            viewCache = LayoutInflater.from(getContext()).inflate(R.layout.custom_text_view, null);
            popupInfo = (View) viewCache.findViewById(R.id.popinfo);
            popupLeft = (TextView) viewCache.findViewById(R.id.popleft);
            popupRight = (TextView) viewCache.findViewById(R.id.popright);
            popupText =(TextView) viewCache.findViewById(R.id.textcache);


        }

        protected boolean onTap(int index) {
//            final SimpleHDActivity selectedHd = currentHDList.get(index);
            PopupClickListener popListener = new PopupClickListener(){
                @Override
                public void onClickedPopup(int popIndex) {
                    if ( popIndex == 0){
                        //查看活动
                        Intent intent = new Intent();
                        intent.setClass(getContext().getApplicationContext(), ShowHdDetailsActivity.class);
//                        intent.putExtra(ShowHdDetailsActivity.HDACTIVITY, selectedHd);
                        getContext().startActivity(intent);
                    }
                    else if(popIndex == 2){
                        //申请加入
//                        if(GlobalVariables.currentUser.getUserId() == selectedHd.getHdOriginId()){//
//
//                        }
                    }
                }
            };
            pop = new PopupOverlay(mMapView,popListener);
            popupText.setText(getItem(index).getTitle());
            Bitmap[] bitMaps={
                    getBitmapFromView(popupLeft),
                    getBitmapFromView(popupInfo),
                    getBitmapFromView(popupRight)
            };
            pop.showPopup(bitMaps,getItem(index).getPoint(),32);
            return false;
        }
        @Override
        public boolean onTap(GeoPoint pt , MapView mMapView){
            if (pop != null){
                pop.hidePop();
            }
            return false;
        }
        public  Bitmap getBitmapFromView(View view) {
            view.destroyDrawingCache();
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = view.getDrawingCache(true);
            return bitmap;
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
            Log.v("iSummon-----------------", geoPoint.toString());
            listener.onAddressPicked(geoPoint.getLongitudeE6(), geoPoint.getLatitudeE6());
            return true;
        }


    }

    public enum DisplayMode {
        NORMAL,
        BALLOON_ONLY,
        SINGLE_TAP;
    }
}
