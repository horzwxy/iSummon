package com.isummon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.model.HDActivity;
import com.isummon.model.HDStatus;
import com.isummon.model.HDType;
import com.isummon.widget.ProgressTaskBundle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by horzwxy on 12/18/13.
 */
public class ShowHdDetailsActivity extends Activity {

    public static final String HDACTIVITY = "hdActivity";
    private HDActivity hdActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_act);

        getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg_black);

        Intent intent = getIntent();
        hdActivity = (HDActivity) intent.getSerializableExtra(HDACTIVITY);

        setText(R.id.actName, R.string.act_name_prompt, hdActivity.getHdName());
        setText(R.id.actPlace, R.string.act_place_prompt, hdActivity.getHdAddress());
        DateFormat s2Date = new SimpleDateFormat(HDActivity.tmFormat);
        DateFormat date2s = new SimpleDateFormat("yyyy年MM月dd日 HH : mm");
        try {
            Date startDate = s2Date.parse(hdActivity.getHdStartTime());
            Date endDate = s2Date.parse(hdActivity.getHdEndTime());
            setText(R.id.act_start_time,
                    R.string.act_start_time_prompt,
                    date2s.format(startDate));
            setText(R.id.act_end_time,
                    R.string.act_end_time_prompt,
                    date2s.format(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setText(R.id.actContent, R.string.act_content_prompt, hdActivity.getHdDesc());
        setText(R.id.act_participants, R.string.act_participants_prompt, hdActivity.getHdCurNum() + "/" + hdActivity.getHdNumLimit());
        setText(R.id.act_property, R.string.act_property_prompt, hdActivity.getHdProperty().getChn());

        ImageView typeImage = (ImageView)findViewById(R.id.act_type_image);
        TextView typeText = (TextView)findViewById(R.id.act_type_name);

        HDType type = hdActivity.getHdType();

        int imageId = getResources().getIdentifier(
                "com.isummon:drawable/" + type.name().toLowerCase(),
                null,
                null
        );
        typeImage.setImageResource(imageId);
        typeText.setText(type.getChn());

        if(GlobalVariables.netHelper.isMyId(
                hdActivity.getHdOriginId()
        )) {
            findViewById(R.id.other_can_see).setVisibility(View.GONE);
        }
        else {
            findViewById(R.id.origin_can_see).setVisibility(View.GONE);
            if(hdActivity.getHdStatus() == HDStatus.CANCELED
                    || hdActivity.getHdStatus() == HDStatus.NO_VACANCY) {
                Button applyButton = (Button) findViewById(R.id.apply_in);
                applyButton.setBackgroundResource(
                        R.drawable.button_bg_disabled);
                applyButton.setText(R.string.not_available);
                applyButton.setEnabled(false);
            }
        }
    }

    private void setText(int viewId, int hintStringId, String content) {
        TextView editText = (TextView) findViewById(viewId);
        editText.setText(getString(hintStringId) + " : " + content);
    }

    public void applyIn(View v) {

    }

    public void cancelAct(View v) {
        new ProgressTaskBundle<Integer,Boolean>(
                this,
                R.string.delivering
        ) {
            @Override
            protected Boolean doWork(Integer[] params) {
                return GlobalVariables.netHelper.cancleHDActivity(params[0]);
            }

            @Override
            protected void dealResult(Boolean result) {
                if(result) {
                    Toast.makeText(ShowHdDetailsActivity.this,
                            R.string.cancel_success,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(ShowHdDetailsActivity.this,
                            R.string.cancel_failed,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }.action(hdActivity.getHdId());
    }

    public void modifyAct(View v) {
        Intent intent = new Intent(this, ModifyHdActivity.class);
        intent.putExtra(ModifyHdActivity.ACTIVITY, hdActivity);
        startActivity(intent);
        finish();
    }
}
