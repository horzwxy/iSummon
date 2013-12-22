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
 * Created by horzwxy on 12/15/13.
 */
public class PickMapAddressActivity extends MapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMapView.setDisplayMode(ISummonMapView.DisplayMode.SINGLE_TAP);
        mMapView.setAddressPickedListener(new AddressPickedListener() {
            @Override
            public void onAddressPicked(double longitude, double latitude) {
                showAddressConfirmDialog(longitude, latitude);
            }
        });
    }

    private void showAddressConfirmDialog(final double longitude, final double latitude) {
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

    private void onAddressNameInput(String name, double longitude, double latitude) {
        if(name != null && !name.equals("")) {
            onReturnAddressResult(name, longitude, latitude);
        }
        else {
            showToast(R.string.input_empty_hint);
        }
    }

    private void onReturnAddressResult(String name, double longitude, double latitude) {
        Intent intent = new Intent();
        intent.putExtra(AddActActivity.ADDRESS_NAME, name );
        intent.putExtra(AddActActivity.LONGITUDE, longitude);
        intent.putExtra(AddActActivity.LATITUDE, latitude);
        setResult(RESULT_OK, intent);
        finish();
    }

    public interface AddressPickedListener {
        public void onAddressPicked(double longitude, double latitude);
    }


}
