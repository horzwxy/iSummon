package com.isummon.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.isummon.R;
import com.isummon.widget.ISummonMapView;

/**
 * 在地图上单击选择活动地点的界面，继承自通用的地图界面
 */
public class PickMapAddressActivity extends MapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMapView.setDisplayMode(ISummonMapView.DisplayMode.SINGLE_TAP);
        mMapView.setAddressPickedListener(new AddressPickedListener() {
            @Override
            public void onAddressPicked(int longitude, int latitude) {
                showAddressConfirmDialog(longitude, latitude);
            }
        });
    }

    private void showAddressConfirmDialog(final int longitude, final int latitude) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.pick_addr_from_map_dialog_title);
        View dialogContent = getLayoutInflater().inflate(R.layout.dialog_input_addr_name, null);
        final EditText nameEditor = (EditText) dialogContent.findViewById(R.id.addr_name_editor);
        builder.setView(dialogContent);
        builder.setPositiveButton(R.string.input_positive,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onAddressNameInput(nameEditor.getText().toString(),
                                longitude,
                                latitude);
                        // no need to call dismiss
                    }
                });
        builder.setNegativeButton(R.string.input_negative,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // no need to call dismiss. System will help that.
                    }
                });
        builder.setCancelable(true);
        builder.create().show();
    }

    private void onAddressNameInput(String name, int longitude, int latitude) {
        if(name != null && !name.equals("")) {
            onReturnAddressResult(name, longitude, latitude);
        }
        else {
            showToast(R.string.input_empty_hint);
        }
    }

    private void onReturnAddressResult(String name, int longitude, int latitude) {
        Intent intent = new Intent();
        intent.putExtra(ActEditActivity.ADDRESS_NAME, name );
        intent.putExtra(ActEditActivity.LONGITUDE, longitude);
        intent.putExtra(ActEditActivity.LATITUDE, latitude);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Activity向地图视图注册回调函数，用来响应单击事件
     */
    public interface AddressPickedListener {
        public void onAddressPicked(int longitude, int latitude);
    }


}
